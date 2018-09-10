package io.github.nowakprojects.pdfitextform;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class AbsoluteTextPdfElement implements PdfElement {
    private final String tag;
    private final String content;
    private final PdfPosition pdfPosition;
    private float characterWidth;

    private AbsoluteTextPdfElement(String tag, String content, PdfPosition pdfPosition) {
        this.tag = tag;
        this.content = content;
        this.pdfPosition = pdfPosition;
    }

    String getTag() {
        return tag;
    }

    String getContent() {
        return content;
    }

    List<String> getContentLetters() {
        return content.chars()
                .mapToObj(c -> (char) c)
                .map(Object::toString)
                .collect(Collectors.toList());

    }

    float getX() {
        return this.pdfPosition.getX();
    }

    float getY() {
        return this.pdfPosition.getY();
    }

    float getCharacterWidth() {
        return characterWidth;
    }

    AbsoluteTextPdfElement changePosition(PdfPosition pdfPosition) {
        return new AbsoluteTextPdfElement(this.tag, this.content, pdfPosition);
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

    //FIXME: Space is from the beggining, not between. How to get font size width? Or leave it and rename!
    AbsoluteTextPdfElement withCharacterWidth(float spaceBetweenLetters) {
        AbsoluteTextPdfElement absoluteTextPdfElement = new AbsoluteTextPdfElement(this.tag, this.content, this.pdfPosition);
        absoluteTextPdfElement.characterWidth = spaceBetweenLetters;
        return absoluteTextPdfElement;
    }


    boolean isTextWithSpaceBetweenLetters() {
        return this.characterWidth != 0;
    }

    static NeedTag builder() {
        return new Builder();
    }

    static class Builder implements NeedTag, NeedContent, NeedPosition {
        private String tag;
        private String content;
        private PdfPosition pdfPosition;

        @Override
        public NeedContent withTag(String tag) {
            this.tag = tag;
            return this;
        }

        @Override
        public NeedPosition andContent(String content) {
            this.content = content;
            return this;
        }

        @Override
        public AbsoluteTextPdfElement positionedFromBottomLeft(float x, float y) {
            return this.positionedOn(
                    PdfPositionFactory.getPosition(PositionType.FROM_BOTTOM_LEFT)
                            .withCoordinates(x, y)
            );
        }

        @Override
        public AbsoluteTextPdfElement positionedFromTopLeft(float x, float y) {
            return this.positionedOn(
                    PdfPositionFactory.getPosition(PositionType.FROM_TOP_LEFT)
                            .withCoordinates(x, y)
            );
        }

        private AbsoluteTextPdfElement positionedOn(PdfPosition pdfPosition) {
            this.pdfPosition = pdfPosition;
            return new AbsoluteTextPdfElement(this.tag, this.content, this.pdfPosition);
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
}

interface NeedTag {
    NeedContent withTag(String tag);
}

interface NeedContent {
    NeedPosition andContent(String content);
}

interface NeedPosition {
    AbsoluteTextPdfElement positionedFromBottomLeft(float x, float y);

    AbsoluteTextPdfElement positionedFromTopLeft(float x, float y);
}