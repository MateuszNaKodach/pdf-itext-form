package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;

class MultilineTextPdfElement extends AbstractPdfElement implements PdfElementWriter<MultilineTextPdfElement> {
    private final String content;
    private final float maxHeight;
    private final float maxWidth;

    MultilineTextPdfElement(String tag, String content, PdfPosition pdfPosition, float customFontSize, float maxHeight,
                            float maxWidth) {
        super(tag, pdfPosition, customFontSize);
        this.content = content;
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
    }

    String getContent() {
        return content;
    }

    float getMaxHeight() {
        return maxHeight;
    }

    float getMaxWidth() {
        return maxWidth;
    }

    @Override
    public void writePdfElement(PdfWriter writer) {
        this.writePdfElement(writer, this);
    }

    @Override
    public void writePdfElement(PdfWriter pdfWriter, MultilineTextPdfElement pdfElement) {
        boolean printed = false;
        for (float fs = customFontSize; fs > 2 && !printed; fs -= 1) {
            if (isOkForFontSize(fs, pdfWriter)) {
                printForSize(fs, pdfWriter);
                printed = true;
            }
        }
        if (!printed) {
            System.out.println("Too more text for field " + tag);
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
            columnText.addText(new Phrase(content, new Font(new Config().baseFont, fontSize)));
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
            Rectangle rectangle = new Rectangle(pdfPosition.getX(), pdfPosition.getY() - maxHeight,
                    pdfPosition.getX() + maxWidth, pdfPosition.getY());
            rectangle.setBorderWidth(2);
            rectangle.setBorder(Rectangle.BOX);
            rectangle.setBorderColor(BaseColor.RED);

            String text = "Test test, halo raz dwa trzy, raz dwa, dwa śćńżł... ";

            new MultilineTextPdfElement(tag, text + text + text + text + text + text + text + text,
                    pdfPosition, fontSize, maxHeight, maxWidth).writePdfElement(writer);
            PdfContentByte cb = writer.getDirectContent();
            cb.rectangle(rectangle);
        }
    }

}
