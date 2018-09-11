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
class PdfFillTool {

    static byte[] generatePdfBytesFromDeclaration(Set<PdfElementCreator> elementCreators,
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
                        element.print(pdfWriter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );

        document.close();

        return outputStream.toByteArray();
    }

    static byte[] generatePdfBytesTemplateFromDeclaration(Set<PdfElementCreator> elementCreators) throws Exception {
        final Rectangle a4PageSize = PageSize.A4;
        Document document = new Document(a4PageSize);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);
        document.open();

        elementCreators.forEach(elementCreator ->
                {
                    try {
                        elementCreator.printTemplate(pdfWriter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );

        document.close();

        return outputStream.toByteArray();
    }

    static void mergePdfsLayers(String bottomFilePath, Map<Integer, byte[]> pages, String destinationPath)
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
