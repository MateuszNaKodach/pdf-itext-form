package io.github.nowakprojects.pdfitextform.old;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.sun.org.apache.bcel.internal.generic.NEW;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class PdfForm2 {

    static final String SRC = "documents\\pdfs\\source\\ZAP-3-04.pdf";
    static final String FILLED = "documents\\pdfs\\source\\TestPESEL.pdf";
    static final String DEST = "documents\\pdfs\\filled\\ZAP-3-04-filled7.pdf";

    static final String NEW_DOCUMENT = "documents\\pdfs\\filled\\NEW_DOCUMENT.pdf";

    static final int FONT_SIZE = 12;


    public static void main(String[] args) throws IOException, DocumentException {
        generatePDFWithData();
        //processPDF();
    }


    public static void generatePDFWithData() throws FileNotFoundException, DocumentException {
        final Rectangle a4PageSize = PageSize.A4;
        Document document = new Document(a4PageSize);
        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(NEW_DOCUMENT));
        document.open();
        absText(pdfWriter, "TestowyTekstĄŹ", 0, a4PageSize.getTop() - FONT_SIZE);
        absText(pdfWriter, "TestowyTekst", 100, 100);
        absText(pdfWriter, "TestowyTekst", 200, 200);
        document.close();
    }



    public static void processPDF() throws IOException, DocumentException {
        /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PdfReader pdfReader = new PdfReader(SRC);
        PdfStamper stamper = new PdfStamper(pdfReader, stream);

        PdfLayer layer = new PdfLayer("Logo", stamper.getWriter());
        layer.setPrint("Print", false);
        PdfReader pageReader = new PdfReader(FILLED);
        PdfTemplate dekraPage = stamper.getImportedPage(pageReader, 1);

        for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
            PdfContentByte bytes = stamper.getOverContent(i);
            bytes.beginLayer(layer);
            bytes.addTemplate(dekraPage, 0, 0);
            bytes.endLayer();
        }

        stamper.close();

        return stream.toByteArray();*/

        PdfReader reader = new PdfReader(SRC);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(DEST));
        PdfContentByte canvas = stamper.getOverContent(1);
        PdfReader r;
        PdfImportedPage page;
        for (String path : Arrays.asList(FILLED)) {
            r = new PdfReader(path);
            page = stamper.getImportedPage(r, 1);
            canvas.addTemplate(page, 0, 0);
            stamper.getWriter().freeReader(r);
            r.close();
        }
        stamper.close();
    }

    private static void absText(PdfWriter writer, String text, float x, float y) {
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
