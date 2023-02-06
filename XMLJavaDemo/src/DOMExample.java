import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DOMExample {
    public static void main(String[] args) {
        List<Student> listStudents = DOMExample.readListStudents();
        // hiển thị các đối tượng student ra màn hình
        for (Student student : listStudents) {
            System.out.println(student.toString());
        }
    }

    public static List<Student> readListStudents() {
        List<Student> listStudents = new ArrayList<>();
        Student student = null;

        try {
            // đọc file input.xml
            File inputFile = new File("src/student.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            //in phan tu goc ra man hinh
            System.out.println("Phan tu goc: " + doc.getDocumentElement().getNodeName());

            // đọc tất cả các phần tử có tên thẻ là "student"
            NodeList nodeListStudent = doc.getElementsByTagName("student");

            // duyệt các phần tử student
            for (int i = 0; i < nodeListStudent.getLength(); i++) {

                //tao doi tuong student
                student = new Student();
                //doc cacs thuoc tinh cua student
                Node nNode = nodeListStudent.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nNode;


                    student.setId(element.getAttribute("id"));
                    student.setFirstName(element.getElementsByTagName("firstname").item(0).getTextContent());
                    student.setLastName(element.getElementsByTagName("lastname").item(0).getTextContent());
                    student.setMarks(element.getElementsByTagName("marks").item(0).getTextContent());
                }
                listStudents.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listStudents;
    }
}
