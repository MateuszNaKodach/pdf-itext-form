package io.github.nowakprojects.pdfitextform;

import java.util.Optional;


abstract class AbstractPdfElement<T> implements PdfElement<T> {

    protected final String tag;
    private final PdfPosition pdfPosition;
    private final FontSize fontSize;

    AbstractPdfElement(String tag, PdfPosition pdfPosition) {
        this.tag = tag;
        this.pdfPosition = pdfPosition;
        this.fontSize = null;
    }

    AbstractPdfElement(String tag, PdfPosition pdfPosition, FontSize fontSize) {
        this.tag = tag;
        this.pdfPosition = pdfPosition;
        this.fontSize = fontSize;
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public PdfPosition getPdfPosition() {
        return pdfPosition;
    }

    @Override
    public Optional<FontSize> getFontSize() {
        return Optional.ofNullable(fontSize);
    }

}

