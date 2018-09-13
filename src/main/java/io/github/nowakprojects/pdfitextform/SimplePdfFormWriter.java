package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;

class SimplePdfFormWriter extends PdfFormWriter<PdfElement, String> {

    SimplePdfFormWriter(PdfElement pdfElement, String content) {
        super(pdfElement, content);
    }

    @Override
    void writeOn(PdfWriter pdfWriter) {
        if (!pdfElement.getFontSize().isPresent()) {
            throw new IllegalStateException("Pdf element have to have declared font size!");
        }
        try {
            PdfPosition position = pdfElement.getPdfPosition();
            PdfContentByte cb = pdfWriter.getDirectContent();
            BaseFont bf = new Config().baseFont;
            cb.saveState();
            cb.beginText();
            cb.moveText(position.getX(), position.getY());
            cb.setFontAndSize(bf, ((FontSize) pdfElement.getFontSize().get()).getValue()); //FIXME: Weird behaviour after Optional.get() call.
            cb.showText(content);
            cb.endText();
            cb.restoreState();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }


}
