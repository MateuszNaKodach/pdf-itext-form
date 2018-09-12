package io.github.nowakprojects.pdfitextform.v1;

class PdfPositionFactory {

    static PdfPositionBuilder getPosition(PositionType positionType) {
        return new PdfPositionBuilder(positionType);
    }

    static class PdfPositionBuilder {
        private final PositionType positionType;

        PdfPositionBuilder(PositionType positionType) {
            this.positionType = positionType;
        }

        PdfPosition withCoordinates(float x, float y) {
            switch (positionType) {
                case FROM_BOTTOM_LEFT:
                    return new BottomLeftPdfPosition(x, y);
                case FROM_TOP_LEFT:
                    return new TopLeftPdfPosition(x, y);
                default:
                    throw new IllegalArgumentException("Illegal position type!");
            }
        }
    }
}
