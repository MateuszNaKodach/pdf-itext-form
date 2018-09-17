package io.github.nowakprojects.pdfitextform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

class AlternativePdfGroup implements PdfGroup {

    private PdfElement pdfElement1;
    private PdfElement pdfElement2;

    private AlternativePdfGroup(PdfElement pdfElement1, PdfElement pdfElement2) {
        this.pdfElement1 = pdfElement1;
        this.pdfElement2 = pdfElement2;
    }

    static AlternativePdfGroup orderedOr(PdfElement pdfElement1, PdfElement pdfElement2) {
        return new AlternativePdfGroup(pdfElement1, pdfElement2);
    }

    public List<PdfElement> getAllGroupElements() {
        return new ArrayList<>(asList(pdfElement1, pdfElement2));
    }

    @Override
    public List<PdfElement> getSelectedGroupElementsBy(PdfFormValues pdfFormValues) {
        return pdfFormValues.containsValueForTag(pdfElement1.getTag())
                ? new ArrayList<>(Collections.singletonList(pdfElement1))
                : new ArrayList<>(Collections.singletonList(pdfElement2));
    }
}
