package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.pdf.PdfWriter;
import java.util.Objects;

class AbsoluteTextPdfElement extends AbstractPdfElement implements PdfElementWriter<AbsoluteTextPdfElement> {

    private AbsoluteTextPdfElement(String tag, PdfPosition pdfPosition) {
        super(tag, pdfPosition);
    }

    AbsoluteTextPdfElement(String tag, PdfPosition pdfPosition, float customFontSize) {
        super(tag, pdfPosition, customFontSize);
    }

    public String getTag() {
        return tag;
    }

    public PdfPosition getPdfPosition() {
        return pdfPosition;
    }

    AbsoluteTextPdfElement withCustomFontSize(float customFontSize) {
        return new AbsoluteTextPdfElement(this.tag, this.pdfPosition, customFontSize);
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


    @Override
    public void writePdfElement(PdfWriter pdfWriter, AbsoluteTextPdfElement pdfElement) {
        /*try {
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
        }*/ //FIXME: Wrtier to another file!
    }

    public void writePdfElement(PdfWriter writer) {
        this.writePdfElement(writer, this);
    }

    static NeedTag builder() {
        return new Builder();
    }

    static class Builder implements NeedTag, NeedPosition {
        private String tag;
        private String content;
        private PdfPosition pdfPosition;

        @Override
        public NeedPosition withTag(String tag) {
            this.tag = tag;
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
            return new AbsoluteTextPdfElement(tag, pdfPosition, fontSize);
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
    NeedPosition withTag(String tag);
}

interface NeedPosition {
    AbsoluteTextPdfElement positionedFromBottomLeft(float x, float y);
}



