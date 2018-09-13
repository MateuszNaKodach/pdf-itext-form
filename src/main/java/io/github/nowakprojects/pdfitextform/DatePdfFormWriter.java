package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.pdf.PdfWriter;

import java.util.*;

class DatePdfFormWriter extends PdfFormWriter<DatePdfElement, Date> {

    DatePdfFormWriter(DatePdfElement pdfElement, Date content) {
        super(pdfElement, content);
    }

    DatePdfFormWriter(DatePdfElement pdfElement, String content) {
        super(pdfElement, DateUtils.getDateFrom(content));
    }

    @Override
    void writeOn(PdfWriter pdfWriter) {
        final float characterWidth = pdfElement.getCharacterWidth();
        final float spaceBetweenGroup = pdfElement.getSpaceBetweenGroup();
        final FontSize fontSize = pdfElement.getFontSize().get();
        final float x = pdfElement.getX();
        final float y = pdfElement.getY();


        SeparatedTextPdfElement dayElement = SeparatedTextPdfElement.builder()
                .withTag(pdfElement.getTag() + "_day")
                .withCharacterWidth(characterWidth)
                .positionedFromBottomLeft(x, y)
                .withFontSize(fontSize);

        new SeparatedTextPdfFormWriter(dayElement, getDaySting(content))
                .writeOn(pdfWriter);

        SeparatedTextPdfElement monthElement = SeparatedTextPdfElement.builder()
                .withTag(pdfElement.getTag() + "_month")
                .withCharacterWidth(characterWidth)
                .positionedFromBottomLeft(x + characterWidth * 2 + spaceBetweenGroup, y)
                .withFontSize(fontSize);

        new SeparatedTextPdfFormWriter(monthElement, getMonthSting(content))
                .writeOn(pdfWriter);

        SeparatedTextPdfElement yearElement = SeparatedTextPdfElement.builder()
                .withTag(pdfElement.getTag() + "_year")
                .withCharacterWidth(characterWidth)
                .positionedFromBottomLeft(x + characterWidth * 4 + spaceBetweenGroup * 2, y)
                .withFontSize(fontSize);

        new SeparatedTextPdfFormWriter(yearElement, getYearString(content))
                .writeOn(pdfWriter);

    }

    private String getDaySting(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
    }

    private String getMonthSting(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.format("%02d", calendar.get(Calendar.MONTH) + 1);
    }

    private String getYearString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.YEAR));
    }
}
