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
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Marcin
 */
class DataReader {

    static Map<String, String> readData(String filePath)
            throws ParserConfigurationException, IOException, SAXException {
        Map<String, String> mapToReturn = new TreeMap<>();

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
            mapToReturn.put(nNode.getNodeName(), nNode.getTextContent());
        }

        return mapToReturn;
    }

}
