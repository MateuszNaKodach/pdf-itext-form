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

    private static PdfPosition getPosition(float x, float y) {
        return PdfPositionFactory.getPosition(PositionType.FROM_BOTTOM_LEFT).withCoordinates(x, y);
    }

    private static SimpleTextPdfElement.Configuration simpleTextPdfElementConfiguration(String tag,
                                                                                        PdfPosition position) {
        return new SimpleTextPdfElement.Configuration(tag, position, Config.FONT_SIZE);
    }

    private static SeparatedTextPdfElement.Configuration separatedTextPdfElementConfiguration(String tag,
                                                                                              PdfPosition position,
                                                                                              float width) {
        return new SeparatedTextPdfElement.Configuration(tag, position, Config.FONT_SIZE, width);
    }

    public static void main(String[] args) throws Exception {

        Map<String, String> data = DataReader.readData("src/main/resources/input.xml");

        Set<PdfElementCreator> page1 = new HashSet<>(), page2 = new HashSet<>();

        page1.add(separatedTextPdfElementConfiguration("pesel", getPosition(63, 785), 15));
        page1.add(simpleTextPdfElementConfiguration("naczelnikUrzeduSkarbowego", getPosition(63, 568)));
        page1.add(simpleTextPdfElementConfiguration("imie", getPosition(330, 495)));
        page1.add(simpleTextPdfElementConfiguration("nazwisko", getPosition(63, 495)));
        page1.add(simpleTextPdfElementConfiguration("kraj", getPosition(63, 450)));
        page1.add(simpleTextPdfElementConfiguration("wojewodztwo", getPosition(170, 450)));
        page1.add(simpleTextPdfElementConfiguration("powiat", getPosition(390, 450)));
        page1.add(simpleTextPdfElementConfiguration("gmina", getPosition(63, 425)));
        page1.add(simpleTextPdfElementConfiguration("nrDomu", getPosition(460, 425)));
        page1.add(simpleTextPdfElementConfiguration("miejscowosc", getPosition(63, 400)));
        page1.add(simpleTextPdfElementConfiguration("kodPocztowy", getPosition(330, 400)));
        page1.add(simpleTextPdfElementConfiguration("poczta", getPosition(400, 400)));

        page2.add(new DatePdfElement.Configuration("data", getPosition(110, 476), Config.FONT_SIZE,
                15, 5));


        Map<Integer, Set<PdfElementCreator>> map = new HashMap<>();
        map.put(1, page1);
        map.put(2, page2);
        PdfToFill pdfToFill = new PdfToFill(Config.SRC, map);

        pdfToFill.preparePdf(data, Config.DEST);

//        pdfToFill.showTemplate(Config.DEST);

    }

}
