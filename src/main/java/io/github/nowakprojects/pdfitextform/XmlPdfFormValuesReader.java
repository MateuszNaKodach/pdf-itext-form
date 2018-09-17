package io.github.nowakprojects.pdfitextform;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
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
        final Map<String, String> result = new HashMap<>();
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document document = docBuilder.parse(new File(filePath));
        Node node = document.getDocumentElement();
        addNodeWithChildrenToMap(node, result, null);
        return result;
    }

    private void addNodeWithChildrenToMap(Node node, Map<String, String> map, String nodePrefix) {
        final Node firstChild = node.getFirstChild();
        if (firstChild != null && firstChild.getNodeValue() != null) {
            map.put(nodePrefix == null ? "" : (nodePrefix + "." + node.getNodeName()), firstChild.getNodeValue());
        }
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                addNodeWithChildrenToMap(currentNode, map, nodePrefix == null ? node.getNodeName() : (nodePrefix + "." + node.getNodeName()));
            }
        }
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

    boolean containsValueForTag(String tag) {
        return valuesByTag.containsKey(tag);
    }


}