package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.pdf.PdfWriter;

abstract class PdfFormWriter<ELEMENT extends PdfElement, C> {

    final ELEMENT pdfElement;
    final C content;

    protected PdfFormWriter(ELEMENT pdfElement, C content) {
        if (!pdfElement.getFontSize().isPresent()) {
            throw new PdfFillingException("Pdf element have to have declared font size!");
        }
        this.pdfElement = pdfElement;
        this.content = content;
    }

    abstract void writeOn(PdfWriter pdfWriter);

}
