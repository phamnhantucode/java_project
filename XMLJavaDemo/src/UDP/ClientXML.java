package UDP;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class ClientXML extends JFrame {
    public static void main(String[] args) {
        new ClientXML();

    }

    private void connectBtn(ActionEvent e) {
        portServer = Integer.parseInt(portTf.getText());
        System.out.println(portServer);
    }


    private void LoadBtn(ActionEvent e) {
        // TODO add your code here
        try {
            byte[] receive = new byte[100000];


            String s = "load";
            DatagramPacket sendPacket = new DatagramPacket(s.getBytes(StandardCharsets.UTF_8), s.getBytes(StandardCharsets.UTF_8).length, inetAddress, portServer);
            clientSocket.send(sendPacket);
            System.out.println("Send" + s.getBytes().toString() + " " + s.getBytes().length);
            DatagramPacket receivePacket = new DatagramPacket(receive, receive.length);
            clientSocket.receive(receivePacket);


            System.out.println("Receiver: " + receivePacket.getData().toString());
            File f = new File("src//UDP//student_client.xml");


            if (f.exists()) {
                f.delete();
                f.createNewFile();
            } else f.createNewFile();
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(f));
            dataOutputStream.write(receive, receivePacket.getOffset(), receivePacket.getLength());
            dataOutputStream.flush();
            dataOutputStream.close();
            table.removeAll();
            DefaultTableModel df = new DefaultTableModel();
            List<String[]> l = CreateXML.readXML(f);
            df.setColumnIdentifiers(collumns);
            for (int i = 0; i < l.size(); i++) {
                df.addRow(l.get(i));
            }
            table.setModel(df);
            table.repaint();
            repaint();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void addBtn(ActionEvent e) {
        // TODO add your code here
        byte[] receive;

        try {
            //ghi XML
            File f = new File("src//UDP//student_client.xml");
            if (f.exists()) {
                f.delete();
                f.createNewFile();
            } else f.createNewFile();
            List<String[]> l = new ArrayList<>();
            l.add(new String[]{idTf.getText(), nameTf.getText(), markTf.getText()});
            CreateXML.create(l, f);

            //truyen du lieu
            BufferedReader bf = new BufferedReader(new FileReader(f));

            String s = "add///" + bf.readLine();
            byte[] send = s.getBytes(StandardCharsets.UTF_8);
            DatagramPacket sendPacket = new DatagramPacket(send, send.length, inetAddress, 9876);
            clientSocket.send(sendPacket);
            Thread.sleep(300);
            LoadBtn.doClick();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void deleteBtn(ActionEvent e) {
        // TODO add your code here
        try {
            String s = "delete///"+ idTf.getText();
            byte[] send = s.getBytes(StandardCharsets.UTF_8);
            DatagramPacket sendPacket = new DatagramPacket(send, send.length, inetAddress, 9876);
            clientSocket.send(sendPacket);
            Thread.sleep(300);
            LoadBtn.doClick();
        }catch (Exception e1) {
            e1.printStackTrace();
        }
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        portLb = new JLabel();
        portTf = new JTextField();
        connectBtn = new JButton();
        separator1 = new JSeparator();
        scrollPane1 = new JScrollPane();
        table = new JTable();
        idLb = new JLabel();
        idTf = new JTextField();
        nameLb = new JLabel();
        nameTf = new JTextField();
        markLb = new JLabel();
        markTf = new JTextField();
        addbtn = new JButton();
        deleteBtn = new JButton();
        LoadBtn = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- portLb ----
        portLb.setText("Port");
        contentPane.add(portLb);
        portLb.setBounds(new Rectangle(new Point(15, 15), portLb.getPreferredSize()));
        contentPane.add(portTf);
        portTf.setBounds(50, 10, 55, portTf.getPreferredSize().height);

        //---- connectBtn ----
        connectBtn.setText("Connect");
        connectBtn.addActionListener(e -> connectBtn(e));
        contentPane.add(connectBtn);
        connectBtn.setBounds(125, 10, 105, connectBtn.getPreferredSize().height);
        contentPane.add(separator1);
        separator1.setBounds(10, 40, 385, separator1.getPreferredSize().height);

        //======== scrollPane1 ========
        {

            //---- table ----
            table.setAutoCreateRowSorter(true);
            table.setModel(new DefaultTableModel(
                new Object[][] {
                    {null, "", null},
                    {null, null, null},
                },
                new String[] {
                    null, null, null
                }
            ) {
                boolean[] columnEditable = new boolean[] {
                    false, false, false
                };
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return columnEditable[columnIndex];
                }
            });
            {
                TableColumnModel cm = table.getColumnModel();
                cm.getColumn(0).setResizable(false);
                cm.getColumn(1).setResizable(false);
                cm.getColumn(2).setResizable(false);
            }
            table.setFillsViewportHeight(true);
            scrollPane1.setViewportView(table);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(0, 135, 415, 320);

        //---- idLb ----
        idLb.setText("ID");
        contentPane.add(idLb);
        idLb.setBounds(new Rectangle(new Point(20, 50), idLb.getPreferredSize()));
        contentPane.add(idTf);
        idTf.setBounds(50, 45, 55, idTf.getPreferredSize().height);

        //---- nameLb ----
        nameLb.setText("Name");
        contentPane.add(nameLb);
        nameLb.setBounds(new Rectangle(new Point(10, 80), nameLb.getPreferredSize()));
        contentPane.add(nameTf);
        nameTf.setBounds(50, 75, 95, nameTf.getPreferredSize().height);

        //---- markLb ----
        markLb.setText("Mark");
        contentPane.add(markLb);
        markLb.setBounds(new Rectangle(new Point(15, 110), markLb.getPreferredSize()));
        contentPane.add(markTf);
        markTf.setBounds(50, 105, 70, markTf.getPreferredSize().height);

        //---- addbtn ----
        addbtn.setText("Add");
        addbtn.addActionListener(e -> addBtn(e));
        contentPane.add(addbtn);
        addbtn.setBounds(320, 50, 70, addbtn.getPreferredSize().height);

        //---- deleteBtn ----
        deleteBtn.setText("Delete");
        deleteBtn.addActionListener(e -> deleteBtn(e));
        contentPane.add(deleteBtn);
        deleteBtn.setBounds(300, 85, 90, deleteBtn.getPreferredSize().height);

        //---- LoadBtn ----
        LoadBtn.setText("Load");
        LoadBtn.addActionListener(e -> LoadBtn(e));
        contentPane.add(LoadBtn);
        LoadBtn.setBounds(300, 10, 95, 25);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel portLb;
    private JTextField portTf;
    private JButton connectBtn;
    private JSeparator separator1;
    private JScrollPane scrollPane1;
    private JTable table;
    private JLabel idLb;
    private JTextField idTf;
    private JLabel nameLb;
    private JTextField nameTf;
    private JLabel markLb;
    private JTextField markTf;
    private JButton addbtn;
    private JButton deleteBtn;
    private JButton LoadBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables


    private DatagramSocket clientSocket;
    int portServer;
    InetAddress inetAddress;
    String[] collumns = {"ID", "Name", "Mark"};
//    byte[] receive;
//    byte[] send;

    public ClientXML() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setVisible(true);
//        setResizable(false);
        try {
            clientSocket = new DatagramSocket(6789);
            inetAddress = InetAddress.getByName("localhost");
//            receive = new byte[100000];
//            send = new byte[100000];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
