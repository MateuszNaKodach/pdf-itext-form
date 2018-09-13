package io.github.nowakprojects.pdfitextform;

class FontSize {

    private final float value;

    static FontSize withValue(float value) {
        return new FontSize(value);
    }

    private FontSize(float value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Passed value for font size: " + value + ", but value grater than 0 is expected!");
        }
        this.value = value;
    }

    float getValue() {
        return value;
    }
}