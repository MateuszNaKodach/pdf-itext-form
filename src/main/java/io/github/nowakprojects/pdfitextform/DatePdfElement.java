package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.pdf.PdfWriter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
/*
public class DatePdfElement extends AbstractPdfElement {

    private final Date date;
    private final float characterWidth;
    private final float spaceBetweenGroup;

    private DatePdfElement(
            String tag,
            Date date,
            PdfPosition pdfPosition,
            float characterWidth,
            float spaceBetweenGroup,
            float fontSize) {
        super(tag, pdfPosition, fontSize);
        this.date = date;
        this.characterWidth = characterWidth;
        this.spaceBetweenGroup = spaceBetweenGroup;
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

    static class Configuration implements PdfElementCreator {

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

        @Override
        public DatePdfElement create(String dateString) throws ParseException {
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date date = simpleDateFormat.parse(dateString);
            return new DatePdfElement(tag, date, pdfPosition, characterWidth, spaceBetweenGroup, fontSize);
        }

        @Override
        public String getTag() {
            return tag;
        }

        @Override
        public void printTemplate(PdfWriter writer) {
            new DatePdfElement(tag, new Date(), pdfPosition, characterWidth, spaceBetweenGroup, fontSize).writePdfElement(writer);
        }

    }
}*/
