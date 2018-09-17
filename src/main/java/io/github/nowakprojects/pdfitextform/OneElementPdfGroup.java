package io.github.nowakprojects.pdfitextform;

import java.util.Collections;
import java.util.List;

class OneElementPdfGroup implements PdfGroup {

    private List<PdfElement> groupElements;

    OneElementPdfGroup(PdfElement groupElements) {
        this.groupElements = Collections.singletonList(groupElements);
    }

    public List<PdfElement> getSelectedGroupElements() {
        return groupElements;
    }
}
