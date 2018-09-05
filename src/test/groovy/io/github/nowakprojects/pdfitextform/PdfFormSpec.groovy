package io.github.nowakprojects.pdfitextform

import spock.lang.Specification

class PdfFormSpec extends Specification {


    def "test"() {
        when:
        def pdfForm = PdfForm
                .fromFile("documents\\pdfs\\source\\ZAP-3-04.pdf")
        pdfForm.tryToFillPdfFormWithXml()

        then:
        pdfForm != null
    }
}
