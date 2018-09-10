package io.github.nowakprojects.pdfitextform;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Marcin
 */
public class PdfSiteSchema {

    private Set<PdfElement> elements;

    private PdfSiteSchema(Set<PdfElement> elements) {
        this.elements = elements;
    }

    static PdfSiteSchema withElements(Set<PdfElement> elements) {
        return new PdfSiteSchema(elements);
    }

    static PdfSiteSchema withElements(PdfElement... elements) {
        return new PdfSiteSchema(new HashSet<>(Arrays.asList(elements)));
    }

    public Set<PdfElement> getElements() {
        return elements;
    }
}
