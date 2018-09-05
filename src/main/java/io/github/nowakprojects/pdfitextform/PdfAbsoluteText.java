package io.github.nowakprojects.pdfitextform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PdfAbsoluteText implements PdfElement {
    private final String tag;
    private final String content;
    private final PdfPosition pdfPosition;
    private float spaceBetweenLetters;

    private PdfAbsoluteText(String tag, String content, PdfPosition pdfPosition) {
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
                .map(c -> c.toString())
                .collect(Collectors.toList());

    }

    float getX() {
        return this.pdfPosition.getX();
    }

    float getY() {
        return this.pdfPosition.getY();
    }

    float getSpaceBetweenLetters() {
        return spaceBetweenLetters;
    }

    PdfAbsoluteText changePosition(PdfPosition pdfPosition) {
        return new PdfAbsoluteText(this.tag, this.content, pdfPosition);
    }

    PdfAbsoluteText changeX(float newX) {
        return changePosition(
                PdfPositionFactory
                        .getPosition(this.pdfPosition.getPositionType())
                        .withCoordinates(newX, getY())
        );
    }

    PdfAbsoluteText changeY(float newY) {
        return changePosition(
                PdfPositionFactory
                        .getPosition(this.pdfPosition.getPositionType())
                        .withCoordinates(getX(), newY)
        );
    }

    PdfAbsoluteText withSpaceBetweenLetters(float spaceBetweenLetters) {
        PdfAbsoluteText pdfAbsoluteText = new PdfAbsoluteText(this.tag, this.content, this.pdfPosition);
        pdfAbsoluteText.spaceBetweenLetters = spaceBetweenLetters;
        return pdfAbsoluteText;
    }


    boolean isTextWithSpaceBetweenLetters() {
        return this.spaceBetweenLetters != 0;
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
        public PdfAbsoluteText positionedFromBottomLeft(float x, float y) {
            return this.positionedOn(
                    PdfPositionFactory.getPosition(PositionType.FROM_BOTTOM_LEFT)
                            .withCoordinates(x, y)
            );
        }

        @Override
        public PdfAbsoluteText positionedFromTopLeft(float x, float y) {
            return this.positionedOn(
                    PdfPositionFactory.getPosition(PositionType.FROM_TOP_LEFT)
                            .withCoordinates(x, y)
            );
        }

        private PdfAbsoluteText positionedOn(PdfPosition pdfPosition) {
            this.pdfPosition = pdfPosition;
            return new PdfAbsoluteText(this.tag, this.content, this.pdfPosition);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PdfAbsoluteText)) return false;
        PdfAbsoluteText that = (PdfAbsoluteText) o;
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
    PdfAbsoluteText positionedFromBottomLeft(float x, float y);

    PdfAbsoluteText positionedFromTopLeft(float x, float y);

}