package io.github.nowakprojects.pdfitextform;


import java.util.Objects;

class DatePdfElement extends AbstractPdfElement<DatePdfElement> {

    private final float characterWidth;
    private final float spaceBetweenGroup;

    private DatePdfElement(
            String tag,
            PdfPosition pdfPosition,
            float characterWidth,
            float spaceBetweenGroup,
            FontSize fontSize) {
        super(tag, pdfPosition, fontSize);
        this.characterWidth = characterWidth;
        this.spaceBetweenGroup = spaceBetweenGroup;
    }

    float getCharacterWidth() {
        return characterWidth;
    }

    float getSpaceBetweenGroup() {
        return spaceBetweenGroup;
    }

    @Override
    public DatePdfElement withFontSize(FontSize fontSize) {
        return new DatePdfElement(tag, getPdfPosition(), characterWidth, spaceBetweenGroup, fontSize);
    }

    static NeedTag builder() {
        return new DatePdfElement.Builder();
    }

    static class Builder implements NeedTag, NeedCharacterWidth, NeedSpaceBetweenGroup, NeedPosition {
        private String tag;
        private PdfPosition pdfPosition;
        private float characterWidth;
        private float spaceBetweenGroup;

        @Override
        public NeedCharacterWidth withTag(String tag) {
            this.tag = tag;
            return this;
        }

        @Override
        public NeedSpaceBetweenGroup withCharacterWidth(float characterWidth) {
            this.characterWidth = characterWidth;
            return this;
        }

        @Override
        public NeedPosition withSpaceBetweenGroup(float spaceBetweenGroup) {
            this.spaceBetweenGroup = spaceBetweenGroup;
            return this;
        }

        @Override
        public DatePdfElement positionedFromBottomLeft(float x, float y) {
            return this.positionedOn(PdfPositionFactory.getBottomLeftPdfPosition(x, y));
        }

        private DatePdfElement positionedOn(PdfPosition pdfPosition) {
            this.pdfPosition = pdfPosition;
            return new DatePdfElement(this.tag, this.pdfPosition, this.characterWidth, this.spaceBetweenGroup, null);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatePdfElement)) return false;
        DatePdfElement that = (DatePdfElement) o;
        return Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag);
    }

    interface NeedTag {
        NeedCharacterWidth withTag(String tag);
    }

    interface NeedCharacterWidth {
        NeedSpaceBetweenGroup withCharacterWidth(float characterWidth);
    }

    interface NeedSpaceBetweenGroup {
        NeedPosition withSpaceBetweenGroup(float characterWidth);
    }

    interface NeedPosition {
        DatePdfElement positionedFromBottomLeft(float x, float y);
    }
}
