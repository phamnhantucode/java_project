import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

public class Exercise_1 {
    public static void main(String[] args) {
        ViewEx1 viewEx1 = new ViewEx1();
    }
}

class ViewEx1 extends JFrame {
    private JComboBox jComboBox;
    public ViewEx1() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(200, 100);
        setLayout(null);
        jComboBox = new JComboBox(ControllerEx1.getTableNames());
        jComboBox.setBounds(50,20,100, 30);
        jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ControllerEx1().openTable(e);
            }
        });
        add(jComboBox);
        setVisible(true);
    }
}

class TableEx1 extends JFrame {
    JScrollPane jScrollPane;
    JTable jTable;
    String[] collumns;
    Vector<String[]> data;
    DefaultTableModel defaultTableModel;
    public TableEx1(String[] collumns, Vector data) {
        this.collumns = collumns;
        this.data = data;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 300);
        defaultTableModel = new DefaultTableModel();
        defaultTableModel.setColumnIdentifiers(collumns);
        Enumeration e = data.elements();
        while (e.hasMoreElements()) {
            defaultTableModel.addRow((String[]) e.nextElement());
        }
        jTable = new JTable(defaultTableModel);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
        TableModel tableModel = jTable.getModel();

        for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++)
        {
            jTable.getColumnModel().getColumn(columnIndex).setCellRenderer(centerRenderer);
        }
        jTable.getFillsViewportHeight();
        jScrollPane = new JScrollPane(jTable);
        add(jScrollPane);
        setVisible(true);
    }

}

class ControllerEx1 {
    private static ModelEx1 modelEx1 = new ModelEx1();
    private static Vector<String[]> data;
    public static String[] getTableNames() {
        Document document = modelEx1.getData();
        NodeList nodeList = document.getElementsByTagName("table");
        String tableNames = "";
        for (int i = 0; i < nodeList.getLength(); i++) {
            tableNames+= nodeList.item(i).getAttributes().item(0).getTextContent() + "//";
        }
        return tableNames.split("//");
    }

    public void openTable(ActionEvent e) {
        JComboBox jComboBox = (JComboBox) e.getSource();

            String columns[] = ControllerEx1.getColumns((String) jComboBox.getItemAt(jComboBox.getSelectedIndex()));
            new TableEx1(columns, data);

    }

    private static String[] getColumns(String table) {
//        Vector<Vector> v = new Vector<Vector>();
        Vector<String[]> vector = new Vector();
        List<String[]> listA= new ArrayList<>();
        Document document = modelEx1.getData();
        Node node = null;
        NodeList nodeList = document.getElementsByTagName("table");
        for (int j = 0; j<nodeList.getLength(); j++) {
            if (nodeList.item(j).getAttributes().item(0).getTextContent().equals(table)) {
                node = nodeList.item(j);
                break;
            }
        }

        NodeList list = node.getChildNodes();
        String columns = "";
        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeName().equals("#text")) continue;
            else {
                columns += list.item(i).getNodeName() + "//";
                Element element = (Element) list.item(i);
                String[] data = element.getTextContent().split(", ");
                listA.add(data);
            }
        }
        for (int i = 0; i < listA.get(0).length; i++) {
            String data = "";
            for (int j = 0; j < listA.size(); j++) {
                data += listA.get(j)[i] + "//";
            }
            vector.add(data.split("//"));
        }
        data = vector;
        return columns.split("//");
    }
}

class ModelEx1 {

    public Document getData() {

        try {
            File inputFile = new File("src/student.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
