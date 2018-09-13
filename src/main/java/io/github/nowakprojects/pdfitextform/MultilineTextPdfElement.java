package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;

class MultilineTextPdfElement extends AbstractPdfElement<MultilineTextPdfElement> implements PdfElementWriter<MultilineTextPdfElement> {
    private final float maxHeight;
    private final float maxWidth;

    MultilineTextPdfElement(
            String tag,
            PdfPosition pdfPosition,
            float maxHeight,
            float maxWidth,
            FontSize customFontSize)
    {
        super(tag, pdfPosition, customFontSize);
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
    }

    float getMaxHeight() {
        return maxHeight;
    }

    float getMaxWidth() {
        return maxWidth;
    }

    @Override
    public MultilineTextPdfElement withFontSize(FontSize fontSize) {
        return new MultilineTextPdfElement(tag, pdfPosition, maxHeight, maxWidth, fontSize);
    }

    public void writePdfElement(PdfWriter writer) {
        this.writePdfElement(writer, this);
    }

    @Override
    public void writePdfElement(PdfWriter pdfWriter, MultilineTextPdfElement pdfElement) {
        /*boolean printed = false;
        for (float fs = fontSize; fs > 2 && !printed; fs -= 1) {
            if (isOkForFontSize(fs, pdfWriter)) {
                printForSize(fs, pdfWriter);
                printed = true;
            }
        }
        if (!printed) {
            System.out.println("Too more text for field " + tag);
        }*/
    }


    public void writePdfElement(String content, PdfWriter pdfWriter) {
        boolean printed = false;
        for (float fs = fontSize.getValue(); fs > 2 && !printed; fs -= 1) {
            if (isOkForFontSize(content, fs, pdfWriter)) {
                printForSize(content, fs, pdfWriter);
                printed = true;
            }
        }
        if (!printed) {
            System.out.println("Too more text for field " + tag);
        }
    }

    private int printForFontSize(String content, float fontSize, PdfWriter writer, boolean simulate) {
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

    private boolean isOkForFontSize(String content, float size, PdfWriter writer) {
        return printForFontSize(content, size, writer, true) == ColumnText.NO_MORE_TEXT;
    }

    private void printForSize(String content, float size, PdfWriter writer) {
        printForFontSize(content, size, writer, false);
    }

    /*static class Configuration implements PdfElementCreator {

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
    }*/

}
