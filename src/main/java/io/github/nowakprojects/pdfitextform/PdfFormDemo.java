package io.github.nowakprojects.pdfitextform;

import io.github.nowakprojects.Config;

import static io.github.nowakprojects.pdfitextform.AlternativePdfGroup.orderedOr;
import static io.github.nowakprojects.pdfitextform.PdfElements.elements;
import static io.github.nowakprojects.pdfitextform.PdfGroups.groups;
import static io.github.nowakprojects.pdfitextform.TemplatePart.staticPart;
import static io.github.nowakprojects.pdfitextform.TemplatePart.tagPart;

/*
Documentation iText:
https://developers.itextpdf.com/sites/default/files/attachments/PR%20-%20iText%20in%20Action%20-%20Second%20edition%20e-book.pdf

TODO:
Definicje pliku, np. PdfFormSchema - który może z XML odczytać dane i stworzyć PdfFormSchema
Taki PdfFormSchema musi zawierać to w jakim miejscu jest dany tag i jaka ma odległość. Może też ewentualnie mieć zmienny rozmiar czcionki, ograniczenie na wielkość pola wpisywania itp!
 */

/*
TODO: Jaka czcionka i rozmiar?
11. Prefix ulicy?
16. Brak pola!

34. Posiadacz rachunku!!! W jaki sposob zapisac? 2 imiona czy jak?

Brak: 34,40,16!
 */
public class PdfFormDemo {

    private static final int LEFT_PDF_SIDE = 54;

    public static void main(String[] args) {

        PdfFormSchema pdfFormSchema = PdfFormSchema
                .withDefaultFontSize(FontSize.withValue(Config.FONT_SIZE))
                .addPageElements(
                        PdfPageNumber.from(1),
                        groups(
                                MultilineTextPdfElement.builder()
                                        .withTag(getASectionTag("PlaceOfSubmission"))
                                        .withMaxSize(15, 524)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE, 579),

                                MultilineTextPdfElement.builder()
                                        .withTag(getBSectionTag("SubmitterName/FirstName"))
                                        .withMaxSize(15, 250)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE + 260, 508),
                                MultilineTextPdfElement.builder()
                                        .withTag(getBSectionTag("SubmitterName/LastName"))
                                        .withMaxSize(15, 250)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE, 508),
                                MultilineTextPdfElement.builder()
                                        .withTag(getBSectionTag("SubmitterAddress/Country"))
                                        .withMaxSize(15, 105)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE, 460),
                                MultilineTextPdfElement.builder()
                                        .withTag(getBSectionTag("SubmitterAddress/Voivodenship"))
                                        .withMaxSize(15, 215)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE + 110, 460),
                                MultilineTextPdfElement.builder()
                                        .withTag(getBSectionTag("SubmitterAddress/County"))
                                        .withMaxSize(15, 200)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE + 330, 460),
                                MultilineTextPdfElement.builder()
                                        .withTag(getBSectionTag("SubmitterAddress/Commune"))
                                        .withMaxSize(15, 120)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE, 435),
                                MultilineTextPdfElement.builder()
                                        .withTags(
                                                getBSectionTag("SubmitterAddress/StreetKind"),
                                                getBSectionTag("SubmitterAddress/Street")
                                        )
                                        .withMaxSize(15, 305)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE + 135, 435),
                                MultilineTextPdfElement.builder()
                                        .withTag(getBSectionTag("SubmitterAddress/HouseNumber"))
                                        .withMaxSize(15, 55)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE + 405, 435),
                                MultilineTextPdfElement.builder()
                                        .withTag(getBSectionTag("SubmitterAddress/FlatNumber"))
                                        .withMaxSize(15, 55)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE + 460, 435),
                                MultilineTextPdfElement.builder()
                                        .withTag(getBSectionTag("SubmitterAddress/City"))
                                        .withMaxSize(15, 265)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE, 410),
                                MultilineTextPdfElement.builder()
                                        .withTag(getBSectionTag("SubmitterAddress/ZipCode"))
                                        .withMaxSize(15, 70)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE + 272, 410),
                                orderedOr(
                                        SeparatedTextPdfElement.builder()
                                                .withTag(getBSectionTag("ContactData/CellPhone"))
                                                .withCharacterWidth(15)
                                                .positionedFromBottomLeft(190, 355),
                                        SeparatedTextPdfElement.builder()
                                                .withTag(getBSectionTag("ContactData/HomePhone"))
                                                .withCharacterWidth(15)
                                                .positionedFromBottomLeft(190, 355)
                                ),
                                MultilineTextPdfElement.builder()
                                        .withTag(getBSectionTag("Fax"))
                                        .withMaxSize(15, 250)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE, 338),
                                MultilineTextPdfElement.builder()
                                        .withTag(getBSectionTag("ContactData/EmailAddress"))
                                        .withMaxSize(15, 250)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE + 260, 338),
                                MultilineTextPdfElement.builder()
                                        .withTag(getBSectionTag("ElectronicAddress"))
                                        .withMaxSize(15, 360)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE, 238),
                                CheckBoxPdfElement.builder()
                                        .withTag(getBSectionTag("ElectronicAddressResignation"))
                                        .positionedFromBottomLeft(488.5f, 226.5f),

                                MultilineTextPdfElement.builder()
                                        .withTag(getBSectionTag("SubmitterCorrAddress/Country"))
                                        .withMaxSize(15, 105)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE, 460 - 267),
                                MultilineTextPdfElement.builder()
                                        .withTag(getBSectionTag("SubmitterCorrAddress/Voivodenship"))
                                        .withMaxSize(15, 215)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE + 110, 460 - 267),
                                MultilineTextPdfElement.builder()
                                        .withTag(getBSectionTag("SubmitterCorrAddress/County"))
                                        .withMaxSize(15, 170)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE + 340, 460 - 267),
                                MultilineTextPdfElement.builder()
                                        .withTag(getBSectionTag("SubmitterCorrAddress/Commune"))
                                        .withMaxSize(15, 120)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE, 435 - 267),
                                MultilineTextPdfElement.builder()
                                        .withTags(
                                                getBSectionTag("SubmitterCorrAddress/StreetKind"),
                                                getBSectionTag("SubmitterCorrAddress/Street")
                                        )
                                        .withMaxSize(15, 305)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE + 135, 435 - 267),
                                MultilineTextPdfElement.builder()
                                        .withTag(getBSectionTag("SubmitterCorrAddress/HouseNumber"))
                                        .withMaxSize(15, 55)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE + 405, 435 - 267),
                                MultilineTextPdfElement.builder()
                                        .withTag(getBSectionTag("SubmitterCorrAddress/FlatNumber"))
                                        .withMaxSize(15, 55)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE + 460, 435 - 267),
                                MultilineTextPdfElement.builder()
                                        .withTag(getBSectionTag("SubmitterCorrAddress/City"))
                                        .withMaxSize(15, 265)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE, 410 - 267),
                                MultilineTextPdfElement.builder()
                                        .withTag(getBSectionTag("SubmitterCorrAddress/ZipCode"))
                                        .withMaxSize(15, 70)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE + 272, 410 - 267)
                        )
                ).addPageElements(
                        PdfPageNumber.from(2),
                        groups(
                                MultilineTextPdfElement.builder()
                                        .withTag(getBSectionTag("PersonalAccountData/BankCountry"))
                                        .withMaxSize(15, 530)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE, 725),
                                MultilineTextPdfElement.builder()
                                        .withTag(getBSectionTag("PersonalAccountData/BankName"))
                                        .withMaxSize(15, 530)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE, 700),
                                MultilineTextPdfElement.builder()
                                        .withTags(
                                                getBSectionTag("PersonalAccountData/AccountOwner/FirstName"),
                                                getBSectionTag("PersonalAccountData/AccountOwner/LastName")
                                        )
                                        .withMaxSize(15, 530)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE, 675),
                                CheckBoxPdfElement.builder()
                                        .withTag(getBSectionTag("ResignationFromReturnToAccount"))
                                        .positionedFromBottomLeft(528.5f, 640.5f),
                                MultilineTextPdfElement.builder()
                                        .withTag(getBSectionTag("PersonalAccountData/IBAN"))
                                        .withMaxSize(15, 250)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE + 60, 648),
                                MultilineTextPdfElement.builder()
                                        .withTag(getBSectionTag("PersonalAccountData/SWIFT"))
                                        .withMaxSize(15, 65)
                                        .positionedFromBottomLeft(430, 648),

                                MultilineTextPdfElement.builder()
                                        .withTag(getCSectionTag("RepresentativePersonName/FirstName"))
                                        .withMaxSize(15, 250)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE, 584),
                                MultilineTextPdfElement.builder()
                                        .withTag(getCSectionTag("RepresentativePersonName/LastName"))
                                        .withMaxSize(15, 250)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE + 260, 584),

                                orderedOr
                                        (
                                                SeparatedTextPdfElement.builder()
                                                        .withTag(getCSectionTag("RepresentativePersonPESEL"))
                                                        .withCharacterWidth(15)
                                                        .positionedFromBottomLeft(228, 550),
                                                SeparatedTextPdfElement.builder()
                                                        .withTag(getCSectionTag("RepresentativePersonNIP"))
                                                        .withCharacterWidth(15)
                                                        .positionedFromBottomLeft(228, 550)
                                        ).attachToPrioritized
                                        (
                                                AbsoluteTextPdfElement.builder()
                                                        .withTag("NIP_ERASURE")
                                                        .positionedFromBottomLeft(LEFT_PDF_SIDE + 10, 564)
                                                        .withDefaultContent("________________")
                                        )
                                        .attachToAlternative(
                                                AbsoluteTextPdfElement.builder()
                                                        .withTag("PESEL_ERASURE")
                                                        .positionedFromBottomLeft(LEFT_PDF_SIDE + 105, 564)
                                                        .withDefaultContent("________")
                                        ),

                                MultilineTextPdfElement.builder()
                                        .withTemplate(
                                                tagPart(getCSectionTag("RepresentativeCorrAddress/StreetKind")),
                                                tagPart(getCSectionTag("RepresentativeCorrAddress/Street")),
                                                tagPart(getCSectionTag("RepresentativeCorrAddress/HouseNumber")),
                                                staticPart("/"),
                                                tagPart(getCSectionTag("RepresentativeCorrAddress/FlatNumber"))
                                        )
                                        .withMaxSize(15, 530)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE, 535),

                                MultilineTextPdfElement.builder()
                                        .withTags(
                                                getCSectionTag("RepresentativeCorrAddress/ZipCode"),
                                                getCSectionTag("RepresentativeCorrAddress/City")
                                        )
                                        .withMaxSize(15, 530)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE, 523),
                                MultilineTextPdfElement.builder()
                                        .withTag(
                                                getCSectionTag("RepresentativeCorrAddress/Country")
                                        )
                                        .withMaxSize(15, 530)
                                        .positionedFromBottomLeft(LEFT_PDF_SIDE, 513),

                                DatePdfElement.builder()
                                        .withTag(getCSectionTag("OperationDate"))
                                        .withCharacterWidth(15)
                                        .withSpaceBetweenGroup(5)
                                        .positionedFromBottomLeft(110, 476)
                        )
                );


        final PdfFormValuesReader pdfFormValuesReader = new XmlPdfFormValuesReader();
        final PdfFillTool pdfFillTool = PdfFillTool.withDefinedOutputDirectory(Config.DEST_DIRECTORY);

        final PdfFormValues pdfFormValuesNormal = pdfFormValuesReader.readFromFile("src/main/resources/input-test-normal.xml");
        pdfFillTool.fillPdfForm(new PdfForm(Config.ZAP_SOURCE, pdfFormSchema, pdfFormValuesNormal), "filledPdf-normal.pdf");

        final PdfFormValues pdfFormValuesMax = pdfFormValuesReader.readFromFile("src/main/resources/input-test-max.xml");
        pdfFillTool.fillPdfForm(new PdfForm(Config.ZAP_SOURCE, pdfFormSchema, pdfFormValuesMax), "filledPdf-max.pdf");
    }

    private static String getASectionTag(String tag) {
        return getSectionTag("A", tag);
    }

    private static String getBSectionTag(String tag) {
        return getSectionTag("B", tag);
    }

    private static String getCSectionTag(String tag) {
        return getSectionTag("C", tag);
    }

    private static String getSectionTag(String section, String tag) {
        return "Document/ACADR00001PrintData/" + section + "/" + tag;
    }

}
