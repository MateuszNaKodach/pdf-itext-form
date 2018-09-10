package io.github.nowakprojects.pdfitextform;

import java.util.Date;

/**
 * Created by Marcin
 */
public class ExampleDataFill {

    public static void main(String[] args) throws Exception {
//        PdfDeclaration pdfDeclaration =
//                PdfDeclaration.
//                        withElements(
//                                new DatePdfElement("data", new Date(), 96, 247, 20,
//                                        15f, 5)
//                        );
//
//        PdfFillTool.generatePdfFromDeclaration(pdfDeclaration);
//        PdfFillTool.mergePdfsLayers(Config.SRC, Config.NEW_DOCUMENT, Config.DEST);

        DataReader.readFillSchema("src/main/resources/config.xml");
    }

}
