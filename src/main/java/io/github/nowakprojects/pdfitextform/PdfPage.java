package io.github.nowakprojects.pdfitextform;

class PdfPage {
    private final int number;

    static PdfPage withNumber(int pageNumber) {
        return new PdfPage(pageNumber);
    }

    private PdfPage(int number) {
        this.number = number;
    }

    int getNumber() {
        return number;
    }
}
