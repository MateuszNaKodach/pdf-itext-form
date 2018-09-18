package io.github.nowakprojects.pdfitextform;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

class SingleElementPdfGroup implements PdfGroup {

    private PdfElement pdfElement;

    private SingleElementPdfGroup(PdfElement pdfElement) {
        this.pdfElement = pdfElement;
    }

    static SingleElementPdfGroup of(PdfElement pdfElement) {
        return new SingleElementPdfGroup(pdfElement);
    }

    public List<PdfElement> getAllGroupElements() {
        return new ArrayList<>(asList(this.pdfElement));
    }

    @Override
    public List<PdfElement> getSelectedGroupElementsBy(PdfFormValues pdfFormValues) {
        return getAllGroupElements();
    }
}
