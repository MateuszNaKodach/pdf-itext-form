package io.github.nowakprojects.pdfitextform;

import java.util.Arrays;
import java.util.List;

class SimplePdfElementGroup implements PdfGroup {

    private List<PdfElement> groupElements;

    SimplePdfElementGroup(List<PdfElement> groupElements) {
        this.groupElements = groupElements;
    }

    SimplePdfElementGroup(PdfElement... groupElements) {
        this.groupElements = Arrays.asList(groupElements);
    }

    @Override
    public List<PdfElement> getSelectedGroupElements() {
        return this.groupElements;
    }
}
