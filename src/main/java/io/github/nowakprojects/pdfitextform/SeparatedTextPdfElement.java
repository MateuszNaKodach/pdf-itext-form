package io.github.nowakprojects.pdfitextform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcin
 */
public class SeparatedTextPdfElement implements PdfElement, ComplicatedPdfElement {
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

    @Override
    public List<SimpleTextPdfElement> getSimpleTextElements() {

        List<SimpleTextPdfElement> elements = new ArrayList<>();
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
    }
}
