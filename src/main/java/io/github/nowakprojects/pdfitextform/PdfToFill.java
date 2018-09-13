package io.github.nowakprojects.pdfitextform;

import java.util.HashMap;
import java.util.Map;

class PdfToFill {

    private final String pdfPath;
    private final PdfFormSchema schemaPages;

    PdfToFill(String pdfPath, PdfFormSchema pdfFormSchema) {
        this.pdfPath = pdfPath;
        this.schemaPages = pdfFormSchema;
    }

    void preparePdf(PdfFormValues pdfFormValues, String outputFilePath) throws Exception {

        Map<Integer, byte[]> topPages = new HashMap<>();

        for (PdfPageNumber pageNumber : schemaPages.getPages()) {
            topPages.put(pageNumber.getValue(), PdfFillTool.generatePdfBytesFromDeclaration(schemaPages.getElementsByPage(pageNumber), pdfFormValues));
        }

        PdfFillTool.mergePdfsLayers(pdfPath, topPages, outputFilePath);

    }

    /*
    void showTemplate(String outputFilePath) throws Exception {

        Map<Integer, byte[]> topPages = new HashMap<>();

        for (Integer pageNumber : schemaPages.keySet()) {
            topPages.put(pageNumber, PdfFillTool.generatePdfBytesTemplateFromDeclaration(schemaPages.get(pageNumber)));
        }

        PdfFillTool.mergePdfsLayers(pdfPath, topPages, outputFilePath);
    }*/

}
