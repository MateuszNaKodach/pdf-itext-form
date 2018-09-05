package io.github.nowakprojects.pdfitextform;
/*
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

class PdfForm {

    private static final String DEST = "documents\\pdfs\\filled\\ZAP-3-04-filled.pdf";

    private final String inputFilePath;

    private PdfForm(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    static PdfForm fromFile(String filePath) {
        return new PdfForm(filePath);
    }


    void tryToFillPdfFormWithXml() throws IOException, DocumentException {

    }

--------
    void tryToFillPdfFormWithXml() throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        PdfReader reader = new PdfReader(inputFilePath);
        FileOutputStream fos = new FileOutputStream(DEST);
        for (int page = 1; page <= 1; page++) {
            fos.write(PdfTextExtractor.getTextFromPage(reader, page).getBytes(StandardCharsets.UTF_8));
        }
        fos.flush();
        fos.close();
    }
-------------
    private Optional<InputStream> getPdfFileInputStream() {
        try {
            InputStream inputStream = new ClassPathResource(inputFilePath).getInputStream();
            return Optional.of(inputStream);
        } catch (IOException ioException) {
            return Optional.empty();
        }
    }

     void tryToFillPdfFormWithXml() throws IOException {
        Optional<InputStream> pdfOptional = getPdfFileInputStream();
        if (!pdfOptional.isPresent()) {
            throw new FileNotFoundException("File with path " + inputFilePath + " not found!");
        }

        InputStream pdfInputStream = pdfOptional.get();
    }

}
*/