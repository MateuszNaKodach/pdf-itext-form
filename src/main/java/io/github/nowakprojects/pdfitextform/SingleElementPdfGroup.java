package io.github.nowakprojects.pdfitextform;

import java.util.ArrayList;
import java.util.List;

class SingleElementPdfGroup implements PdfGroup {

    private List<PdfElement> groupElements;

    private SingleElementPdfGroup(PdfElement groupElements) {
        ArrayList<PdfElement> elementsList = new ArrayList<>();
        elementsList.add(groupElements);
        this.groupElements = elementsList;
    }

    static SingleElementPdfGroup of(PdfElement pdfElement) {
        return new SingleElementPdfGroup(pdfElement);
    }

    public List<PdfElement> getAllGroupElements() {
        return groupElements;
    }

    @Override
    public List<PdfElement> getSelectedGroupElementsBy(PdfFormValues pdfFormValues) {
        return getAllGroupElements();
    }
}
