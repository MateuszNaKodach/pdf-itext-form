package io.github.nowakprojects.pdfitextform;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class PdfGroupHelper {

    private PdfGroupHelper() {
    }


    static HashMap<PdfPageNumber, Set<PdfGroup>> getPdfGroupMapFrom(HashMap<PdfPageNumber, Set<PdfElement>> elementsByPages) {
        final HashMap<PdfPageNumber, Set<PdfGroup>> result = new HashMap<>();
        for (Map.Entry<PdfPageNumber, Set<PdfElement>> pageWithElements : elementsByPages.entrySet()) {
            result.put(
                    pageWithElements.getKey(),
                    pageWithElements.getValue().stream().map(SingleElementPdfGroup::of).collect(Collectors.toSet())
            );
        }
        return result;
    }

    private static Set<PdfGroup> getSinglePdfGroupSetFrom(Set<PdfElement> pdfElements) {
        return pdfElements.stream()
                .map(SingleElementPdfGroup::of)
                .collect(Collectors.toSet());
    }

    static Set<PdfElement> getSelectedElementsFromGroups(Set<PdfGroup> groups, PdfFormValues pdfFormValues) {
        return groups
                .stream()
                .map(pdfGroup -> isNull(pdfFormValues) ? pdfGroup.getAllGroupElements() : pdfGroup.getSelectedGroupElementsBy(pdfFormValues))
                .reduce((elements, acc) -> {
                    acc.addAll(elements);
                    return acc;
                })
                .map(HashSet::new)
                .orElseGet(HashSet::new);
    }
}
