package Bai_tap_thuc_hanh_mau;

import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

class Student implements Comparable {

    private String name;
    private String id;
    private float aver;

    public Student() {
        name = new String("");
        id = new String("");
        aver = 0;
    }

    public Student(String n, String i, float a) {
        name = n;
        id = i;
        aver = a;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public float getAver() {
        return aver;
    }

    @Override
    public int compareTo(Object o) {
        Student otherRect = (Student) o;
        return (int) (this.aver-otherRect.aver);
    }
}

public class Ex10 extends  Frame implements  ActionListener {
    private MenuBar bar;
    private Menu action;
    private MenuItem input, search, sortView, exit;

    FileWriter f;
    PrintWriter pr;

    Vector list = new Vector();

    public Ex10(String s) {
        super(s);
        loadData();

        //tao he thong menu
        bar = new MenuBar();
        setMenuBar(bar);
        action = new Menu("Action");
        bar.add(action);
        action.addActionListener(this);
        input = new MenuItem("Input");
        action.add(input);
        input.addActionListener(this);

        action.addSeparator();
        sortView = new MenuItem("Sort and View");
        action.add(sortView);
        sortView.addActionListener(this);

        action.addSeparator();
        search = new MenuItem("Search");
        action.add(search);
        search.addActionListener(this);

        action.addSeparator();
        exit = new MenuItem("Exit");
        action.add(exit);
        exit.addActionListener(this);

        //Thiet lap kich thuoc va hien thi cho cua so chinh
        setSize(400,300);
        setVisible(true);
        setResizable(false);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource()==exit) {
            saveFile();
            System.exit(0);
        }
        if (ae.getSource()==input) {
            InputForm f = new InputForm(this,"Input information");
        }
        if (ae.getSource() == sortView) {
            sort();
        }
        if (ae.getSource() == search) {
            new SearchForm("Search", list);
        }
    }

    public void updateList(String n, String i, float a) {
        Student st = new Student(n,i,a);
        list.add(st);
    }

    //Mo tep tin de ghi moi khi chuong  trinh ket thuc
    public void saveFile() {
        try {
            f = new FileWriter("./src/data.txt", false);
            pr = new PrintWriter(f);

            Enumeration vEnum = list.elements();
            while (vEnum.hasMoreElements()) {
                Student st = (Student) vEnum.nextElement();
                String toString = st.getName() + "&" + st.getId() + "&" + st.getAver();
                pr.println(toString);
                pr.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        try {
            FileReader fr = new FileReader("./src/data.txt");
            BufferedReader br = new BufferedReader(fr);
            String s;
            while ((s = br.readLine())!=null) {
                String[] data = s.split("&");
                Student st = new Student(data[0], data[1], Float.parseFloat(data[2]));
                list.add(st);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sort() {
        Student[] sts = new Student[list.size()];
        int index = 0;
        Enumeration vEnum = list.elements();
        while (vEnum.hasMoreElements()) {
            sts[index] = (Student) vEnum.nextElement();
            index++;
        }
        Arrays.sort(sts);
        TextArea ta = new TextArea("Name \t id \t aver\n");
        for (index = 0; index <sts.length; index++) {
            ta.append(sts[index].getName() + "\t" + sts[index].getId() + "\t" + sts[index].getAver() + "\n");
        }
        this.add(ta);
        this.validate();
    }

    public static void main(String[] args) {
        new Ex10("Student Management");
    }
}

class InputForm extends Frame implements ActionListener{
    private Label nameLb;
    private TextField name;
    private Label idlb;
    private TextField id;
    private Label averlb;
    private TextField aver;
    Button save, newInput, cancel;
    Ex10 mainFrame;

    public InputForm(Ex10 a, String s) {
        super(s);
        mainFrame = a;
        setLayout(new BorderLayout());
        Panel p1 = new Panel();
        p1.setLayout(new GridLayout(3,2));

        nameLb = new Label("Name");
        name = new TextField(20);
        p1.add(nameLb);
        p1.add(name);
        idlb = new Label("Id");
        id = new TextField(20);
        p1.add(idlb);
        p1.add(id);
        averlb = new Label("Aver");
        aver = new TextField(20);
        p1.add(averlb);
        p1.add(aver);

        this.add(p1, "North");

        Panel p2 = new Panel();
        save = new Button("Save");
        newInput = new Button("New");
        cancel = new Button("Cancel");
        save.addActionListener(this);
        newInput.addActionListener(this);
        cancel.addActionListener(this);
        p2.add(save);
        p2.add(newInput);
        p2.add(cancel);

        this.add(p2,"South");
        setSize(300,300);
        setVisible(true);
        setResizable(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save) {
            mainFrame.updateList(name.getText(), id.getText(), Float.parseFloat(aver.getText()));
        }

        if (e.getSource() == newInput) {
            reset();
        }
        if (e.getSource() == cancel) {
            this.dispose();
        }
    }

    public void reset() {
        name.setText("");
        id.setText("");
        aver.setText("0");
    }
}

class SearchForm extends Frame implements ActionListener {
    private Label nameLb;
    private TextField name;
    private TextArea ta;
    private Button search , cancel;
    private Vector list;

    public SearchForm(String s, Vector v) {
        super(s);
        list = v;

        setLayout(new BorderLayout());
        Panel p1 = new Panel();
        p1.setLayout(new GridLayout(3, 2));
        nameLb = new Label("Name");
        name = new TextField(20);
        p1.add(nameLb);
        p1.add(name);
        this.add(p1,"North");

        ta = new TextArea("Name \t Id \t Aver \n");
        this.add(ta,"Center");

        Panel p2 = new Panel();
        search = new Button("Search");
        cancel = new Button("Cancel");
        search.addActionListener(this);
        cancel.addActionListener(this);
        p2.add(search);
        p2.add(cancel);
        this.add(p2, "South");
        setSize(300, 300);
        setVisible(true);
        setResizable(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == search) {
            search(name.getText());
        }
        if (e.getSource() == cancel) {
            this.dispose();
        }
    }

    public void search(String s) {
        Enumeration vNum = list.elements();

        while (vNum.hasMoreElements()) {
            Student st = (Student) vNum.nextElement();
            if (st.getName().indexOf(s) != -1) {
                ta.append(st.getName()+"\t" + st.getId() + "\t" + st.getAver() + "\n");
            }
        }
        this.validate();
    }
}