package io.github.nowakprojects.pdfitextform;

import java.util.*;
import java.util.stream.Collectors;

import static io.github.nowakprojects.pdfitextform.PdfFormSchema.getElementsFromGroups;
import static java.util.Collections.emptySet;
import static java.util.Objects.isNull;

class PdfFormSchema {
    private final FontSize defaultFontSize;
    private final String defaultFontName;
    private final boolean overrideElementsCustomFontSize;
    private final HashMap<PdfPageNumber, Set<PdfGroup>> elementsByPages;

    private PdfFormSchema(FontSize defaultFontSize, boolean overrideElementsCustomFontSize, String defaultFontName) {
        this.defaultFontSize = defaultFontSize;
        this.overrideElementsCustomFontSize = overrideElementsCustomFontSize;
        this.defaultFontName = defaultFontName;
        this.elementsByPages = new HashMap<>();
    }

    private static PdfFormSchema createWithElements(FontSize defaultFontSize, boolean overrideElementsCustomFontSize, String defaultFontName, HashMap<PdfPageNumber, Set<PdfElement>> elementsByPages) {
        return new PdfFormSchema(
                defaultFontSize,
                overrideElementsCustomFontSize,
                defaultFontName,
                getPdfGroupMapFrom(elementsByPages)
        );
    }


    private PdfFormSchema(FontSize defaultFontSize, boolean overrideElementsCustomFontSize, String defaultFontName, HashMap<PdfPageNumber, Set<PdfGroup>> elementsByPages) {
        this.defaultFontSize = defaultFontSize;
        this.overrideElementsCustomFontSize = overrideElementsCustomFontSize;
        this.defaultFontName = defaultFontName;
        this.elementsByPages = elementsByPages;
    }

    private static HashMap<PdfPageNumber, Set<PdfGroup>> getPdfGroupMapFrom(HashMap<PdfPageNumber, Set<PdfElement>> elementsByPages) {
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

    static PdfFormSchema withDefaultFontSize(FontSize defaultFontSize) {
        return new PdfFormSchema(defaultFontSize, false, null);
    }

    PdfFormSchema addPageElements(PdfPageNumber pdfPageNumber, PdfElements pdfElements) {
        final Set<PdfElement> elementsWithFontSize = pdfElements.getElements()
                .stream()
                .filter(element -> !overrideElementsCustomFontSize && !element.getFontSize().isPresent())
                .map(element -> (PdfElement) element.withFontSize(defaultFontSize))
                .collect(Collectors.toSet());

        final HashMap<PdfPageNumber, Set<PdfGroup>> elementsByPages = new HashMap<PdfPageNumber, Set<PdfGroup>>(this.elementsByPages) {
            {
                put(
                        pdfPageNumber,
                        elementsWithFontSize.stream()
                                .map(SingleElementPdfGroup::of)
                                .collect(Collectors.toSet())
                );
            }
        };
        return new PdfFormSchema(this.defaultFontSize, this.overrideElementsCustomFontSize, this.defaultFontName, elementsByPages);
    }

    PdfFormSchema addPageElements(PdfPageNumber pdfPageNumber, PdfGroups pdfGroups) {
        pdfGroups.getElements()
                .forEach(element -> ((PdfElement) element).setFontSize(defaultFontSize));

        final HashMap<PdfPageNumber, Set<PdfGroup>> elementsByPages = new HashMap<PdfPageNumber, Set<PdfGroup>>(this.elementsByPages) {
            {
                put(
                        pdfPageNumber,
                        pdfGroups.getGroups()
                );
            }
        };
        return new PdfFormSchema(this.defaultFontSize, this.overrideElementsCustomFontSize, this.defaultFontName, elementsByPages);
    }


    Set<PdfElement> getAllElementsByPageBy(PdfPageNumber pdfPageNumber, PdfFormValues pdfFormValues) {
        return getElementsFromGroups(elementsByPages.getOrDefault(pdfPageNumber, emptySet()), pdfFormValues);
    }

    static Set<PdfElement> getElementsFromGroups(Set<PdfGroup> groups, PdfFormValues pdfFormValues) {
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


    Set<PdfElement> getAllElementsByPage(PdfPageNumber pdfPageNumber) {
        return getElementsFromGroups(elementsByPages.getOrDefault(pdfPageNumber, emptySet()), null);
    }


    Set<PdfPageNumber> getPages() {
        return elementsByPages.keySet();
    }

    int countPages() {
        return elementsByPages.keySet().size();
    }

    int countElementsOnPage(PdfPageNumber pageNumber) {
        return elementsByPages.getOrDefault(pageNumber, emptySet()).size();
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


class PdfGroups {

    private final Set<PdfGroup> groups;

    private PdfGroups(Set<PdfGroup> groups) {
        this.groups = groups;
    }

    static PdfGroups groups(PdfGroup... groups) {
        return new PdfGroups(new HashSet<>(Arrays.asList(groups)));
    }

    Set<PdfGroup> getGroups() {
        return this.groups;
    }

    Set<PdfElement> getElements() {
        return getElementsFromGroups(groups,null);
    }
}