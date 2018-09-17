package io.github.nowakprojects.pdfitextform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


abstract class AbstractPdfElement<T> implements PdfElement<T> {

    protected final String tag;
    protected final PdfPosition pdfPosition;
    protected FontSize fontSize;

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

    @Override
    public List<PdfElement> getAllGroupElements() {
        ArrayList<PdfElement> result = new ArrayList<>();
        result.add(this);
        return result;
    }

    @Override
    public List<PdfElement> getSelectedGroupElementsBy(PdfFormValues pdfFormValues) {
        return getAllGroupElements();
    }

    @Override
    public void setFontSize(FontSize fontSize) {
        this.fontSize = fontSize;
    }
}

