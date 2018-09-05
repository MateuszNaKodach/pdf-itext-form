package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.pdf.PdfWriter;

public class PdfForm3 {

    public static void main(String[] args) {
        PdfDeclaration pdfDeclaration =
                PdfDeclaration.withElements(
                        PdfAbsoluteText.builder()
                                .withTag("pesel")
                                .andContent("91032312312")
                                .positionedFromTopLeft(100, 100)
                                .addSpaceBetweenLetters(20)
                );

    }

    public static void generatePdfFromDeclaration(PdfDeclaration pdfDeclaration) {
        //pdfDeclaration.getElements().forEach();
    }

    public void renderElementOn(PdfWriter pdfWriter, PdfElement pdfElement) {
        if (pdfElement.isAbsoluteText()) {
            renderAbsoluteTextOn(pdfWriter, (PdfAbsoluteText) pdfElement);
        }
    }

    public void renderAbsoluteTextOn(PdfWriter pdfWriter, PdfAbsoluteText pdfAbsoluteText) {

    }
}
