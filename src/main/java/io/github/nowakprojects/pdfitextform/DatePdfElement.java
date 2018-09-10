package io.github.nowakprojects.pdfitextform;

import java.util.*;

/**
 * Created by Marcin
 */
public class DatePdfElement implements ComplicatedPdfElement, PdfElement {

    private final String tag;
    private final Date date;
    private final float characterWidth;
    private final float spaceBetweenGroup;
    private final PdfPosition pdfPosition;
    private final float fontSize;

    DatePdfElement(String tag, Date date, PdfPosition pdfPosition, float characterWidth,
                          float spaceBetweenGroup, float fontSize) {
        this.tag = tag;
        this.date = date;
        this.characterWidth = characterWidth;
        this.spaceBetweenGroup = spaceBetweenGroup;
        this.pdfPosition = pdfPosition;
        this.fontSize = fontSize;
    }

    @Override
    public List<SimpleTextPdfElement> getSimpleTextElements() {
        List<SimpleTextPdfElement> elements = new ArrayList<>();

        elements.addAll(createAbsoluteTextPdfElement("_day", getDaySting(), pdfPosition.getX(),
                pdfPosition.getY()).getSimpleTextElements());

        elements.addAll(createAbsoluteTextPdfElement("_month", getMonthSting(),
                pdfPosition.getX() + characterWidth * 2 + spaceBetweenGroup, pdfPosition.getY())
                .getSimpleTextElements());

        elements.addAll(createAbsoluteTextPdfElement("_year", getYearString(),
                pdfPosition.getX() + characterWidth * 4 + spaceBetweenGroup * 2, pdfPosition.getY())
                .getSimpleTextElements());

        return elements;
    }

    private SeparatedTextPdfElement createAbsoluteTextPdfElement(String tagPostfix, String content, float xTopLeft,
                                                                 float yTopLeft) {
        return new SeparatedTextPdfElement(tag+tagPostfix, content,
                PdfPositionFactory.getPosition(PositionType.FROM_BOTTOM_LEFT).withCoordinates(xTopLeft, yTopLeft),
                fontSize, characterWidth);
    }

    private String getDaySting() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
    }

    private String getMonthSting() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.format("%02d", calendar.get(Calendar.MONTH) + 1);
    }

    private String getYearString() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    public float getCharacterWidth() {
        return characterWidth;
    }

    public float getSpaceBetweenGroup() {
        return spaceBetweenGroup;
    }

    public PdfPosition getPdfPosition() {
        return pdfPosition;
    }

    public float getFontSize() {
        return fontSize;
    }

    static class Configuration {

        private final String tag;
        private final float characterWidth;
        private final float spaceBetweenGroup;
        private final PdfPosition pdfPosition;
        private final float fontSize;

        Configuration(String tag, PdfPosition pdfPosition, float fontSize, float characterWidth,
                      float spaceBetweenGroup) {
            this.fontSize = fontSize;
            this.pdfPosition = pdfPosition;
            this.tag = tag;
            this.characterWidth = characterWidth;
            this.spaceBetweenGroup = spaceBetweenGroup;
        }

        DatePdfElement create(Date date) {
            return new DatePdfElement(tag, date, pdfPosition, characterWidth, spaceBetweenGroup, fontSize);
        }

    }
}
