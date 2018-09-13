package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.pdf.PdfWriter;

import java.util.Optional;

public interface PdfElement<T> {

    String getTag();

    PdfPosition getPdfPosition();

    Optional<FontSize> getFontSize();

    T withFontSize(FontSize fontSize);

    default float getX() {
        return getPdfPosition().getX();
    }

    default float getY() {
        return getPdfPosition().getY();
    }
}
