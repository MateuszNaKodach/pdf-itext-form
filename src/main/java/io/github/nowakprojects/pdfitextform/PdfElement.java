package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.pdf.PdfWriter;

import java.util.Optional;

interface PdfElement<T> {

    String getTag();

    PdfPosition getPdfPosition();

    Optional<Float> getCustomFontSize();

    T withCustomFontSize(float customFontSize);

    default float getX() {
        return getPdfPosition().getX();
    }

    default float getY() {
        return getPdfPosition().getY();
    }

    void writePdfElement(String content, PdfWriter pdfWriter);
}
