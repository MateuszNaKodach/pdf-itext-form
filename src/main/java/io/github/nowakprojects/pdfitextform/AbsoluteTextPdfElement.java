package io.github.nowakprojects.pdfitextform;

import java.util.Objects;

class AbsoluteTextPdfElement extends AbstractPdfElement<AbsoluteTextPdfElement> {

    private AbsoluteTextPdfElement(String tag, PdfPosition pdfPosition) {
        super(tag, pdfPosition);
    }

    AbsoluteTextPdfElement(String tag, PdfPosition pdfPosition, FontSize customFontSize) {
        super(tag, pdfPosition, customFontSize);
    }

    public String getTag() {
        return tag;
    }

    public PdfPosition getPdfPosition() {
        return pdfPosition;
    }

    public AbsoluteTextPdfElement withFontSize(FontSize fontSize) {
        return new AbsoluteTextPdfElement(this.tag, this.pdfPosition, fontSize);
    }

    AbsoluteTextPdfElement changeX(float newX) {
        return changePosition(
                PdfPositionFactory
                        .getPosition(this.pdfPosition.getPositionType())
                        .withCoordinates(newX, getY())
        );
    }

    AbsoluteTextPdfElement changeY(float newY) {
        return changePosition(
                PdfPositionFactory
                        .getPosition(this.pdfPosition.getPositionType())
                        .withCoordinates(getX(), newY)
        );
    }

    private AbsoluteTextPdfElement changePosition(PdfPosition pdfPosition) {
        return new AbsoluteTextPdfElement(this.tag, pdfPosition);
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
            return this.positionedOn(PdfPositionFactory.getBottomLeftPdfPosition(x,y));
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





