package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;

class MultilineTextPdfFormWriter extends PdfFormWriter<MultilineTextPdfElement, String> {

    MultilineTextPdfFormWriter(MultilineTextPdfElement pdfElement, String content) {
        super(pdfElement, content);
    }

    @Override
    void writeOn(PdfWriter pdfWriter) {
        boolean printed = false;
        for (float fs = pdfElement.getFontSize().get().getValue(); fs > 2 && !printed; fs -= 1) {
            if (isOkForFontSize(content, fs, pdfWriter)) {
                printForSize(content, fs, pdfWriter);
                printed = true;
            }
        }
        if (!printed) {
            System.out.println("Too more text for field " + pdfElement.getTag());
        }
    }

    private int printForFontSize(String content, float fontSize, PdfWriter writer, boolean simulate) {
        try {
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf = new Config().baseFont;
            cb.setFontAndSize(bf, fontSize);
            ColumnText columnText = new ColumnText(cb);
            Rectangle rectangle = new Rectangle(
                    pdfElement.getPdfPosition().getX(),
                    pdfElement.getPdfPosition().getY() - pdfElement.getMaxHeight(),
                    pdfElement.getY() + pdfElement.getMaxWidth(),
                    pdfElement.getY()
            );
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
}
