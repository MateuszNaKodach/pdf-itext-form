package io.github.nowakprojects.pdfitextform;

public class PdfFillingException extends RuntimeException {

    public PdfFillingException() {
    }

    public PdfFillingException(String message) {
        super(message);
    }

    public PdfFillingException(String message, Throwable cause) {
        super(message, cause);
    }

    public PdfFillingException(Throwable cause) {
        super(cause);
    }

    public PdfFillingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
