package io.github.nowakprojects.pdfitextform;

import static io.github.nowakprojects.pdfitextform.PositionType.FROM_BOTTOM_LEFT;

class BottomLeftPdfPosition implements PdfPosition {

    private final PositionType positionType = FROM_BOTTOM_LEFT;
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

    @Override
    public PositionType getPositionType() {
        return positionType;
    }
}
