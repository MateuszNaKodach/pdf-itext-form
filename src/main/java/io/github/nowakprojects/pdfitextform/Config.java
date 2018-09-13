package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;

import java.io.IOException;

public class Config {

    public static final String CARDO_REGULAR_FONT = "src/main/resources/fonts/Cardo-Regular.ttf";

    public static final int FONT_SIZE = 12;
    public static final String FORM_SOURCE = "src/main/resources/documents/pdfs/source/ZAP-3-04.pdf";
    public static final String DEST_DIRECTORY = "src/main/resources/documents/pdfs/filled/";

    public BaseFont baseFont = BaseFont.createFont(Config.CARDO_REGULAR_FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

    Config() throws IOException, DocumentException {
    }
}
