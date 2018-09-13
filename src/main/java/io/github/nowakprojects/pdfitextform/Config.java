package io.github.nowakprojects.pdfitextform;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;

import java.io.IOException;

class Config {

    static final String CARDO_REGULAR_FONT = "src/main/resources/fonts/Cardo-Regular.ttf";

    static final int FONT_SIZE = 12;
    static final String SRC = "src/main/resources/documents/pdfs/source/ZAP-3-04.pdf";
    static final String DEST = "src/main/resources/documents/pdfs/filled/ZAP-3-04-filled7.pdf";

    BaseFont baseFont = BaseFont.createFont(Config.CARDO_REGULAR_FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

    Config() throws IOException, DocumentException {
    }
}
