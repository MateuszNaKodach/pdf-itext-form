package io.github.nowakprojects.pdfitextform;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Marcin
 */
public class DatePdfElement implements ComplicatedPdfElement {
    private final String tag;
    private final Date date;
    private final float characterWidth;
    private final float groupSpace;
    private final float fontSize;
    private final PdfPosition pdfPosition;

    public DatePdfElement(String tag, Date date, float xTopLeft, float yTopLeft, float fontSize, float characterWidth,
                   float groupSpace) {
        this.tag = tag;
        this.date = date;
        this.fontSize = fontSize;
        this.characterWidth = characterWidth;
        this.groupSpace = groupSpace;
        pdfPosition = PdfPositionFactory.getPosition(PositionType.FROM_TOP_LEFT).withCoordinates(xTopLeft, yTopLeft);
    }

    @Override
    public List<AbsoluteTextPdfElement> getSimpleTextElements() {
        List<AbsoluteTextPdfElement> elements = new ArrayList<>();

        elements.add(createAbsoluteTextPdfElement("_day", getDaySting(), pdfPosition.getX(),
                pdfPosition.getY()));

        elements.add(createAbsoluteTextPdfElement("_month", getMonthSting(),
                pdfPosition.getX() + characterWidth*2 + groupSpace, pdfPosition.getY()));

        elements.add(createAbsoluteTextPdfElement("_year", getYearString(),
                pdfPosition.getX() + characterWidth*4 + groupSpace*2, pdfPosition.getY()));

        return elements;
    }

    private AbsoluteTextPdfElement createAbsoluteTextPdfElement(String tagPostfix, String content, float xTopLeft,
                                                                float yTopLeft) {
        return AbsoluteTextPdfElement.builder()
                .withTag(tag + tagPostfix)
                .andContent(content)
                .positionedFromTopLeft(xTopLeft, yTopLeft)
                .withCharacterWidth(characterWidth);
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

    public float getGroupSpace() {
        return groupSpace;
    }

    public float getFontSize() {
        return fontSize;
    }

    public PdfPosition getPdfPosition() {
        return pdfPosition;
    }
}
