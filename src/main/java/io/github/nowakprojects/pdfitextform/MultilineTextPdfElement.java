package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;

/**
 * Created by Marcin
 */
public class MultilineTextPdfElement implements PdfElement {
    private final String tag;
    private final String content;
    private final PdfPosition pdfPosition;
    private final float fontSize;
    private final float maxHeight;
    private final float maxWidth;

    MultilineTextPdfElement(String tag, String content, PdfPosition pdfPosition, float fontSize, float maxHeight,
                            float maxWidth) {
        this.tag = tag;
        this.content = content;
        this.pdfPosition = pdfPosition;
        this.fontSize = fontSize;
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
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

    public float getMaxHeight() {
        return maxHeight;
    }

    public float getMaxWidth() {
        return maxWidth;
    }

    @Override
    public void print(PdfWriter writer) {
        boolean printed = false;
        for (float fs = fontSize; fs > 6 && !printed; fs -= 1) {
            System.out.println("loop");
            if (isOkForFontSize(fs, writer)) {
                System.out.println("inIf");
                printForSize(fs, writer);
                System.out.println("printed");
                printed = true;
            }
        }
        if (!printed) {
            System.out.println("Za dużo tekstu na pole");
        }
    }

    private int printForFontSize(float fontSize, PdfWriter writer, boolean simulate) {
        try {
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf = new Config().baseFont;
            cb.setFontAndSize(bf, fontSize);
            ColumnText columnText = new ColumnText(cb);
            Rectangle rectangle = new Rectangle(pdfPosition.getX(), pdfPosition.getY() - maxHeight,
                    pdfPosition.getX() + maxWidth, pdfPosition.getY());
            columnText.setSimpleColumn(rectangle);
            // TODO: 11.09.2018 analyse what exactly is leading
            columnText.setLeading(fontSize);
            columnText.addText(new Phrase(content));
            return columnText.go(simulate);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private boolean isOkForFontSize(float size, PdfWriter writer) {
        return printForFontSize(size, writer, true) == ColumnText.NO_MORE_TEXT;
    }

    private void printForSize(float size, PdfWriter writer) {
        printForFontSize(size, writer, false);
    }

    static class Configuration implements PdfElementCreator {

        private final String tag;
        private final PdfPosition pdfPosition;
        private final float fontSize;
        private final float maxHeight;
        private final float maxWidth;

        Configuration(String tag, PdfPosition pdfPosition, float fontSize, float maxHeight, float maxWidth) {
            this.fontSize = fontSize;
            this.pdfPosition = pdfPosition;
            this.tag = tag;
            this.maxHeight = maxHeight;
            this.maxWidth = maxWidth;
        }

        @Override
        public MultilineTextPdfElement create(String content) {
            return new MultilineTextPdfElement(tag, content, pdfPosition, fontSize, maxHeight, maxWidth);
        }

        @Override
        public String getTag() {
            return tag;
        }

        @Override
        public void printTemplate(PdfWriter writer) {
            new MultilineTextPdfElement(tag, "1 2 3 4 5 6 7 8 9 1 2 3 4 5 6 7 8 9 1 2 3 4 5 " +
                    "6 7 8 9 1 2 3 4 5 6 7 8 9 1 2 3 4 5 6 7 8 9 1 2 3 4 5 6 7 8 9 1 2 3 4 5 6 7 8 9 ",
                    pdfPosition, fontSize, maxHeight, maxWidth).print(writer);
        }
    }

}
