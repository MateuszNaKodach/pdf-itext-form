package io.github.nowakprojects.pdfitextform;

import java.util.ArrayList;
import java.util.List;

class EmptyPdfGroup implements PdfGroup {

    private List<PdfElement> groupElements = new ArrayList<>();

    static EmptyPdfGroup empty() {
        return new EmptyPdfGroup();
    }

    public List<PdfElement> getAllGroupElements() {
        return groupElements;
    }

    @Override
    public List<PdfElement> getSelectedGroupElementsBy(PdfFormValues pdfFormValues) {
        return getAllGroupElements();
    }
}
