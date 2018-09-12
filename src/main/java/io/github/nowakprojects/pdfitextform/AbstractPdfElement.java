package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.pdf.PdfWriter;

abstract class AbstractPdfElement implements PdfElement {

    protected final String tag;
    protected final PdfPosition pdfPosition;

    protected AbstractPdfElement(String tag, PdfPosition pdfPosition) {
        this.tag = tag;
        this.pdfPosition = pdfPosition;
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public PdfPosition getPdfPosition() {
        return pdfPosition;
    }

    abstract void writePdfElement(PdfWriter writer);

}

