package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class PdfFillTool {

    private final String pdfOutputDirectoryPath;
    private boolean ignoreLackOfElementValue = false;

    private PdfFillTool(String pdfOutputDirectoryPath) {
        this.pdfOutputDirectoryPath = pdfOutputDirectoryPath;
    }

    static PdfFillTool withDefinedOutputDirectory(String pdfOutputDirectoryPath) {
        return new PdfFillTool(pdfOutputDirectoryPath);
    }

    //TODO: Funkcja zwracajaca byte array pliku!
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
                                generatePdfBytesFrom(formSchema.getElementsByPage(pdfPageNumber), formValues))
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
                        String value = pdfFormValues.getValueByTag(pdfElement.getTag());
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
