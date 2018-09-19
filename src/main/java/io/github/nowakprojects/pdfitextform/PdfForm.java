package io.github.nowakprojects.pdfitextform;

class PdfForm {
    private final PdfFormSchema schema;
    private final PdfFormValues values;

    PdfForm(PdfFormSchema schema, PdfFormValues values) {
        this.schema = schema;
        this.values = values;
    }

    PdfFormSchema getSchema() {
        return schema;
    }

    PdfFormValues getValues() {
        return values;
    }
}
