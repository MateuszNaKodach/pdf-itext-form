package io.github.nowakprojects.pdfitextform;

import java.util.Objects;

class AbsoluteTextPdfElement extends AbstractPdfElement<AbsoluteTextPdfElement> {

    private AbsoluteTextPdfElement(String tag, PdfPosition pdfPosition) {
        super(tag, pdfPosition);
    }

    AbsoluteTextPdfElement(String tag, PdfPosition pdfPosition, FontSize customFontSize) {
        super(tag, pdfPosition, customFontSize);
    }

    private AbsoluteTextPdfElement(String tag, PdfPosition pdfPosition, FontSize customFontSize, String defaultContent) {
        super(tag, pdfPosition, customFontSize, defaultContent);
    }

    public AbsoluteTextPdfElement withFontSize(FontSize fontSize) {
        return new AbsoluteTextPdfElement(getTag(), getPdfPosition(), fontSize, this.defaultContent);
    }

    AbsoluteTextPdfElement changeX(float newX) {
        return changePosition(PdfPositionFactory.getBottomLeftPdfPosition(newX, getY()));
    }

    AbsoluteTextPdfElement changeY(float newY) {
        return changePosition(PdfPositionFactory.getBottomLeftPdfPosition(getX(), newY));
    }

    private AbsoluteTextPdfElement changePosition(PdfPosition pdfPosition) {
        return new AbsoluteTextPdfElement(this.tag, pdfPosition, this.fontSize, this.defaultContent);
    }

    public AbsoluteTextPdfElement withDefaultContent(String defaultContent) {
        return new AbsoluteTextPdfElement(this.tag, getPdfPosition(), this.fontSize, defaultContent);
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
        public AbsoluteTextPdfElement positionedFromBottomLeft(float x, float y) {
            return this.positionedOn(PdfPositionFactory.getBottomLeftPdfPosition(x, y));
        }

        private AbsoluteTextPdfElement positionedOn(PdfPosition pdfPosition) {
            this.pdfPosition = pdfPosition;
            return new AbsoluteTextPdfElement(this.tag, this.pdfPosition);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbsoluteTextPdfElement)) return false;
        AbsoluteTextPdfElement that = (AbsoluteTextPdfElement) o;
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
        AbsoluteTextPdfElement positionedFromBottomLeft(float x, float y);
    }
}





