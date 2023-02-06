package Bai_tap_tu_giai;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Ex5 extends JFrame implements ActionListener {

    private MenuBar bar;
    private Menu file, edit;
    private MenuItem neww, saveAs, save, open, cut, copy, paste;

    private JTextArea ta;

    private JFileChooser chooser;

    FileReader fr = null;
    FileWriter fw = null;
    BufferedReader br = null;
    BufferedWriter bw = null;

    private String path = null, temp;

    public Ex5() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ta = new JTextArea(20,40);
//        ta.setSize(400,400);

        bar = new MenuBar();

        //menu file
        file = new Menu("File");
        file.addActionListener(this);
        neww = new MenuItem("New");
        file.add(neww);
        neww.addActionListener(this);

        file.addSeparator();
        saveAs = new MenuItem("Save As");
        file.add(saveAs);
        saveAs.addActionListener(this);

        file.addSeparator();
        save = new MenuItem("Save");
        file.add(save);
        save.addActionListener(this);

        file.addSeparator();
        open = new MenuItem("Open");
        file.add(open);
        open.addActionListener(this);

        //menu edit
        edit = new Menu("Edit");
        edit.addActionListener(this);
        cut = new MenuItem("Cut");
        edit.add(cut);
        cut.addActionListener(this);

        edit.addSeparator();
        copy = new MenuItem("Copy");
        edit.add(copy);
        copy.addActionListener(this);

        edit.addSeparator();
        paste = new MenuItem("Paste");
        edit.add(paste);
        paste.addActionListener(this);


        bar.add(file);
        bar.add(edit);
        add(ta);
        setMenuBar(bar);
        setSize(400,300);
        pack();
        setVisible(true);

        setResizable(false);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(neww)) {
            ta.setText("");
        }
        if (e.getSource().equals(open)) {
            openFile();
        }
        if (e.getSource().equals(save)) {
            saveFile();
        }

        if (e.getSource().equals(saveAs)) {
            saveAsFile();
        }

        if (e.getSource().equals(copy)) {
            copyFile();
        }
        if (e.getSource().equals(cut)) {
            cutFile();
        }
        if (e.getSource().equals(paste)) {
            pasteFile();
        }
    }

    private void pasteFile() {
        ta.append(temp);
    }

    private void cutFile() {
        copyFile();
        ta.setText("");
    }

    private void copyFile() {
        temp = ta.getText();
    }

    private void saveAsFile() {
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Open");
        String fileName = "";
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            path = chooser.getSelectedFile().getAbsolutePath();
        }
        try {
            fw = new FileWriter(path);
            bw = new BufferedWriter(fw);
            bw.write(ta.getText());
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile() {

            if (path == null) JOptionPane.showMessageDialog(this, "You have not open file!");
            else {
                try {
                    fw = new FileWriter(path);
                    bw = new BufferedWriter(fw);
                    bw.write(ta.getText());
                    bw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                    try {
                        fw.close();
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

    }

    private void openFile() {
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Open");
        String fileName = "";
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            path = chooser.getSelectedFile().getAbsolutePath();
        }
        try {
            ta.setText("");
            fr = new FileReader(path);
            br = new BufferedReader(fr);
            String tmp;
            while ((tmp = br.readLine())!= null)
                    ta.append(tmp + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Ex5();
    }
}
