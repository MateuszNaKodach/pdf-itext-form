package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;

class AbsoluteTextPdfFormWriter extends PdfFormWriter<AbsoluteTextPdfElement> {

    AbsoluteTextPdfFormWriter(AbsoluteTextPdfElement pdfElement, PdfWriter pdfWriter) {
        super(pdfElement, pdfWriter);
    }

    @Override
    void writeWithContent(String content) {
        if (!pdfElement.getFontSize().isPresent()) {
            throw new IllegalStateException("Pdf element have to have font size!");
        }
        try {
            PdfPosition position = pdfElement.getPdfPosition();
            PdfContentByte cb = pdfWriter.getDirectContent();
            BaseFont bf = new Config().baseFont;
            cb.saveState();
            cb.beginText();
            cb.moveText(position.getX(), position.getY());
            cb.setFontAndSize(bf, pdfElement.getFontSize().get().getValue());
            cb.showText(content);
            cb.endText();
            cb.restoreState();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
