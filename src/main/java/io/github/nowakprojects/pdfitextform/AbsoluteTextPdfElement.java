package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by Marcin
 */
class AbsoluteTextPdfElement extends AbstractPdfElement implements PdfElementWriter<AbsoluteTextPdfElement> {
    private final String content;
    private final Float customFontSize;

    private AbsoluteTextPdfElement(String tag, String content, PdfPosition pdfPosition) {
        super(tag, pdfPosition);
        this.content = content;
        this.customFontSize = null;
    }

    AbsoluteTextPdfElement(String tag, String content, PdfPosition pdfPosition, float customFontSize) {
        super(tag, pdfPosition);
        this.content = content;
        this.customFontSize = customFontSize;
    }

    public String getTag() {
        return tag;
    }

    String getContent() {
        return content;
    }

    public PdfPosition getPdfPosition() {
        return pdfPosition;
    }

    Optional<Float> getCustomFontSize() {
        return Optional.ofNullable(customFontSize);
    }

    AbsoluteTextPdfElement withCustomFontSize(float customFontSize) {
        return new AbsoluteTextPdfElement(this.tag, this.content, this.pdfPosition, customFontSize);
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
        return new AbsoluteTextPdfElement(this.tag, this.content, pdfPosition);
    }


    @Override
    public void writePdfElement(PdfWriter pdfWriter, AbsoluteTextPdfElement pdfElement) {
        try {
            PdfPosition position = pdfPosition;
            PdfContentByte cb = pdfWriter.getDirectContent();
            BaseFont bf = new Config().baseFont;
            cb.saveState();
            cb.beginText();
            cb.moveText(position.getX(), position.getY());
            cb.setFontAndSize(bf, customFontSize);
            cb.showText(content);
            cb.endText();
            cb.restoreState();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    public void writePdfElement(PdfWriter writer) {
        this.writePdfElement(writer, this);
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


    static class Configuration implements PdfElementCreator {

        private final String tag;
        private final PdfPosition pdfPosition;
        private final float fontSize;

        Configuration(String tag, PdfPosition pdfPosition, float fontSize) {
            this.fontSize = fontSize;
            this.pdfPosition = pdfPosition;
            this.tag = tag;
        }

        @Override
        public AbsoluteTextPdfElement create(String content) {
            return new AbsoluteTextPdfElement(tag, content, pdfPosition, fontSize);
        }

        @Override
        public String getTag() {
            return tag;
        }

        @Override
        public void printTemplate(PdfWriter writer) {
            create(tag + "_example").writePdfElement(writer);
        }

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
}



