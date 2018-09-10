package io.github.nowakprojects.pdfitextform;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Marcin
 */
class PdfToFill {

    private final String pdfPath;
    private final Map<Integer, Set<PdfElementCreator>> schemaPages;

    PdfToFill(String pdfPath, Map<Integer, Set<PdfElementCreator>> schemaPages) {
        this.pdfPath = pdfPath;
        this.schemaPages = schemaPages;
    }

    void preparePdf(Map<String, String> values, String outputFilePath) throws Exception {

        Map<Integer, byte[]> topPages = new HashMap<>();

        for (Integer pageNumber : schemaPages.keySet()) {
            topPages.put(pageNumber, PdfFillTool.generatePdfBytesFromDeclaration(schemaPages.get(pageNumber), values));
        }

        PdfFillTool.mergePdfsLayers(pdfPath, topPages, outputFilePath);

    }

}
