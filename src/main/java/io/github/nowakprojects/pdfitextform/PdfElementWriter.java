package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.pdf.PdfWriter;

interface PdfElementWriter<ELEMENT> {

    void writePdfElement(PdfWriter pdfWriter, ELEMENT pdfElement);

}
