package io.github.nowakprojects.pdfitextform;

class PdfPositionFactory {

    static PdfPosition getBottomLeftPdfPosition(float x, float y) {
        return getPosition(PositionType.FROM_BOTTOM_LEFT).withCoordinates(x, y);
    }

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
                default:
                    throw new IllegalArgumentException("Illegal position type!");
            }
        }
    }
}
