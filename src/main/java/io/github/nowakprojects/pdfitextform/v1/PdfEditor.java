package io.github.nowakprojects.pdfitextform.v1;

public class PdfEditor {

    private final String filePath;
    private final boolean isNew;

    public PdfEditor(String filePath, boolean isNew) {
        this.filePath = filePath;
        this.isNew = isNew;
    }

    static PdfEditor createNewPdf(String filePath) {
        return new PdfEditor(filePath, true);
    }

    static PdfEditor editExistingPdf(String filePath) {
        return new PdfEditor(filePath, false);
    }
}
