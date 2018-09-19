package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import io.github.nowakprojects.Config;

class CheckBoxPdfFormWriter extends PdfFormWriter<CheckBoxPdfElement, Boolean> {

    CheckBoxPdfFormWriter(CheckBoxPdfElement pdfElement, String content) {
        super(pdfElement, Boolean.valueOf(content));
    }

    CheckBoxPdfFormWriter(CheckBoxPdfElement pdfElement, boolean content) {
        super(pdfElement, content);
    }

    @Override
    void writeOn(PdfWriter pdfWriter) {
        PdfPosition position = pdfElement.getPdfPosition();
        PdfContentByte cb = pdfWriter.getDirectContent();
        BaseFont bf = Config.baseFont;
        cb.saveState();
        cb.beginText();
        cb.moveText(position.getX(), position.getY());
        cb.setFontAndSize(bf, ((FontSize) pdfElement.getFontSize().get()).getValue());
        cb.showText(content ? "X" : "");
        cb.endText();
        cb.restoreState();
    }


}
