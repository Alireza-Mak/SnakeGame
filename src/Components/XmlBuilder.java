package Components;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XmlBuilder {
    private final String url = System.getProperty("user.dir") + "\\src\\records.xml";
    private Document document;

    /**
     * Parses the XML file and returns a list of Record objects.
     *
     * @return List of records with name and score.
     */
    public ArrayList<Record> getRecords() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        ArrayList<Record> records = new ArrayList<>();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(url);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("record");
            for (int i = 0; i < nList.getLength(); i++) {
                Element record = (Element) nList.item(i);
                int score = Integer.parseInt(record.getElementsByTagName("score").item(0).getTextContent());
                String name = record.getElementsByTagName("name").item(0).getTextContent();
                records.add(new Record(name, score));
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println(e.getMessage());
        }
        return records;
    }

    private void prettierXml(Transformer transformer) {
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
    }

    public static void trimWhitespace(Node node) {
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); ++i) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.TEXT_NODE) {
                child.setTextContent(child.getTextContent().trim());
            }
            trimWhitespace(child);
        }
    }

    public void addNewRecord(String name, String score) {
        try {
            //Load or Create xml file
            loadOrCreateDocument();

            //create New Record
            int id = createNewID();
            Element record = document.createElement("record");
            record.setAttribute("id", String.valueOf(id));

            Element nameEl = document.createElement("name");
            nameEl.appendChild(document.createTextNode(name));

            Element score_el = document.createElement("score");
            score_el.appendChild(document.createTextNode(score));

            record.appendChild(nameEl);
            record.appendChild(score_el);

            // Append to <records>
            document.getDocumentElement().appendChild(record);

            // Save updated document
            saveDocument();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void loadOrCreateDocument() throws Exception {
        File file = new File(url);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        if (file.exists()) {
            document = builder.parse(file);
            document.getDocumentElement().normalize();
        } else {
            document = builder.newDocument();
            Element root = document.createElement("records");
            document.appendChild(root);
        }
        trimWhitespace(document);
    }

    private void saveDocument() throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        prettierXml(transformer);
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(url));
        transformer.transform(source, result);
    }

    private int createNewID() {
        int id;
        NodeList recordNodes = document.getElementsByTagName("record");
        int length = recordNodes.getLength();
        if (length == 0) {
            id = 1;
        } else {
            Element lastRecord = (Element) recordNodes.item(length - 1);
            id = Integer.parseInt(lastRecord.getAttribute("id")) + 1;
        }
        return id;
    }
}
