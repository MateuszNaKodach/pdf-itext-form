package io.github.nowakprojects.pdfitextform;

class PdfForm {
    private final String formFilePath;
    private final PdfFormSchema schema;
    private final PdfFormValues values;

    public PdfForm(String formFilePath, PdfFormSchema schema, PdfFormValues values) {
        this.formFilePath = formFilePath;
        this.schema = schema;
        this.values = values;
    }

    public String getFormFilePath() {
        return formFilePath;
    }

    public PdfFormSchema getSchema() {
        return schema;
    }

    public PdfFormValues getValues() {
        return values;
    }
}
