package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.pdf.PdfWriter;

abstract class PdfFormWriter<ELEMENT extends PdfElement> {

    final ELEMENT pdfElement;

    final PdfWriter pdfWriter;

    protected PdfFormWriter(ELEMENT pdfElement, PdfWriter pdfWriter) {
        this.pdfElement = pdfElement;
        this.pdfWriter = pdfWriter;
    }

    abstract void writeWithContent(String content);

}
