package io.github.nowakprojects.pdfitextform;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class DateUtils {

    static Date getDateFrom(String dateString) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new IllegalArgumentException(dateString + " cannot be parsed to date with pattern " + pattern + " !");
        }
    }

}
