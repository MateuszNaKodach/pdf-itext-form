package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/*
Documentation iText:
https://developers.itextpdf.com/sites/default/files/attachments/PR%20-%20iText%20in%20Action%20-%20Second%20edition%20e-book.pdf

TODO:
Definicje pliku, np. PdfFormDeclaration - który może z XML odczytać dane i stworzyć PdfDeclaration
Taki PdfFormDeclaration musi zawierać to w jakim miejscu jest dany tag i jaka ma odległość. Może też ewentualnie mieć zmienny rozmiar czcionki, ograniczenie na wielkość pola wpisywania itp!
 */
public class PdfForm3 {

    private static final String CARDO_REGULAR_FONT = "src/main/resources/fonts/Cardo-Regular.ttf";

    private static final int FONT_SIZE = 12;
    private static final String SRC = "documents/pdfs/source/ZAP-3-04.pdf";
    private static final String DEST = "documents/pdfs/filled/ZAP-3-04-filled7.pdf";

    private static final String NEW_DOCUMENT = "documents/pdfs/filled/NEW_DOCUMENT3.pdf";

    public static void main(String[] args) throws Exception {

        Map<String, String> data = DataReader.readData("src/main/resources/input.xml");
        System.out.println(data.size());
        for (String key: data.keySet()) {
            System.out.println(key + " --||-- " + data.get(key));
        }

        PdfDeclaration pdfDeclaration =
                PdfDeclaration.
                        withElements(
                                AbsoluteTextPdfElement.builder()
                                        .withTag("pesel")
                                        .andContent("91032312312")
                                        .positionedFromBottomLeft(63, 785)
                                        .withCharacterWidth(15),
                                AbsoluteTextPdfElement.builder()
                                        .withTag("imie")
                                        .andContent("Jan")
                                        .positionedFromBottomLeft(330, 495),
                                AbsoluteTextPdfElement.builder()
                                        .withTag("nazwisko")
                                        .andContent("Kowalski Karakuła")
                                        .positionedFromBottomLeft(63, 495)
                        );

        generatePdfFromDeclaration(pdfDeclaration);
        mergePdfsLayers(SRC, NEW_DOCUMENT, DEST);
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
            stringOnPosition(pdfWriter, absoluteTextPdfElement.getContent(), absoluteTextPdfElement.getX(), absoluteTextPdfElement.getY());
        }
    }

    private static void stringOnPosition(PdfWriter writer, String text, float x, float y) {
        try {
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf = BaseFont.createFont(CARDO_REGULAR_FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            cb.saveState();
            cb.beginText();
            cb.moveText(x, y);
            cb.setFontAndSize(bf, FONT_SIZE);
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
        PdfContentByte canvas = stamper.getOverContent(1);
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
