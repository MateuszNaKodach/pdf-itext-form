package io.github.nowakprojects.pdfitextform;


import java.util.Optional;

public interface PdfElement<T> extends PdfGroup {

    String getTag();

    PdfPosition getPdfPosition();

    Optional<FontSize> getFontSize();

    T withFontSize(FontSize fontSize);

    void setFontSize(FontSize fontSize);

    default float getX() {
        return getPdfPosition().getX();
    }

    default float getY() {
        return getPdfPosition().getY();
    }
}
