package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.pdf.PdfWriter;

import java.util.Optional;

abstract class AbstractPdfElement<T> implements PdfElement<T> {

    protected final String tag;
    protected final PdfPosition pdfPosition;
    protected final FontSize customFontSize;

    protected AbstractPdfElement(String tag, PdfPosition pdfPosition) {
        this.tag = tag;
        this.pdfPosition = pdfPosition;
        this.customFontSize = null;
    }

    protected AbstractPdfElement(String tag, PdfPosition pdfPosition, FontSize customFontSize) {
        this.tag = tag;
        this.pdfPosition = pdfPosition;
        this.customFontSize = customFontSize;
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
    public Optional<FontSize> getCustomFontSize() {
        return Optional.ofNullable(customFontSize);
    }

    abstract void writePdfElement(PdfWriter writer);

}

