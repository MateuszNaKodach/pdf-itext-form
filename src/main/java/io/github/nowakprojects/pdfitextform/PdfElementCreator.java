package io.github.nowakprojects.pdfitextform;

import java.text.ParseException;

/**
 * Created by Marcin
 */
interface PdfElementCreator {

    PdfElement create(String content) throws ParseException;

    String getTag();
}
