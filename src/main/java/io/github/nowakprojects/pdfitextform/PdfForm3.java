package io.github.nowakprojects.pdfitextform;

import java.util.*;

/*
Documentation iText:
https://developers.itextpdf.com/sites/default/files/attachments/PR%20-%20iText%20in%20Action%20-%20Second%20edition%20e-book.pdf

TODO:
Definicje pliku, np. PdfFormDeclaration - który może z XML odczytać dane i stworzyć PdfDeclaration
Taki PdfFormDeclaration musi zawierać to w jakim miejscu jest dany tag i jaka ma odległość. Może też ewentualnie mieć zmienny rozmiar czcionki, ograniczenie na wielkość pola wpisywania itp!
 */
public class PdfForm3 {

    public static void main(String[] args) throws Exception {

        Map<String, String> data = DataReader.readData("src/main/resources/input.xml");
//        System.out.println(data.size());
//        for (String key : data.keySet()) {
//            System.out.println(key + " --||-- " + data.get(key));
//        }

        PdfDeclaration pdfDeclaration =
                PdfDeclaration.
                        withElements(
                                AbsoluteTextPdfElement.builder()
                                        .withTag("pesel")
                                        .andContent("91032312312")
                                        .positionedFromBottomLeft(63, 785)
                                        .withCharacterWidth(15),
                                AbsoluteTextPdfElement.builder()
                                        .withTag("imie")
                                        .andContent("Jan")
                                        .positionedFromBottomLeft(330, 495),
                                AbsoluteTextPdfElement.builder()
                                        .withTag("nazwisko")
                                        .andContent("Kowalski Karakuła")
                                        .positionedFromBottomLeft(63, 495)
                        );

        Set<PdfElementCreator> page1 = new HashSet<>(), page2 = new HashSet<>();

        page1.add(new SeparatedTextPdfElement.Configuration("pesel",
                PdfPositionFactory.getPosition(PositionType.FROM_BOTTOM_LEFT).withCoordinates(63, 785),
                Config.FONT_SIZE, 15));
        page1.add(new SimpleTextPdfElement.Configuration("imie",
                PdfPositionFactory.getPosition(PositionType.FROM_BOTTOM_LEFT).withCoordinates(330, 495),
                Config.FONT_SIZE));
        page1.add(new SimpleTextPdfElement.Configuration("nazwisko",
                PdfPositionFactory.getPosition(PositionType.FROM_BOTTOM_LEFT).withCoordinates(63, 495),
                Config.FONT_SIZE));

        page2.add(new DatePdfElement.Configuration("data",
                PdfPositionFactory.getPosition(PositionType.FROM_BOTTOM_LEFT).withCoordinates(63, 495),
                Config.FONT_SIZE, 15, 5));

        Map<Integer, Set<PdfElementCreator>> map = new HashMap<>();
        map.put(1, page1);
        map.put(2, page2);

        PdfToFill pdfToFill = new PdfToFill(Config.SRC, map);

        pdfToFill.preparePdf(data, Config.DEST);

//        PdfFillTool.generatePdfFromDeclaration(pdfDeclaration);
//        PdfFillTool.mergePdfsLayers(Config.SRC, Config.NEW_DOCUMENT, Config.DEST);
    }

}
