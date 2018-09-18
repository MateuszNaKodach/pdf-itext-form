package io.github.nowakprojects.pdfitextform;


import java.util.Arrays;
import java.util.Optional;


public interface PdfElement<T> extends PdfGroup {

    String TAG_SEPARATOR = " ";

    String getTag();

    default boolean hasMultipleTags() {
        return getTag().contains(TAG_SEPARATOR);
    }

    default String[] getTagsArray() {
        return hasMultipleTags() ? Arrays.stream(getTag().split(TAG_SEPARATOR)).filter(s->!s.trim().isEmpty()).toArray(String[]::new) : new String[]{getTag()};
    }

    PdfPosition getPdfPosition();

    Optional<FontSize> getFontSize();

    T withFontSize(FontSize fontSize);

    void setFontSize(FontSize fontSize);

    Optional<String> getDefaultContent();

    T withDefaultContent(String defaultContent);

    default float getX() {
        return getPdfPosition().getX();
    }

    default float getY() {
        return getPdfPosition().getY();
    }
}
