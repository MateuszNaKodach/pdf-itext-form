package io.github.nowakprojects.pdfitextform;

import static io.github.nowakprojects.pdfitextform.PositionType.FROM_TOP_LEFT;

class TopLeftPdfPosition implements PdfPosition {

    private final PositionType positionType = FROM_TOP_LEFT;
    private final float x;
    private final float y;

    TopLeftPdfPosition(float x, float y) {
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
