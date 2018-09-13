package io.github.nowakprojects.pdfitextform;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.emptySet;

class PdfFormSchema {
    private final float defaultFontSize;
    private final boolean overrideElementsCustomFontSize;
    private final HashMap<PdfPageNumber, Set<PdfElement>> elementsByPages;

    private PdfFormSchema(float defaultFontSize, boolean overrideElementsCustomFontSize) {
        this.defaultFontSize = defaultFontSize;
        this.overrideElementsCustomFontSize = overrideElementsCustomFontSize;
        this.elementsByPages = new HashMap<>();
    }

    private PdfFormSchema(float defaultFontSize, boolean overrideElementsCustomFontSize, HashMap<PdfPageNumber, Set<PdfElement>> elementsByPages) {
        this.defaultFontSize = defaultFontSize;
        this.overrideElementsCustomFontSize = overrideElementsCustomFontSize;
        this.elementsByPages = elementsByPages;
    }

    static PdfFormSchema withDefaultFontSize(float defaultFontSize) {
        return new PdfFormSchema(defaultFontSize, false);
    }

    static PdfFormSchema withDefaultFontSize(float defaultFontSize, boolean overrideElementsCustomFontSize) {
        return new PdfFormSchema(defaultFontSize, overrideElementsCustomFontSize);
    }

    PdfFormSchema addPageElements(PdfPageNumber pdfPageNumber, PdfElements pdfElements) {
        final Set<PdfElement> elementsWithFontSize = pdfElements.getElements()
                .stream()
                .filter(element -> !overrideElementsCustomFontSize && !element.getCustomFontSize().isPresent())
                .map(element -> (PdfElement) element.withCustomFontSize(defaultFontSize))
                .collect(Collectors.toSet());

        final HashMap<PdfPageNumber, Set<PdfElement>> elementsByPages = new HashMap<PdfPageNumber, Set<PdfElement>>(this.elementsByPages) {
            {
                put(
                        pdfPageNumber,
                        elementsWithFontSize
                );
            }
        };
        return new PdfFormSchema(this.defaultFontSize, this.overrideElementsCustomFontSize, elementsByPages);
    }

    Set<PdfElement> getElementsByPage(PdfPageNumber pdfPageNumber) {
        return elementsByPages.getOrDefault(pdfPageNumber, emptySet());
    }

    Set<PdfPageNumber> getPages() {
        return elementsByPages.keySet();
    }

    Integer countPages() {
        return elementsByPages.keySet().size();
    }
}


class PdfElements {

    private final Set<PdfElement> elements;

    private PdfElements(Set<PdfElement> elements) {
        this.elements = elements;
    }

    static PdfElements elements(PdfElement... elements) {
        return new PdfElements(new HashSet<>(Arrays.asList(elements)));
    }

    Set<PdfElement> getElements() {
        return elements;
    }
}