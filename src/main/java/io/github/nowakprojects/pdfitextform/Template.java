package io.github.nowakprojects.pdfitextform;

class Template {
    private final TemplatePart[] content;

    protected Template(TemplatePart[] content) {
        this.content = content;
    }

    static Template withParts(TemplatePart[] parts) {
        return new Template(parts);
    }

    TemplatePart[] getContent() {
        return content;
    }
}

class IfTagValueTemplate extends TemplatePart {

    private final String showOnlyIfThisTagHasValue;

    IfTagValueTemplate(TemplateType templateType, String content, String tag) {
        super(templateType, content);
        this.showOnlyIfThisTagHasValue = tag;
    }

    @Override
    boolean shouldBeShowWith(PdfFormValues pdfFormValues) {
        return pdfFormValues.getValueByTag(showOnlyIfThisTagHasValue) != null;
    }
}

class TemplatePart {
    private final TemplateType templateType;
    private final String content;

    TemplatePart(TemplateType templateType, String content) {
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


    boolean shouldBeShowWith(PdfFormValues pdfFormValues) {
        return true;
    }

    TemplatePart showOnlyIfTagHasValue(String tag) {
        return new IfTagValueTemplate(this.templateType, this.content, tag);
    }
}

enum TemplateType {
    STATIC,
    TAG;
}
