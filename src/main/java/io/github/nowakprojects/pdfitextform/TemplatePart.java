package io.github.nowakprojects.pdfitextform;

class Template {
    private final TemplatePart[] content;

    private Template(TemplatePart[] content) {
        this.content = content;
    }

    static Template withParts(TemplatePart[] parts) {
        return new Template(parts);
    }

    TemplatePart[] getContent() {
        return content;
    }
}

class TemplatePart {
    private final TemplateType templateType;
    private final String content;

    private TemplatePart(TemplateType templateType, String content) {
        this.templateType = templateType;
        this.content = content;
    }

    static TemplatePart staticPart(String content) {
        return new TemplatePart(TemplateType.STATIC, content);
    }

    static TemplatePart tagPart(String content) {
        return new TemplatePart(TemplateType.TAG, content);
    }

    public String getContent() {
        return content;
    }

    boolean isStaticPart() {
        return this.templateType == TemplateType.STATIC;
    }

    boolean isTagPart() {
        return this.templateType == TemplateType.TAG;
    }
}

enum TemplateType {
    STATIC,
    TAG;
}
