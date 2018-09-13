package io.github.nowakprojects.pdfitextform;

import java.util.*;

import static io.github.nowakprojects.pdfitextform.PdfElements.elements;

/*
Documentation iText:
https://developers.itextpdf.com/sites/default/files/attachments/PR%20-%20iText%20in%20Action%20-%20Second%20edition%20e-book.pdf

TODO:
Definicje pliku, np. PdfFormSchema - który może z XML odczytać dane i stworzyć PdfFormSchema
Taki PdfFormSchema musi zawierać to w jakim miejscu jest dany tag i jaka ma odległość. Może też ewentualnie mieć zmienny rozmiar czcionki, ograniczenie na wielkość pola wpisywania itp!
 */
public class PdfForm3 {

    public static void main(String[] args) throws Exception {

        PdfFormSchema pdfFormSchema = PdfFormSchema
                .withDefaultFontSize(Config.FONT_SIZE)
                .addPageElements(
                        PdfPageNumber.from(1),
                        elements(
                                AbsoluteTextPdfElement.builder()
                                        .withTag("naczelnikUrzeduSkarbowego")
                                        .positionedFromBottomLeft(63, 568)
                        )
                ).addPageElements(
                        PdfPageNumber.from(2),
                        elements(
                                AbsoluteTextPdfElement.builder()
                                        .withTag("naczelnikUrzeduSkarbowego")
                                        .positionedFromBottomLeft(63, 568)
                        )
                );


        final PdfFormValuesReader pdfFormValuesReader = new XmlPdfFormValuesReader();
        final PdfFormValues pdfFormValues = pdfFormValuesReader.readFromFile("src/main/resources/input.xml");

        //Map<String, String> data = DataReader.readData("src/main/resources/input.xml");

        Set<PdfElementCreator> page1 = new HashSet<>(), page2 = new HashSet<>();

        /*
        page1.add(separatedTextPdfElementConfiguration("pesel", getBottomLeftPdfPosition(63, 785), 15));
        page1.add(simpleTextPdfElementConfiguration("naczelnikUrzeduSkarbowego", getBottomLeftPdfPosition(63, 568)));
        page1.add(simpleTextPdfElementConfiguration("imie", getBottomLeftPdfPosition(330, 495)));
        page1.add(simpleTextPdfElementConfiguration("nazwisko", getBottomLeftPdfPosition(63, 495)));
        page1.add(simpleTextPdfElementConfiguration("kraj", getBottomLeftPdfPosition(63, 450)));
        page1.add(simpleTextPdfElementConfiguration("wojewodztwo", getBottomLeftPdfPosition(170, 450)));
        page1.add(simpleTextPdfElementConfiguration("powiat", getBottomLeftPdfPosition(390, 450)));
        page1.add(simpleTextPdfElementConfiguration("gmina", getBottomLeftPdfPosition(63, 425)));
        page1.add(simpleTextPdfElementConfiguration("nrDomu", getBottomLeftPdfPosition(460, 425)));
        page1.add(simpleTextPdfElementConfiguration("miejscowosc", getBottomLeftPdfPosition(63, 400)));
        page1.add(simpleTextPdfElementConfiguration("kodPocztowy", getBottomLeftPdfPosition(330, 400)));
        page1.add(simpleTextPdfElementConfiguration("poczta", getBottomLeftPdfPosition(400, 400)));
        page1.add((new MultilineTextPdfElement.Configuration("multi", getBottomLeftPdfPosition(10, 400), Config.FONT_SIZE, 50, 100)));
        page2.add(new DatePdfElement.Configuration("data", getBottomLeftPdfPosition(110, 476), Config.FONT_SIZE,
                15, 5));
*/

        Map<Integer, Set<PdfElementCreator>> map = new HashMap<>();
        map.put(1, page1);
        map.put(2, page2);


        PdfToFill pdfToFill = new PdfToFill(Config.SRC, pdfFormSchema);

        pdfToFill.preparePdf(pdfFormValues, Config.DEST);

//        pdfToFill.showTemplate(Config.DEST);

    }


}
