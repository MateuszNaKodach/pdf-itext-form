package io.github.nowakprojects.pdfitextform;

/**
 * Created by Marcin
 */
class SimpleTextPdfElement implements PdfElement {
    private final String tag;
    private final String content;
    private final PdfPosition pdfPosition;
    private final float fontSize;

    SimpleTextPdfElement(String tag, String content, PdfPosition pdfPosition, float fontSize) {
        this.tag = tag;
        this.content = content;
        this.pdfPosition = pdfPosition;
        this.fontSize = fontSize;
    }

    public String getTag() {
        return tag;
    }

    public String getContent() {
        return content;
    }

    public PdfPosition getPdfPosition() {
        return pdfPosition;
    }

    public float getFontSize() {
        return fontSize;
    }

    static class Configuration implements PdfElementCreator {
        private final String tag;
        private final PdfPosition pdfPosition;
        private final float fontSize;

        Configuration(String tag, PdfPosition pdfPosition, float fontSize) {
            this.fontSize = fontSize;
            this.pdfPosition = pdfPosition;
            this.tag = tag;
        }

        @Override
        public SimpleTextPdfElement create(String content) {
            return new SimpleTextPdfElement(tag, content, pdfPosition, fontSize);
        }
    }

}
