package Bai_tap_thuc_hanh_mau;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;

public class Ex5 extends JFrame implements ActionListener {

    JTextArea ta = new JTextArea();
    JButton open = new JButton("Open..");

    JFileChooser chooser;

    FileReader fr;
    BufferedReader br;

    public Ex5() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container cont = this.getContentPane();

        open.addActionListener(this);
        cont.add(ta);
        cont.add(open,"South");
        this.setSize(400,400);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));

        chooser.setDialogTitle("Open file");
        //Nếu đã chọn tệp tin
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String filename = chooser.getSelectedFile().getAbsolutePath();
            readFile(filename);
        }
    }

    public void readFile(String fileName) {
        try{
            ta.setText("");
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            String s;
            while ((s = br.readLine()) != null)
                ta.append(s + "\n");
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Ex5();
    }
}
