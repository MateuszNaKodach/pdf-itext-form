package io.github.nowakprojects.pdfitextform;

interface PdfElement {

    String getTag();

    PdfPosition getPdfPosition();

    default float getX() {
        return getPdfPosition().getX();
    }

    default float getY() {
        return getPdfPosition().getY();
    }
}
