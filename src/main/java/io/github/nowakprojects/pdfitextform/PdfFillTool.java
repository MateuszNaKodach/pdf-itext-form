package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Created by Marcin
 */
public class PdfFillTool {

    public static byte[] generatePdfBytesFromDeclaration(Set<PdfElementCreator> elementCreators,
                                                         Map<String, String> values) throws Exception {
        final Rectangle a4PageSize = PageSize.A4;
        Document document = new Document(a4PageSize);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);
        document.open();

        elementCreators.forEach(elementCreator ->
                {
                    try {
                        String value = values.get(elementCreator.getTag());
                        if (value == null)
                            throw new Exception("Can't find value for " + elementCreator.getTag());
                        PdfElement element = elementCreator.create(values.get(elementCreator.getTag()));
                        element.getSimpleElements().forEach(simpleElement -> printPdfElement(pdfWriter, simpleElement));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );

        document.close();

        return outputStream.toByteArray();
    }

    private static void printPdfElement(PdfWriter writer, SimpleTextPdfElement simpleTextPdfElement) {
        try {
            PdfPosition position = simpleTextPdfElement.getPdfPosition();
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf = BaseFont.createFont(Config.CARDO_REGULAR_FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            cb.saveState();
            cb.beginText();
            cb.moveText(position.getX(), position.getY());
            cb.setFontAndSize(bf, simpleTextPdfElement.getFontSize());
            cb.showText(simpleTextPdfElement.getContent());
            cb.endText();
            cb.restoreState();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void mergePdfsLayers(String bottomFilePath, String topFilePath, String destinationPath) throws Exception {
        PdfReader reader = new PdfReader(bottomFilePath);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(destinationPath));
        PdfContentByte canvas = stamper.getOverContent(2);
        PdfReader r;
        PdfImportedPage page;

        r = new PdfReader(topFilePath);
        page = stamper.getImportedPage(r, 1);
        canvas.addTemplate(page, 0, 0);
        stamper.getWriter().freeReader(r);
        r.close();

        stamper.close();
    }

    public static void mergePdfsLayers(String bottomFilePath, Map<Integer, byte[]> pages, String destinationPath)
            throws Exception {
        PdfReader reader = new PdfReader(bottomFilePath);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(destinationPath));

        for (Integer pageNumber: pages.keySet()) {
            PdfContentByte canvas = stamper.getOverContent(pageNumber);
            PdfReader r;
            PdfImportedPage page;

            r = new PdfReader(pages.get(pageNumber));
            page = stamper.getImportedPage(r, 1);
            canvas.addTemplate(page, 0, 0);
            stamper.getWriter().freeReader(r);
            r.close();
        }

        stamper.close();
    }
}
