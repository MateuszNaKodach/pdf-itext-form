package io.github.nowakprojects;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;

import java.io.IOException;

public class Config {

    public static final int FONT_SIZE = 10;
    public static final String ZAP_SOURCE = "src/main/resources/documents/pdfs/source/ZAP-3-04.pdf";
    public static final String DEST_DIRECTORY = "src/main/resources/documents/pdfs/filled/";

    public static BaseFont baseFont;
    private static final String CARDO_REGULAR_FONT = "src/main/resources/fonts/Cardo-Regular.ttf";
    private static final String ARIAL_FONT = "src/main/resources/fonts/arial.ttf";


    static {
        try {
            baseFont = BaseFont.createFont(Config.ARIAL_FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
