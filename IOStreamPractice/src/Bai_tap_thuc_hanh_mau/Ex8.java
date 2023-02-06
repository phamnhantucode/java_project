package Bai_tap_thuc_hanh_mau;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;

public class Ex8 extends JFrame implements ActionListener {

    private JTextArea ta = null;
    private JButton saveAsBtn = null;
    private JFileChooser chooser = null;

    private FileWriter f;

    public Ex8() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);
        ta = new JTextArea();
        ta.setBounds(0, 0, 400, 320);
        this.add(ta);
        saveAsBtn = new JButton("Save As");
        saveAsBtn.addActionListener(this);
        saveAsBtn.setBounds(150, 327, 100, 30);
        this.add(saveAsBtn);
        this.setLayout(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Save As");

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String fileName = chooser.getSelectedFile().getAbsolutePath();
            saveFile(fileName);
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
        new Ex8();
    }
}
