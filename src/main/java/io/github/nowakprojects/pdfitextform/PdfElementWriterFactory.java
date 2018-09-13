package io.github.nowakprojects.pdfitextform;


/*
TODO: Better solution will be cerate PdfWriterRegister and register them for certain types.
FOR EXAMPLE: https://stackoverflow.com/questions/34291714/how-to-implement-factory-pattern-with-generics-in-java
 */

class PdfElementWriterFactory {

    static PdfFormWriter getPdfElementWriterFor(PdfElement pdfElement, String content) {
        if (pdfElement instanceof MultilineTextPdfElement) {
            return new MultilineTextPdfFormWriter((MultilineTextPdfElement) pdfElement, content);
        } else if (pdfElement instanceof SeparatedTextPdfElement) {
            return new SeparatedTextPdfFormWriter((SeparatedTextPdfElement) pdfElement, content);
        } else if (pdfElement instanceof DatePdfElement) {
            return new DatePdfFormWriter((DatePdfElement) pdfElement, content);
        } else {
            return new SimplePdfFormWriter(pdfElement, content);
        }
    }
}

