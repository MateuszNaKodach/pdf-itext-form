package io.github.nowakprojects.pdfitextform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

class AlternativePdfGroup implements PdfGroup {

    private PdfElement prioritized;
    private PdfElement alternative;

    private AlternativePdfGroup(PdfElement prioritized, PdfElement alternative) {
        this.prioritized = prioritized;
        this.alternative = alternative;
    }

    static AlternativePdfGroup orderedOr(PdfElement prioritized, PdfElement alternative) {
        return new AlternativePdfGroup(prioritized, alternative);
    }

    public List<PdfElement> getAllGroupElements() {
        return new ArrayList<>(asList(prioritized, alternative));
    }

    @Override
    public List<PdfElement> getSelectedGroupElementsBy(PdfFormValues pdfFormValues) {
        return pdfFormValues.containsValueForTag(prioritized.getTag())
                ? new ArrayList<>(Collections.singletonList(prioritized))
                : new ArrayList<>(Collections.singletonList(alternative));
    }
}
