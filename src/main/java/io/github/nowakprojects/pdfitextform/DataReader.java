package io.github.nowakprojects.pdfitextform;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;

/**
 * Created by Marcin
 */
class DataReader {

    static Map<String, String> readData(String filePath) throws ParserConfigurationException, IOException, SAXException {
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

    static void readFillSchema(String filePath) throws ParserConfigurationException, IOException, SAXException {
        Map<Integer, Set<Object>> ll;
        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        FileInputStream input = new FileInputStream(filePath);
        Document doc = builder.parse(input);
        doc.getDocumentElement().normalize();

        Element element = doc.getDocumentElement();
        NodeList pages = element.getChildNodes();

        for (int pageIndex = 0; pageIndex < pages.getLength(); pageIndex++) {
            Node page = pages.item(pageIndex);
            NodeList pageElements = page.getChildNodes();

            for (int elementOnPageIndex = 0; elementOnPageIndex < pageElements.getLength(); elementOnPageIndex++) {
                Node pageElement = pageElements.item(elementOnPageIndex);
                if (!pageElement.getNodeName().startsWith("#")) {
                    println(pageElement.getNodeName());
                }
            }
        }

    }

    private static Map<String, String> getAttributesMap(Node baseNode) {

        NamedNodeMap attrs = baseNode.getAttributes();
        Map<String, String> attributesMap = new HashMap<>();
        for (int attrIndex = 0; attrIndex<attrs.getLength(); attrIndex++) {
            Node node = attrs.item(attrIndex);
            attributesMap.put(node.getNodeName(), node.getNodeValue());
        }
        return attributesMap;
    }

    public static void println(String str) {
        System.out.println(str);
    }

}
