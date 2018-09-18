package io.github.nowakprojects.pdfitextform;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;


abstract class AbstractPdfElement<T> implements PdfElement<T> {

    protected final String tag;
    protected final PdfPosition pdfPosition;
    protected FontSize fontSize;
    protected final String defaultContent;
    protected final Template template;

    AbstractPdfElement(String tag, PdfPosition pdfPosition) {
        this.tag = tag;
        this.pdfPosition = pdfPosition;
        this.fontSize = null;
        this.defaultContent = null;
        this.template = null;
    }

    AbstractPdfElement(String tag, PdfPosition pdfPosition, FontSize fontSize) {
        this.tag = tag;
        this.pdfPosition = pdfPosition;
        this.fontSize = fontSize;
        this.defaultContent = null;
        this.template = null;
    }

    AbstractPdfElement(String tag, PdfPosition pdfPosition, FontSize fontSize, String defaultContent, Template template) {
        this.tag = tag;
        this.pdfPosition = pdfPosition;
        this.fontSize = fontSize;
        this.defaultContent = defaultContent;
        this.template = template;
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
    public Optional<String> getDefaultContent() {
        return Optional.ofNullable(defaultContent);
    }

    @Override
    public boolean isTemplated() {
        return this.template != null;
    }

    @Override
    public Template getTemplate() {
        return template;
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

