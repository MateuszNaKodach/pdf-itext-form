package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;
import java.util.Objects;

class MultilineTextPdfElement extends AbstractPdfElement<MultilineTextPdfElement> {
    private final float maxHeight;
    private final float maxWidth;

    private MultilineTextPdfElement(
            String tag,
            PdfPosition pdfPosition,
            float maxHeight,
            float maxWidth,
            FontSize customFontSize) {
        super(tag, pdfPosition, customFontSize);
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
    }

    float getMaxHeight() {
        return maxHeight;
    }

    float getMaxWidth() {
        return maxWidth;
    }

    @Override
    public MultilineTextPdfElement withFontSize(FontSize fontSize) {
        return new MultilineTextPdfElement(tag, pdfPosition, maxHeight, maxWidth, fontSize);
    }

    static NeedTag builder() {
        return new Builder();
    }

    static class Builder implements NeedTag, NeedPosition, NeedMaxSize {
        private String tag;
        private PdfPosition pdfPosition;
        private float maxHeight;
        private float maxWidth;

        @Override
        public NeedMaxSize withTag(String tag) {
            this.tag = tag;
            return this;
        }

        @Override
        public NeedPosition withMaxSize(float maxHeight, float maxWidth) {
            this.maxHeight = maxHeight;
            this.maxWidth = maxWidth;
            return this;
        }

        @Override
        public MultilineTextPdfElement positionedFromBottomLeft(float x, float y) {
            return this.positionedOn(PdfPositionFactory.getBottomLeftPdfPosition(x, y));
        }

        private MultilineTextPdfElement positionedOn(PdfPosition pdfPosition) {
            this.pdfPosition = pdfPosition;
            return new MultilineTextPdfElement(this.tag, this.pdfPosition, this.maxHeight, this.maxWidth, null);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MultilineTextPdfElement)) return false;
        MultilineTextPdfElement that = (MultilineTextPdfElement) o;
        return Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag);
    }

    interface NeedTag {
        NeedMaxSize withTag(String tag);
    }

    interface NeedMaxSize {
        NeedPosition withMaxSize(float maxHeight, float maxWidth);
    }

    interface NeedPosition {
        MultilineTextPdfElement positionedFromBottomLeft(float x, float y);
    }
}



