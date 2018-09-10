package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Marcin
 */
public class PdfFillTool {

    public static void generatePdfFromDeclaration(PdfDeclaration pdfDeclaration) throws Exception {
        final Rectangle a4PageSize = PageSize.A4;
        Document document = new Document(a4PageSize);
        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(Config.NEW_DOCUMENT));
        document.open();
        pdfDeclaration.getElements()
                .forEach(element -> {
                    if (element.isAbsoluteText())
                        renderElementOn(pdfWriter, element);
                    else if (element instanceof DatePdfElement)
                        ((DatePdfElement) element).getSimpleTextElements().forEach(el -> renderElementOn(pdfWriter, el));
                });
        document.close();
    }

    public static void renderElementOn(PdfWriter pdfWriter, PdfElement pdfElement) {
        if (pdfElement.isAbsoluteText()) {
            renderAbsoluteTextOn(pdfWriter, (AbsoluteTextPdfElement) pdfElement);
        }
    }

    public static void renderAbsoluteTextOn(PdfWriter pdfWriter, AbsoluteTextPdfElement absoluteTextPdfElement) {
        if (absoluteTextPdfElement.isTextWithSpaceBetweenLetters()) {
            List<String> textLetters = absoluteTextPdfElement.getContentLetters();
            for (int i = 0; i < textLetters.size(); i++) {
                stringOnPosition(
                        pdfWriter,
                        textLetters.get(i),
                        absoluteTextPdfElement.getX() + (i * absoluteTextPdfElement.getCharacterWidth()),
                        absoluteTextPdfElement.getY()
                );
            }
        } else {
            stringOnPosition(pdfWriter, absoluteTextPdfElement.getContent(), absoluteTextPdfElement.getX(),
                    absoluteTextPdfElement.getY());
        }
    }

    private static void stringOnPosition(PdfWriter writer, String text, float x, float y) {
        try {
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf = BaseFont.createFont(Config.CARDO_REGULAR_FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            cb.saveState();
            cb.beginText();
            cb.moveText(x, y);
            cb.setFontAndSize(bf, Config.FONT_SIZE);
            cb.showText(text);
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
}
