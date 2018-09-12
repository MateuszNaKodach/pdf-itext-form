package io.github.nowakprojects.pdfitextform.v1;

interface PdfElement {

    default boolean isAbsoluteText() {
        return this instanceof AbsoluteTextPdfElement;
    }

}
