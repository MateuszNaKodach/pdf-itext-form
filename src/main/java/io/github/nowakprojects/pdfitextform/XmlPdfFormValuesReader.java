package io.github.nowakprojects.pdfitextform;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class XmlPdfFormValuesReader implements PdfFormValuesReader {

    @Override
    public PdfFormValues readFromFile(String filePath) {
        try {
            final Map<String, String> valuesByTag = tryToReadFromFile(filePath);
            return new PdfFormValues(valuesByTag);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new IllegalArgumentException("Exception!", e);
        }
    }

    private Map<String, String> tryToReadFromFile(String filePath) throws ParserConfigurationException, IOException, SAXException {
        Map<String, String> result = new HashMap<>();
        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        FileInputStream input = new FileInputStream(filePath);
        Document doc = builder.parse(input);
        doc.getDocumentElement().normalize();

        Element element = doc.getDocumentElement();
        NodeList nodeList = element.getChildNodes();

        for (int temp = 0; temp < nodeList.getLength(); temp++) {
            Node nNode = nodeList.item(temp);
            result.put(nNode.getNodeName(), nNode.getTextContent());
        }

        return result;
    }


}

class PdfFormValues {

    private final Map<String, String> valuesByTag;

    PdfFormValues(Map<String, String> valuesByTag) {
        this.valuesByTag = valuesByTag;
    }

    String getValueByTag(String tag) {
        return valuesByTag.get(tag);
    }


}