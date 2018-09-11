package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.pdf.PdfWriter;

interface PdfElement {

    void print(PdfWriter writer);
}
