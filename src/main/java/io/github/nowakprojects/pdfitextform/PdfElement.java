package io.github.nowakprojects.pdfitextform;

interface PdfElement {

    default boolean isAbsoluteText() {
        return this instanceof AbsoluteTextPdfElement;
    }

}
