package io.github.nowakprojects.pdfitextform;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PdfDeclaration {
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

    public Set<PdfElement> getElements() {
        return elements;
    }
}
