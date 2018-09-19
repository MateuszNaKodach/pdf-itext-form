package io.github.nowakprojects.pdfitextform;


import java.util.Objects;

public class SeparatedTextPdfElement extends AbstractPdfElement<SeparatedTextPdfElement> {
    private final float characterWidth;

    private SeparatedTextPdfElement(String tag, PdfPosition pdfPosition, float characterWidth, FontSize fontSize) {
        super(tag, pdfPosition, fontSize);
        this.characterWidth = characterWidth;
    }

    private SeparatedTextPdfElement(
            String tag,
            PdfPosition pdfPosition,
            float characterWidth,
            FontSize fontSize,
            String defaultContent,
            Template template) {
        super(tag, pdfPosition, fontSize, defaultContent, template);
        this.characterWidth = characterWidth;
    }

    public float getCharacterWidth() {
        return characterWidth;
    }

    @Override
    public SeparatedTextPdfElement withFontSize(FontSize fontSize) {
        return new SeparatedTextPdfElement(tag, getPdfPosition(), characterWidth, fontSize, this.defaultContent, this.template);
    }

    @Override
    public SeparatedTextPdfElement withDefaultContent(String defaultContent) {
        return new SeparatedTextPdfElement(this.tag, this.pdfPosition, this.characterWidth, this.fontSize, defaultContent, this.template);
    }

    static SeparatedTextPdfElement.NeedTag builder() {
        return new SeparatedTextPdfElement.Builder();
    }

    static class Builder implements NeedTag, NeedCharacterWidth, NeedPosition {
        private String tag;
        private PdfPosition pdfPosition;
        private float characterWidth;

        @Override
        public NeedCharacterWidth withTag(String tag) {
            this.tag = tag;
            return this;
        }

        @Override
        public NeedPosition withCharacterWidth(float characterWidth) {
            this.characterWidth = characterWidth;
            return this;
        }

        @Override
        public SeparatedTextPdfElement positionedFromBottomLeft(float x, float y) {
            return this.positionedOn(PdfPositionFactory.getBottomLeftPdfPosition(x, y));
        }

        private SeparatedTextPdfElement positionedOn(PdfPosition pdfPosition) {
            this.pdfPosition = pdfPosition;
            return new SeparatedTextPdfElement(this.tag, this.pdfPosition, this.characterWidth, null);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeparatedTextPdfElement)) return false;
        SeparatedTextPdfElement that = (SeparatedTextPdfElement) o;
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
        NeedPosition withCharacterWidth(float characterWidth);
    }

    interface NeedPosition {
        SeparatedTextPdfElement positionedFromBottomLeft(float x, float y);
    }
}
