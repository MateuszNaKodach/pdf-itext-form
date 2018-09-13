package io.github.nowakprojects.pdfitextform;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.emptySet;

class PdfFormSchema {
    private final FontSize defaultFontSize;
    private final String defaultFontName;
    private final boolean overrideElementsCustomFontSize;
    private final HashMap<PdfPageNumber, Set<PdfElement>> elementsByPages;

    private PdfFormSchema(FontSize defaultFontSize, boolean overrideElementsCustomFontSize, String defaultFontName) {
        this.defaultFontSize = defaultFontSize;
        this.overrideElementsCustomFontSize = overrideElementsCustomFontSize;
        this.defaultFontName = defaultFontName;
        this.elementsByPages = new HashMap<>();
    }

    private PdfFormSchema(FontSize defaultFontSize, boolean overrideElementsCustomFontSize, String defaultFontName, HashMap<PdfPageNumber, Set<PdfElement>> elementsByPages) {
        this.defaultFontSize = defaultFontSize;
        this.overrideElementsCustomFontSize = overrideElementsCustomFontSize;
        this.defaultFontName = defaultFontName;
        this.elementsByPages = elementsByPages;
    }

    static PdfFormSchema withDefaultFont(String defaultFontName, FontSize defaultFontSize) {
        return new PdfFormSchema(defaultFontSize, false, defaultFontName);
    }

    static PdfFormSchema withDefaultFontSize(String defaultFontName, FontSize defaultFontSize, boolean overrideElementsCustomFontSize) {
        return new PdfFormSchema(defaultFontSize, overrideElementsCustomFontSize, defaultFontName);
    }

    static PdfFormSchema withDefaultFontSize(FontSize defaultFontSize, boolean overrideElementsCustomFontSize) {
        return new PdfFormSchema(defaultFontSize, overrideElementsCustomFontSize, null);
    }

    PdfFormSchema addPageElements(PdfPageNumber pdfPageNumber, PdfElements pdfElements) {
        final Set<PdfElement> elementsWithFontSize = pdfElements.getElements()
                .stream()
                .filter(element -> !overrideElementsCustomFontSize && !element.getFontSize().isPresent())
                .map(element -> (PdfElement) element.withFontSize(defaultFontSize))
                .collect(Collectors.toSet());

        final HashMap<PdfPageNumber, Set<PdfElement>> elementsByPages = new HashMap<PdfPageNumber, Set<PdfElement>>(this.elementsByPages) {
            {
                put(
                        pdfPageNumber,
                        elementsWithFontSize
                );
            }
        };
        return new PdfFormSchema(this.defaultFontSize, this.overrideElementsCustomFontSize, this.defaultFontName, elementsByPages);
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