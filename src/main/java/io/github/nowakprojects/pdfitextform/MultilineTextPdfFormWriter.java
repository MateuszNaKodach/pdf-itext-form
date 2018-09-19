package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import io.github.nowakprojects.Config;

class MultilineTextPdfFormWriter extends PdfFormWriter<MultilineTextPdfElement, String> {

    MultilineTextPdfFormWriter(MultilineTextPdfElement pdfElement, String content) {
        super(pdfElement, content);
    }

    @Override
    void writeOn(PdfWriter pdfWriter) {
        boolean printed = false;
        for (float fontSize = pdfElement.getFontSize().get().getValue(); fontSize > FontSize.MIN_FONT_SIZE && !printed; fontSize -= 1) {
            if (isEnoughSpaceFor(content, fontSize, pdfWriter)) {
                print(content, fontSize, pdfWriter);
                printed = true;
            }
        }
        if (!printed) {
            throw new PdfFillingException("Too more text for field with tag" + pdfElement.getTag());
        }
    }

    private boolean isEnoughSpaceFor(String content, float size, PdfWriter writer) {
        return print(content, size, writer, true) == ColumnText.NO_MORE_TEXT;
    }

    private int print(String content, float fontSize, PdfWriter writer, boolean simulate) {
        try {
            PdfContentByte cb = writer.getDirectContent();
            BaseFont baseFont = Config.baseFont;
            cb.setFontAndSize(baseFont, fontSize);
            ColumnText columnText = new ColumnText(cb);
            Rectangle rectangle = new Rectangle(
                    pdfElement.getPdfPosition().getX(),
                    pdfElement.getPdfPosition().getY() - pdfElement.getMaxHeight(),
                    pdfElement.getX() + pdfElement.getMaxWidth(),
                    pdfElement.getY()
            );
            columnText.setSimpleColumn(rectangle);
            // TODO: 11.09.2018 analyse what exactly is leading
            columnText.setAlignment(Element.ALIGN_TOP);
            columnText.setLeading(fontSize);
            columnText.addText(new Phrase(content, new Font(Config.baseFont, fontSize)));
            return columnText.go(simulate);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void print(String content, float size, PdfWriter writer) {
        print(content, size, writer, false);
    }
}
