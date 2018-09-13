package io.github.nowakprojects.pdfitextform;

import java.util.*;

import static java.util.Collections.emptySet;

/**
 * TODO: PDF Name, Elements on declared page
 */
class PdfFormDeclaration {
    private final float defaultFontSize;
    private final HashMap<PdfPageNumber, Set<PdfElement>> elementsByPages;

    PdfFormDeclaration(float defaultFontSize) {
        this.defaultFontSize = defaultFontSize;
        this.elementsByPages = new HashMap<>();
    }

    PdfFormDeclaration(float defaultFontSize, HashMap<PdfPageNumber, Set<PdfElement>> elementsByPages) {
        this.defaultFontSize = defaultFontSize;
        this.elementsByPages = elementsByPages;
    }

    /*
    private PdfFormDeclaration(HashMap<PdfPageNumber, Set<PdfElement>> elementsByPages) {
        this.elementsByPages = elementsByPages;
    }

    static PdfFormDeclaration withPageElements(PdfPageNumber pdfPage, PdfElements pdfElements) {
        final HashMap<PdfPageNumber, Set<PdfElement>> elementsByPages =
                new HashMap<PdfPageNumber, Set<PdfElement>>() {{
                    put(pdfPage, pdfElements.getElements());
                }};
        return new PdfFormDeclaration(elementsByPages);
    }*/

    static PdfFormDeclaration withDefaultFontSize(float defaultFontSize) {
        return new PdfFormDeclaration(defaultFontSize);
    }

    PdfFormDeclaration addPageElements(PdfPageNumber pdfPageNumber, PdfElements pdfElements) {
        final HashMap<PdfPageNumber, Set<PdfElement>> elementsByPages = new HashMap<PdfPageNumber, Set<PdfElement>>(this.elementsByPages) {
            {
                put(pdfPageNumber, pdfElements.getElements());
            }
        };
        return new PdfFormDeclaration(this.defaultFontSize, elementsByPages);
    }

    public Set<PdfElement> getElementsByPage(PdfPageNumber pdfPageNumber) {
        return elementsByPages.getOrDefault(pdfPageNumber, emptySet());
    }

    public Set<PdfPageNumber> getPages() {
        return elementsByPages.keySet();
    }

    public Integer countPages() {
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