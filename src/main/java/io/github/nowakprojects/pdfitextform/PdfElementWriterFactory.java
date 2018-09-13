package io.github.nowakprojects.pdfitextform;

class PdfElementWriterFactory {

    static PdfFormWriter getPdfElementWriterFor(PdfElement pdfElement, Object content) {
        if (pdfElement instanceof MultilineTextPdfElement) {
            return new MultilineTextPdfFormWriter((MultilineTextPdfElement) pdfElement, (String) content);
        } else if (pdfElement instanceof SeparatedTextPdfElement) {
            return new SeparatedTextPdfFormWriter((SeparatedTextPdfElement) pdfElement, (String) content);
        } else if (content instanceof String) {
            return new SimplePdfFormWriter(pdfElement, (String) content);
        } else {
            throw new IllegalArgumentException("Passed values cannot be printed!");
        }
    }

}

