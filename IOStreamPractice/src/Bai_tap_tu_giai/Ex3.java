package Bai_tap_tu_giai;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class Ex3 extends JFrame implements ActionListener {

    private JTextArea ta = null;
    private JButton saveAsBtn = null;
    private JButton openBtn = null;
    private JFileChooser chooser = null;

    private FileWriter f;
    private FileReader f1;

    public Ex3() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);
        ta = new JTextArea();
        ta.setBounds(0, 0, 400, 320);
        this.add(ta);
        saveAsBtn = new JButton("Save As");
        saveAsBtn.addActionListener(this);
        saveAsBtn.setBounds(90, 327, 100, 30);
        openBtn = new JButton("Open");
        openBtn.addActionListener(this);
        openBtn.setBounds(190, 327, 100, 30);
        this.add(saveAsBtn);
        this.add(openBtn);
        this.setLayout(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(saveAsBtn)) {
            chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Save As");

            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                String fileName = chooser.getSelectedFile().getAbsolutePath();
                saveFile(fileName);
            }
        } else {
            chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Open");

            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                String fileName = chooser.getSelectedFile().getAbsolutePath();
                openFile(fileName);
            }
        }
    }

    private void openFile(String fileName) {
        BufferedReader b;
        try {
            f1 = new FileReader(fileName);
            b = new BufferedReader(f1);
            String s;
            while ((s = b.readLine()) != null) {
                ta.append(s + "\n");
            }
            b.close();
            f1.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveFile(String fileName) {
        try {
            String content = ta.getText();
            f = new FileWriter(fileName);
            f.write(content);
            f.flush();
            f.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Ex3();
    }
}