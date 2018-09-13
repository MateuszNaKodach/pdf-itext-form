package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.pdf.PdfWriter;


class SeparatedTextPdfFormWriter extends PdfFormWriter<SeparatedTextPdfElement, String> {

    SeparatedTextPdfFormWriter(SeparatedTextPdfElement pdfElement, String content) {
        super(pdfElement, content);
    }

    @Override
    void writeOn(PdfWriter pdfWriter) {
        float characterWidth = pdfElement.getCharacterWidth();
        String tag = pdfElement.getTag();
        float shift = 0;
        char[] array = content.toCharArray();
        for (int i = 0; i < array.length; i++) {
            final AbsoluteTextPdfElement absoluteTextPdfElement = new AbsoluteTextPdfElement(
                    tag + "_" + i,
                    getShiftedPosition(shift),
                    pdfElement.getFontSize().get()
            );

            new SimplePdfFormWriter(absoluteTextPdfElement, String.valueOf(array[i])).writeOn(pdfWriter);

            shift += characterWidth;
        }
    }


    private PdfPosition getShiftedPosition(float shift) {
        return PdfPositionFactory.getPosition(PositionType.FROM_BOTTOM_LEFT)
                .withCoordinates(pdfElement.getX() + shift, pdfElement.getY());
    }
}
