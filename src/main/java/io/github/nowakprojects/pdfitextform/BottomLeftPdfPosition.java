package io.github.nowakprojects.pdfitextform;

class BottomLeftPdfPosition implements PdfPosition {

    private final float x;
    private final float y;

    BottomLeftPdfPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }
}
