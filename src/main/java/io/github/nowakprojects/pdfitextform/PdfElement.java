package io.github.nowakprojects.pdfitextform;

import java.util.Optional;

interface PdfElement {

    String getTag();

    PdfPosition getPdfPosition();

    Optional<Float> getCustomFontSize();

    default float getX() {
        return getPdfPosition().getX();
    }

    default float getY() {
        return getPdfPosition().getY();
    }
}
