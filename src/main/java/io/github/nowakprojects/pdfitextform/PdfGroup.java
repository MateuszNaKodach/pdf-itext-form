package io.github.nowakprojects.pdfitextform;

import java.util.List;

interface PdfGroup {

    List<PdfElement> getAllGroupElements();

    List<PdfElement> getSelectedGroupElementsBy(PdfFormValues pdfFormValues);

    default boolean isEmpty() {
        return getAllGroupElements().isEmpty();
    }

}
