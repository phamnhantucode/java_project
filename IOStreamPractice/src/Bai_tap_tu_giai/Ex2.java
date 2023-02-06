package Bai_tap_tu_giai;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class Ex2 extends JFrame implements ActionListener {

    private JTextField nameTf = new JTextField(10);
    private JTextField birthTf = new JTextField(10);
    private JTextField emailTf = new JTextField(10);
    private JTextField phoneTf = new JTextField(10);

    private JLabel nameLb = new JLabel("Name:");
    private JLabel birthLb = new JLabel("Birthday:");
    private JLabel emailLb = new JLabel("Email:");
    private JLabel phoneLb = new JLabel("Phone:");

    private JButton saveBtn = new JButton("Save");
    private JButton cancelBtn = new JButton("Cancel");
    private JButton clearBtn = new JButton("Clear");

    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();

    public Ex2() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 170);
        this.setLayout(new FlowLayout());
        panel1.setLayout(new GridLayout(4,2));
        panel1.add(nameLb);panel1.add(nameTf);
        panel1.add(birthLb);panel1.add(birthTf);
        panel1.add(emailLb);panel1.add(emailTf);
        panel1.add(phoneLb);panel1.add(phoneTf);
        this.add(panel1);
        readFile();
        panel2.add(saveBtn); panel2.add(cancelBtn); panel2.add(clearBtn);
        this.add(panel2);
        cancelBtn.addActionListener(this);
        saveBtn.addActionListener(this);
        clearBtn.addActionListener(this);

        this.setVisible(true);
    }

    private void readFile() {
        FileReader f = null;
        BufferedReader b = null;
        try {
            f = new FileReader("text.txt");
            b = new BufferedReader(f);
            nameTf.setText(b.readLine());
            birthTf.setText(b.readLine());
            emailTf.setText(b.readLine());
            phoneTf.setText(b.readLine());
            b.close();
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelBtn) {
            System.exit(0);
        }
        else if (e.getSource() == saveBtn){
            writeFile();
        }
        else {
            nameTf.setText("");
            birthTf.setText("");
            emailTf.setText("");
            phoneTf.setText("");
        }
    }

    public void writeFile() {
        FileWriter f = null;
        BufferedWriter b = null;
        String s = "";
        try {
            f = new FileWriter("text.txt");
            b = new BufferedWriter(f);
            s += nameTf.getText() + "\n" + birthTf.getText() + "\n" + emailTf.getText() + "\n" + phoneTf.getText();
            b.write(s);
            b.flush();
            b.close();
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        new Ex2();
    }
}
