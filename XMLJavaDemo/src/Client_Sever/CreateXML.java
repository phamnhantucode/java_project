
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CreateXML {
    public static void create(List list, File f) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.newDocument();
            Element rootElement = document.createElement("class");
            document.appendChild(rootElement);
            for (int i = 0; i < list.size(); i++) {
                String[] data = (String[]) list.get(i);
                Element student = document.createElement("student");
                student.setAttribute("id", data[0]);
                rootElement.appendChild(student);
                //add name into student
                Element name = document.createElement("name");
                name.appendChild(document.createTextNode(data[1]));
                student.appendChild(name);
                //add mark into student
                Element mark = document.createElement("age");
                mark.appendChild(document.createTextNode(data[2]));
                student.appendChild(mark);

                Element height = document.createElement("height");
                height.appendChild(document.createTextNode(data[3]));
                student.appendChild(height);

                Element weight = document.createElement("weight");
                weight.appendChild(document.createTextNode(data[4]));
                student.appendChild(weight);
            }
            //ghi noi dung vao file XML
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(document);
            StreamResult streamResult = new StreamResult(f);
            transformer.transform(source, streamResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List readXML(File f) {
        List<String[]> l = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(f);
            doc.getDocumentElement().normalize();

            // đọc tất cả các phần tử có tên thẻ là "student"
            NodeList nodeListStudent = doc.getElementsByTagName("student");

            for (int i = 0; i < nodeListStudent.getLength(); i++) {
                Node node = nodeListStudent.item(i);
                if (node.getNodeType()==Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String[] data = {element.getAttribute("id"), element.getElementsByTagName("name").item(0).getTextContent(), element.getElementsByTagName("age").item(0).getTextContent(),
                            element.getElementsByTagName("height").item(0).getTextContent(), element.getElementsByTagName("weight").item(0).getTextContent()};
                    l.add(data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }
}
