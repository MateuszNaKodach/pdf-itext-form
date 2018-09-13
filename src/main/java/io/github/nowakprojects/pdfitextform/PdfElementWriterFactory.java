package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.pdf.PdfWriter;

public class PdfElementWriterFactory {

    public static PdfFormWriter getPdfElementWriterFor(PdfElement pdfElement, PdfWriter pdfWriter) {
        if (pdfElement instanceof AbsoluteTextPdfElement) {
            return new AbsoluteTextPdfFormWriter((AbsoluteTextPdfElement) pdfElement, pdfWriter);
        }
        return null;
    }


}

