
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientXML extends JFrame {
    public final static String IP = "127.0.0.1";
    public final static int PORT = 9888;

    Socket socket;
    PrintWriter out;
    BufferedReader in;

    JButton connect = new JButton("Connect");
    //them student
    JLabel student = new JLabel("Student");
    JLabel idLb = new JLabel("ID:");
    JLabel nameLb = new JLabel("Name");
    JLabel ageLb = new JLabel("Age");
    JLabel heightLb = new JLabel("Height");
    JLabel weightLb = new JLabel("Weight");
    JTextField idTf = new JTextField();
    JTextField nameTf = new JTextField();
    JTextField ageTf = new JTextField();
    JTextField heightTf = new JTextField();
    JTextField weightTf = new JTextField();
    JButton add = new JButton("Add");
    JButton delete = new JButton("Delete");

    // table to display data
    JTable table = new JTable();
    JScrollPane scrollPane;
    String[] collumns = {"ID", "Name", "Age", "Height", "Weight"};
    JButton refresh = new JButton("Load Table");

    public ClientXML() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(null);
        add(connect);
        connect.setBounds(10, 10, 100, 25);
        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectServer();
            }
        });
        //add Frame;
        add(student);
        student.setBounds(200,10,100,40);
        student.setFont(new Font("Consolas", Font.BOLD, 19));

        add(idLb);
        idLb.setBounds(10,60,50,20);
        add(idTf);
        idTf.setBounds(100, 60, 100, 20);

        add(heightLb);
        heightLb.setBounds(270,60,50,20);
        add(heightTf);
        heightTf.setBounds(320, 60, 100, 20);

        add(weightLb);
        weightLb.setBounds(270,90,50,20);
        add(weightTf);
        weightTf.setBounds(320, 90, 100, 20);

        add(nameLb);
        nameLb.setBounds(10,90,50,20);
        add(nameTf);
        nameTf.setBounds(100, 90, 150, 20);

        add(ageLb);
        ageLb.setBounds(10,120,50,20);
        add(ageTf);
        ageTf.setBounds(100, 120, 100, 20);

        add(add);
        add.setBounds(10, 150, 80, 20);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addData();
            }
        });

        table.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10,180,460,250);
        add(scrollPane);
        refresh.setBounds(10, 435, 140,25);
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        refreshTable();
                    }
                };
                t.start();
            }
        });
        add(refresh);
        setVisible(true);
    }

    private void refreshTable() {
        try {
            boolean t = true;
            out.println("getData");
            out.flush();
            while (t) {
                String line = in.readLine();
                if (!line.isEmpty() && line!=null) {
                    out.println("OK");
                    out.flush();
                    File f = new File("src//Client_Sever//student_client.xml");
                    if (f.exists()) {
                        f.delete();
                        try {
                            f.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        try {
                            f.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(f));
                    dataOutputStream.write(line.getBytes(), 0, line.length());
                    dataOutputStream.flush();
                    List<String[]> list = CreateXML.readXML(f);
                    addTableData(list);
                    t = false;
                }

            }
        } catch (IOException e) {
        e.printStackTrace();
    }
    }

    private void addTableData(List<String[]> list) {
        table.removeAll();

        DefaultTableModel defaultTableModel = new DefaultTableModel();
        defaultTableModel.setColumnIdentifiers(collumns);
        for (int i = 0; i < list.size(); i++)   {
            defaultTableModel.addRow(list.get(i));
        }
        table.setModel(defaultTableModel);
    }

    private void addData() {

//        String data ="add"+ "//" + idTf.getText() + "//" + nameTf.getText() + "//" + markTf.getText();
//        out.println(data);
//        out.flush();
        String[] data = {idTf.getText(), nameTf.getText(), ageTf.getText(), heightTf.getText(), weightTf.getText()};
        List<String[]> l = new ArrayList<>();
        l.add(data);
        File f = new File("student_client.xml");
        if (f.exists()) {
            f.delete();
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        CreateXML.create(l, f);

//        DataInputStream dataInputStream = new DataInputStream(f);
        try {
//            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(f));
            byte[] file = dataInputStream.readAllBytes();
            String decoded = new String(file, "UTF-8");
//            decoded.replaceAll("[" + System.lineSeparator() + "]+", "baode");
            out.println("add" + "//" + decoded);
            out.flush();
//            dataOutputStream.write(file, 0, file.length);
//            dataOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connectServer() {
        try {
            socket = new Socket(IP, PORT);
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            if (socket == null)
                JOptionPane.showMessageDialog(null, "Cannot connect to server");
            else JOptionPane.showMessageDialog(null, "Connect succesfully");
            refresh.doClick();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ClientXML();
    }
}
