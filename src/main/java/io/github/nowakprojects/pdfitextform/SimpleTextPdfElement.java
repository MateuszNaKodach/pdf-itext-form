package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;

/**
 * Created by Marcin
 */
class SimpleTextPdfElement implements PdfElement {
    private final String tag;
    private final String content;
    private final PdfPosition pdfPosition;
    private final float fontSize;

    SimpleTextPdfElement(String tag, String content, PdfPosition pdfPosition, float fontSize) {
        this.tag = tag;
        this.content = content;
        this.pdfPosition = pdfPosition;
        this.fontSize = fontSize;
    }

    public String getTag() {
        return tag;
    }

    public String getContent() {
        return content;
    }

    public PdfPosition getPdfPosition() {
        return pdfPosition;
    }

    public float getFontSize() {
        return fontSize;
    }

    @Override
    public void print(PdfWriter writer) {
        try {
            PdfPosition position = pdfPosition;
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf = new Config().baseFont;
            cb.saveState();
            cb.beginText();
            cb.moveText(position.getX(), position.getY());
            cb.setFontAndSize(bf, fontSize);
            cb.showText(content);
            cb.endText();
            cb.restoreState();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    static class Configuration implements PdfElementCreator {

        private final String tag;
        private final PdfPosition pdfPosition;
        private final float fontSize;

        Configuration(String tag, PdfPosition pdfPosition, float fontSize) {
            this.fontSize = fontSize;
            this.pdfPosition = pdfPosition;
            this.tag = tag;
        }

        @Override
        public SimpleTextPdfElement create(String content) {
            return new SimpleTextPdfElement(tag, content, pdfPosition, fontSize);
        }

        @Override
        public String getTag() {
            return tag;
        }

        @Override
        public void printTemplate(PdfWriter writer) {
            create(tag + "_example").print(writer);
        }

    }

}
