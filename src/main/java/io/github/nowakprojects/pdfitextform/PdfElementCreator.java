package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.pdf.PdfWriter;

import java.text.ParseException;

/**
 * Created by Marcin
 */
interface PdfElementCreator {

    AbstractPdfElement create(String content) throws ParseException;

    String getTag();

    void printTemplate(PdfWriter writer);
}
