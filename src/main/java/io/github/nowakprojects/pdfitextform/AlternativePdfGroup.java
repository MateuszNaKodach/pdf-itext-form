package io.github.nowakprojects.pdfitextform;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

class AlternativePdfGroup implements PdfGroup {

    private final PdfElement prioritized;
    private final PdfElement alternative;
    private final PdfGroup prioritizedAttachment;
    private final PdfGroup alternativeAttachment;


    private AlternativePdfGroup(PdfElement prioritized, PdfElement alternative) {
        this.prioritized = prioritized;
        this.alternative = alternative;
        this.alternativeAttachment = EmptyPdfGroup.empty();
        this.prioritizedAttachment = EmptyPdfGroup.empty();
    }

    private AlternativePdfGroup(PdfElement prioritized, PdfElement alternative, PdfGroup prioritizedAttachment, PdfGroup alternativeAttachment) {
        this.prioritized = prioritized;
        this.alternative = alternative;
        this.prioritizedAttachment = prioritizedAttachment;
        this.alternativeAttachment = alternativeAttachment;
    }

    static AlternativePdfGroup orderedOr(PdfElement prioritized, PdfElement alternative) {
        return new AlternativePdfGroup(prioritized, alternative);
    }

    AlternativePdfGroup attachToPrioritized(PdfGroup prioritizedAttachment) {
        return new AlternativePdfGroup(this.prioritized, this.alternative, prioritizedAttachment, this.alternativeAttachment);
    }


    AlternativePdfGroup attachToAlternative(PdfGroup alternativeAttachment) {
        return new AlternativePdfGroup(this.prioritized, this.alternative, this.prioritizedAttachment, alternativeAttachment);
    }

    public List<PdfElement> getAllGroupElements() {
        return Stream.concat(
                getWithAttachmentForValues(null, prioritized, prioritizedAttachment).stream(),
                getWithAttachmentForValues(null, alternative, alternativeAttachment).stream()
        ).collect(Collectors.toList());
    }

    @Override
    public List<PdfElement> getSelectedGroupElementsBy(PdfFormValues pdfFormValues) {
        return pdfFormValues.containsValueForTag(prioritized.getTag())
                ? new ArrayList<>(getWithAttachmentForValues(pdfFormValues, prioritized, prioritizedAttachment))
                : new ArrayList<>(getWithAttachmentForValues(pdfFormValues, alternative, alternativeAttachment));
    }

    private Set<PdfElement> getWithAttachmentForValues(PdfFormValues pdfFormValues, PdfElement prioritized, PdfGroup prioritizedAttachment) {
        return Stream.concat(Stream.of(prioritized), prioritizedAttachment.getSelectedGroupElementsBy(pdfFormValues).stream()).collect(Collectors.toSet());
    }
}
