package io.github.nowakprojects.pdfitextform;

class PdfPageNumber {
    private final int value;

    static PdfPageNumber from(int pageNumber) {
        return new PdfPageNumber(pageNumber);
    }

    private PdfPageNumber(int value) {
        this.value = value;
    }

    int getValue() {
        return value;
    }
}
