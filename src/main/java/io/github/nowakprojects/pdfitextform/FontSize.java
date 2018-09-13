package io.github.nowakprojects.pdfitextform;

class FontSize {

    static final float MIN_FONT_SIZE = 2;

    private final float value;

    static FontSize withValue(float value) {
        return new FontSize(value);
    }

    private FontSize(float value) {
        if (value < MIN_FONT_SIZE) {
            throw new IllegalArgumentException("Passed value for font size: " + value + ", but value equal or grater than" + MIN_FONT_SIZE + "is expected!");
        }
        this.value = value;
    }

    float getValue() {
        return value;
    }
}
