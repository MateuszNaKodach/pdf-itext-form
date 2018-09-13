package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.pdf.PdfWriter;

import java.util.HashSet;
import java.util.Set;

public class SeparatedTextPdfElement extends AbstractPdfElement<SeparatedTextPdfElement> {
    private final float characterWidth;

    SeparatedTextPdfElement(String tag, PdfPosition pdfPosition, float customFontSize, float characterWidth) {
        super(tag, pdfPosition, customFontSize);
        this.characterWidth = characterWidth;
    }

    public String getTag() {
        return tag;
    }

    public float getCharacterWidth() {
        return characterWidth;
    }

    @Override
    public SeparatedTextPdfElement withCustomFontSize(float customFontSize) {
        return new SeparatedTextPdfElement(tag, pdfPosition, customFontSize, characterWidth);
    }

    @Override
    public void writePdfElement(String content, PdfWriter pdfWriter) {

    }

    //FIXME: Fix it!
    /*
    Set<AbsoluteTextPdfElement> getSimpleElementsForContent(String content) {

        Set<AbsoluteTextPdfElement> elements = new HashSet<>();
        float shift = 0;

        char[] array = content.toCharArray();
        for (int i = 0; i < array.length; i++) {
            elements.add(new AbsoluteTextPdfElement(tag + "_" + i, String.valueOf(array[i]),
                    getShiftedPosition(shift), customFontSize));
            shift += characterWidth;
        }

        return elements;
    }

    private PdfPosition getShiftedPosition(float shift) {
        return PdfPositionFactory.getPosition(PositionType.FROM_BOTTOM_LEFT)
                .withCoordinates(pdfPosition.getX() + shift, pdfPosition.getY());
    }*/

    @Override
    public void writePdfElement(PdfWriter writer) {
        //getSimpleElements().forEach(element -> element.writePdfElement(writer));
    }

    /*
    static class Configuration implements PdfElementCreator {

        private final String tag;
        private final PdfPosition pdfPosition;
        private final float fontSize;
        private final float characterWidth;

        Configuration(String tag, PdfPosition pdfPosition, float fontSize, float characterWidth) {
            this.fontSize = fontSize;
            this.pdfPosition = pdfPosition;
            this.tag = tag;
            this.characterWidth = characterWidth;
        }

        @Override
        public SeparatedTextPdfElement create(String content) {
            return new SeparatedTextPdfElement(tag, content, pdfPosition, fontSize, characterWidth);
        }

        @Override
        public String getTag() {
            return tag;
        }

        @Override
        public void printTemplate(PdfWriter writer) {
            create("12345678901").writePdfElement(writer);
        }

    }*/
}
