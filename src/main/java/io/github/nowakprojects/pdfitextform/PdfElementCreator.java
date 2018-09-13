package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.pdf.PdfWriter;

import java.text.ParseException;

interface PdfElementCreator {

    AbstractPdfElement create(String content) throws ParseException;

    String getTag();

    void printTemplate(PdfWriter writer);
}
