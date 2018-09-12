package io.github.nowakprojects.pdfitextform.v1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO: PDF Name, Elements on declared page
 */
class PdfDeclaration {
    private Set<PdfElement> elements = new HashSet<>();

    private PdfDeclaration(Set<PdfElement> elements) {
        this.elements = elements;
    }

    static PdfDeclaration withElements(Set<PdfElement> elements) {
        return new PdfDeclaration(elements);
    }

    static PdfDeclaration withElements(PdfElement... elements) {
        return new PdfDeclaration(new HashSet<>(Arrays.asList(elements)));
    }

    Set<PdfElement> getElements() {
        return elements;
    }
}
