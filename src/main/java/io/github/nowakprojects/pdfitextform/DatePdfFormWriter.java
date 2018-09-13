package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.pdf.PdfWriter;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
/*
public class DatePdfFormWriter extends PdfFormWriter<DatePdfElement, Date> {


    public DatePdfFormWriter(DatePdfElement pdfElement, Date content) {
        super(pdfElement, content);
    }

    @Override
    void writeOn(PdfWriter pdfWriter) {

    }*/
/*

    public Set<AbsoluteTextPdfElement> getSimpleElements() {

        Set<AbsoluteTextPdfElement> elements = new HashSet<>();

        elements.addAll(createAbsoluteTextPdfElement("_day", getDaySting(), pdfPosition.getX(),
                pdfPosition.getY()).getSimpleElements());

        elements.addAll(createAbsoluteTextPdfElement("_month", getMonthSting(),
                pdfPosition.getX() + characterWidth * 2 + spaceBetweenGroup, pdfPosition.getY())
                .getSimpleElements());

        elements.addAll(createAbsoluteTextPdfElement("_year", getYearString(),
                pdfPosition.getX() + characterWidth * 4 + spaceBetweenGroup * 2, pdfPosition.getY())
                .getSimpleElements());

        return elements;
    }

    private SeparatedTextPdfElement createAbsoluteTextPdfElement(
            String tagPostfix,
            String content,
            float xTopLeft,
            float yTopLeft) {
        return new SeparatedTextPdfElement(tag + tagPostfix, content,
                PdfPositionFactory.getPosition(PositionType.FROM_BOTTOM_LEFT).withCoordinates(xTopLeft, yTopLeft), fontSize, characterWidth);
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

    @Override
    public void writePdfElement(PdfWriter pdfWriter, DatePdfElement pdfElement) {
        getSimpleElements().forEach(element -> element.writePdfElement(pdfWriter));
    }*/
//}
