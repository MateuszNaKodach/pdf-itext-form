package io.github.nowakprojects.pdfitextform;

import java.util.Date;

/**
 * Created by Marcin
 */
public class ExampleDataFill {

    public static void main(String[] args) throws Exception {
        PdfDeclaration pdfDeclaration =
                PdfDeclaration.
                        withElements(
                                new DatePdfElement("data", new Date(),
                                        PdfPositionFactory.getPosition(PositionType.FROM_BOTTOM_LEFT)
                                                .withCoordinates(96, 247),
                                        20, 15f, 5)
                        );

//        PdfFillTool.generatePdfFromDeclaration(pdfDeclaration);
//        PdfFillTool.mergePdfsLayers(Config.SRC, Config.NEW_DOCUMENT, Config.DEST);

    }

}
