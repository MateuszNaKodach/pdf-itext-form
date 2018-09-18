package io.github.nowakprojects.pdfitextform;

import java.util.Objects;

class CheckBoxPdfElement extends AbstractPdfElement<CheckBoxPdfElement> {

    private CheckBoxPdfElement(String tag, PdfPosition pdfPosition) {
        super(tag, pdfPosition);
    }

    private CheckBoxPdfElement(String tag, PdfPosition pdfPosition, FontSize customFontSize) {
        super(tag, pdfPosition, customFontSize);
    }

    private CheckBoxPdfElement(String tag, PdfPosition pdfPosition, FontSize customFontSize, String defaultContent, Template template) {
        super(tag, pdfPosition, customFontSize, defaultContent, template);
    }

    public CheckBoxPdfElement withFontSize(FontSize fontSize) {
        return new CheckBoxPdfElement(getTag(), getPdfPosition(), fontSize, this.defaultContent, this.template);
    }

    CheckBoxPdfElement changeX(float newX) {
        return changePosition(PdfPositionFactory.getBottomLeftPdfPosition(newX,getY()));
    }

    CheckBoxPdfElement changeY(float newY) {
        return changePosition(PdfPositionFactory.getBottomLeftPdfPosition(getX(),newY));
    }

    private CheckBoxPdfElement changePosition(PdfPosition pdfPosition) {
        return new CheckBoxPdfElement(this.tag, pdfPosition);
    }

    @Override
    public CheckBoxPdfElement withDefaultContent(String defaultContent) {
        return new CheckBoxPdfElement(this.tag, this.pdfPosition, this.fontSize, defaultContent, this.template);
    }

    static NeedTag builder() {
        return new Builder();
    }

    static class Builder implements NeedTag, NeedPosition {
        private String tag;
        private PdfPosition pdfPosition;

        @Override
        public NeedPosition withTag(String tag) {
            this.tag = tag;
            return this;
        }

        @Override
        public CheckBoxPdfElement positionedFromBottomLeft(float x, float y) {
            return this.positionedOn(PdfPositionFactory.getBottomLeftPdfPosition(x,y));
        }

        private CheckBoxPdfElement positionedOn(PdfPosition pdfPosition) {
            this.pdfPosition = pdfPosition;
            return new CheckBoxPdfElement(this.tag, this.pdfPosition);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckBoxPdfElement)) return false;
        CheckBoxPdfElement that = (CheckBoxPdfElement) o;
        return Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag);
    }

    interface NeedTag {
        NeedPosition withTag(String tag);
    }

    interface NeedPosition {
        CheckBoxPdfElement positionedFromBottomLeft(float x, float y);
    }
}





