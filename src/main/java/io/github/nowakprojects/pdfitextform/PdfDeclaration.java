package io.github.nowakprojects.pdfitextform;

import java.util.*;

import static java.util.Collections.emptySet;

/**
 * TODO: PDF Name, Elements on declared page
 */
class PdfDeclaration {
    private final float defaultFontSize;
    private final HashMap<PdfPage, Set<PdfElement>> elementsByPages;

    PdfDeclaration(float defaultFontSize) {
        this.defaultFontSize = defaultFontSize;
        this.elementsByPages = new HashMap<>();
    }

    PdfDeclaration(float defaultFontSize, HashMap<PdfPage, Set<PdfElement>> elementsByPages) {
        this.defaultFontSize = defaultFontSize;
        this.elementsByPages = elementsByPages;
    }

    /*
    private PdfDeclaration(HashMap<PdfPage, Set<PdfElement>> elementsByPages) {
        this.elementsByPages = elementsByPages;
    }

    static PdfDeclaration withPageElements(PdfPage pdfPage, PdfElements pdfElements) {
        final HashMap<PdfPage, Set<PdfElement>> elementsByPages =
                new HashMap<PdfPage, Set<PdfElement>>() {{
                    put(pdfPage, pdfElements.getElements());
                }};
        return new PdfDeclaration(elementsByPages);
    }*/

    static PdfDeclaration withDefaultFontSize(float defaultFontSize) {
        return new PdfDeclaration(defaultFontSize);
    }

    PdfDeclaration addPageElements(PdfPage pdfPage, PdfElements pdfElements) {
        final HashMap<PdfPage, Set<PdfElement>> elementsByPages = new HashMap<PdfPage, Set<PdfElement>>(this.elementsByPages) {
            {
                put(pdfPage, pdfElements.getElements());
            }
        };
        return new PdfDeclaration(this.defaultFontSize, elementsByPages);
    }

    public Set<PdfElement> getElementsByPage(PdfPage pdfPage) {
        return elementsByPages.getOrDefault(pdfPage, emptySet());
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