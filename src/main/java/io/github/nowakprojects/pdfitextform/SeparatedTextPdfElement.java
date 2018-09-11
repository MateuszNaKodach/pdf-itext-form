package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.pdf.PdfWriter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Marcin
 */
public class SeparatedTextPdfElement implements PdfElement {
    private final String tag;
    private final String content;
    private final PdfPosition pdfPosition;
    private final float fontSize;
    private final float characterWidth;

    SeparatedTextPdfElement(String tag, String content, PdfPosition pdfPosition, float fontSize, float characterWidth) {
        this.tag = tag;
        this.content = content;
        this.pdfPosition = pdfPosition;
        this.fontSize = fontSize;
        this.characterWidth = characterWidth;
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

    public float getCharacterWidth() {
        return characterWidth;
    }

    Set<SimpleTextPdfElement> getSimpleElements() {

        Set<SimpleTextPdfElement> elements = new HashSet<>();
        float shift = 0;

        char[] array = content.toCharArray();
        for (int i = 0; i < array.length; i++) {
            elements.add(new SimpleTextPdfElement(tag + "_" + i, String.valueOf(array[i]),
                    getShiftedPosition(shift), fontSize));
            shift += characterWidth;
        }

        return elements;
    }

    private PdfPosition getShiftedPosition(float shift) {
        return PdfPositionFactory.getPosition(PositionType.FROM_BOTTOM_LEFT)
                .withCoordinates(pdfPosition.getX() + shift, pdfPosition.getY());
    }

    @Override
    public void print(PdfWriter writer) {
        getSimpleElements().forEach(element -> element.print(writer));
    }

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
    }
}
