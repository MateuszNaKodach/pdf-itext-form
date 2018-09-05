package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class PdfForm3 {

    static final int FONT_SIZE = 12;
    static final String SRC = "documents\\pdfs\\source\\ZAP-3-04.pdf";
    static final String FILLED = "documents\\pdfs\\source\\TestPESEL.pdf";
    static final String DEST = "documents\\pdfs\\filled\\ZAP-3-04-filled7.pdf";

    static final String NEW_DOCUMENT = "documents\\pdfs\\filled\\NEW_DOCUMENT2.pdf";

    public static void main(String[] args) throws Exception {
        PdfDeclaration pdfDeclaration =
                PdfDeclaration.
                        withElements(
                                PdfAbsoluteText.builder()
                                        .withTag("pesel")
                                        .andContent("91032312312")
                                        .positionedFromBottomLeft(100, 100)
                                        .withSpaceBetweenLetters(5),
                                PdfAbsoluteText.builder()
                                        .withTag("imie")
                                        .andContent("Jan")
                                        .positionedFromBottomLeft(200, 200),
                                PdfAbsoluteText.builder()
                                        .withTag("nazwisko")
                                        .andContent("Kowalski")
                                        .positionedFromBottomLeft(300, 300)
                        );

        generatePdfFromDeclaration(pdfDeclaration);
    }

    public static void generatePdfFromDeclaration(PdfDeclaration pdfDeclaration) throws Exception {
        final Rectangle a4PageSize = PageSize.A4;
        Document document = new Document(a4PageSize);
        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(NEW_DOCUMENT));
        document.open();
        pdfDeclaration.getElements()
                .forEach(element -> renderElementOn(pdfWriter, element));
        document.close();
    }

    public static void renderElementOn(PdfWriter pdfWriter, PdfElement pdfElement) {
        if (pdfElement.isAbsoluteText()) {
            renderAbsoluteTextOn(pdfWriter, (PdfAbsoluteText) pdfElement);
        }
    }

    public static void renderAbsoluteTextOn(PdfWriter pdfWriter, PdfAbsoluteText pdfAbsoluteText) {
        if (pdfAbsoluteText.isTextWithSpaceBetweenLetters()) {
            List<String> textLetters = pdfAbsoluteText.getContentLetters();
            for (int i = 0; i < textLetters.size(); i++) {
                stringOnPosition(
                        pdfWriter,
                        textLetters.get(i),
                        pdfAbsoluteText.getX() + (i * pdfAbsoluteText.getSpaceBetweenLetters()),
                        pdfAbsoluteText.getY()
                );
            }
        } else {
            stringOnPosition(pdfWriter, pdfAbsoluteText.getContent(), pdfAbsoluteText.getX(), pdfAbsoluteText.getY());
        }
    }

    private static void stringOnPosition(PdfWriter writer, String text, float x, float y) {
        try {
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, StandardCharsets.UTF_8.displayName(), BaseFont.NOT_EMBEDDED);
            cb.saveState();
            cb.beginText();
            cb.moveText(x, y);
            cb.setFontAndSize(bf, FONT_SIZE);
            cb.showText(text);
            cb.endText();
            cb.restoreState();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
