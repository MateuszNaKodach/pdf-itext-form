package io.github.nowakprojects.pdfitextform;

import java.util.Map;
import java.util.TreeMap;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;

/**
 * Created by Marcin
 */
public class DataReader {

    public static Map<String, String> readData(String filePath) throws ParserConfigurationException, IOException, SAXException {
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
