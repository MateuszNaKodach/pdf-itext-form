package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Objects.isNull;

class PdfFillTool {

    private final String pdfOutputDirectoryPath;
    private boolean ignoreLackOfElementValue = true;

    private PdfFillTool(String pdfOutputDirectoryPath) {
        this.pdfOutputDirectoryPath = pdfOutputDirectoryPath;
    }

    static PdfFillTool withDefinedOutputDirectory(String pdfOutputDirectoryPath) {
        return new PdfFillTool(pdfOutputDirectoryPath);
    }

    void fillPdfForm(InputStream in, OutputStream out, Map<String, String> config) {

    }

    //TODO: Funkcja zwracajaca byte array pliku! ma na wejsci input stream in, i outptstream out
    void fillPdfForm(PdfForm pdfForm, String pdfOutputFileName) {
        final PdfFormSchema formSchema = pdfForm.getSchema();
        final PdfFormValues formValues = pdfForm.getValues();
        final Map<Integer, byte[]> topPages = new HashMap<Integer, byte[]>() {
            {
                formSchema.getPages()
                        .stream()
                        .filter(page -> formSchema.countElementsOnPage(page) > 0)
                        .forEach(pdfPageNumber -> put(
                                pdfPageNumber.getValue(),
                                generatePdfBytesFrom(formSchema.getSelectedElementsByPageAndValues(pdfPageNumber, formValues), formValues))
                        );
            }
        };
        try {
            mergePdfsLayers(pdfForm.getFormFilePath(), topPages, pdfOutputDirectoryPath + pdfOutputFileName);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
            throw new PdfFillingException(e);
        }
    }

    public void setIgnoreLackOfElementValue(boolean ignoreLackOfElementValue) {
        this.ignoreLackOfElementValue = ignoreLackOfElementValue;
    }

    private byte[] generatePdfBytesFrom(Set<PdfElement> pdfElements, PdfFormValues pdfFormValues) {
        final Rectangle a4PageSize = PageSize.A4;
        Document document = new Document(a4PageSize);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);
            document.open();

            pdfElements.forEach(pdfElement ->
                    {
                        String value =
                                pdfElement.isTemplated()
                                        ? getTemplatePdfElementValueWith(pdfElement, pdfFormValues)
                                        : (
                                        pdfElement.hasMultipleTags()
                                                ? Arrays.stream(pdfElement.getTagsArray())
                                                .map(pdfFormValues::getValueByTag)
                                                .reduce((s, acc) -> s + PdfFormValues.VALUE_SEPARATOR + acc)
                                                .orElse(null)
                                                : pdfFormValues.getValueByTag(pdfElement.getTag())
                                );

                        if (value == null && pdfElement.getDefaultContent().isPresent()) {
                            value = (String) pdfElement.getDefaultContent().get();
                        }
                        if (value == null && !ignoreLackOfElementValue) {
                            throw new PdfFillingException("Can't find value for " + pdfElement.getTag());
                        } else if (value != null) {
                            PdfElementWriterFactory
                                    .getPdfElementWriterFor(pdfElement, value)
                                    .writeOn(pdfWriter);
                        }
                    }
            );
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new PdfFillingException(e);
        }
        document.close();

        return outputStream.toByteArray();
    }

    private String getTemplatePdfElementValueWith(PdfElement pdfElement, PdfFormValues pdfFormValues) {
        return Arrays.stream(pdfElement.getTemplate().getContent())
                .filter(templatePart -> templatePart.shouldBeShowWith(pdfFormValues))
                .map(templatePart -> templatePart.isTagPart() ? pdfFormValues.getValueByTag(templatePart.getContent()) : templatePart.getContent())
                .map(value -> isNull(value) ? "" : value)
                .reduce((s, acc) -> s + PdfFormValues.VALUE_SEPARATOR + acc)
                .orElse("");
    }

    private static void mergePdfsLayers(String bottomFilePath, Map<Integer, byte[]> pages, String destinationPath) throws IOException, DocumentException {
        PdfReader bottomFileReader = new PdfReader(bottomFilePath);
        PdfStamper stamper = new PdfStamper(bottomFileReader, new FileOutputStream(destinationPath));

        for (Integer pageNumber : pages.keySet()) {
            PdfContentByte canvas = stamper.getOverContent(pageNumber);
            PdfReader pageReader = new PdfReader(pages.get(pageNumber));
            PdfImportedPage page = stamper.getImportedPage(pageReader, 1);
            canvas.addTemplate(page, 0, 0);
            stamper.getWriter().freeReader(pageReader);
            pageReader.close();
        }

        stamper.close();
    }
}
