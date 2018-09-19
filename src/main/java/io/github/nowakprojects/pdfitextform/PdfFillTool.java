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

    void writeFilledPdfFormToFile(String pdfFormFilePath, String pdfOutputFileName, PdfForm pdfForm) {
        File formFile = new File(pdfFormFilePath);
        try {
            final FileInputStream fileInputStream = new FileInputStream(formFile);
            String destinationPath = pdfOutputDirectoryPath + pdfOutputFileName;
            final FileOutputStream fileOutputStream = new FileOutputStream(destinationPath);
            writeFilledPdfFormToOutputStream(fileInputStream, fileOutputStream, pdfForm);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void writeFilledPdfFormToOutputStream(InputStream inputStream, OutputStream outputStream, PdfForm pdfForm) {
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
            mergePdfsLayers(inputStream, topPages, outputStream);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
            throw new PdfFillingException(e);
        }
    }

    private static void mergePdfsLayers(InputStream bottomFileInputStrem, Map<Integer, byte[]> pages, OutputStream outputStream) throws IOException, DocumentException {
        PdfReader bottomFileReader = new PdfReader(bottomFileInputStrem);
        PdfStamper stamper = new PdfStamper(bottomFileReader, outputStream);

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

    public void setIgnoreLackOfElementValue(boolean ignoreLackOfElementValue) {
        this.ignoreLackOfElementValue = ignoreLackOfElementValue;
    }

    private void writeElementWithValuesToOutputStream(Set<PdfElement> pdfElements, PdfFormValues pdfFormValues, OutputStream outputStream) {
        final Rectangle a4PageSize = PageSize.A4;
        Document document = new Document(a4PageSize);
        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);
            document.open();
            pdfElements.forEach(pdfElement -> writePdfElement(pdfFormValues, pdfWriter, pdfElement));
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new PdfFillingException(e);
        }
        document.close();
    }

    private void writePdfElement(PdfFormValues pdfFormValues, PdfWriter pdfWriter, PdfElement pdfElement) {
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

    private byte[] generatePdfBytesFrom(Set<PdfElement> pdfElements, PdfFormValues pdfFormValues) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        writeElementWithValuesToOutputStream(pdfElements, pdfFormValues, outputStream);
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

}
