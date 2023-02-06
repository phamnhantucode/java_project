package Bai_tap_tu_giai;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Ex6 extends JFrame implements ActionListener {
    //component giao dien menu
    private JButton load;
    private JButton add = null;
    private JButton delete = null;
    private JButton sortBySalaryAsc;
    private JButton sortBySalaryDesc;
    private JButton filter_personnel_with_birthdays_month;
    private JButton filter_employee_by_department;
    private JButton search;
    private JTable table = null;
    private JScrollPane scrollPane = null;
    private JRadioButton director = null;
    private  JRadioButton manager = null;
    private JRadioButton employee = null;

    String[] directorCol = {"Name", "Birth", "Coefficients Salary", "Position Coefficient"};
    String[] managerCol = {"Name", "Birth", "Coefficients Salary", "Number of employee"};
    String[] employeeCol = {"Name", "Birth", "Coefficients Salary", "Department"};

    Vector list = new Vector();
    public Ex6(String s) {
        super(s);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        panelMenu();
        createTable();
        loadData();
        setSize(700, 500);
        setVisible(true);
        setResizable(false);
    }

    public void loadData() {
        FileInputStream fr = null;
        ObjectInputStream objectInputStream = null;
        try {
            fr = new FileInputStream("./src/person.dat");
            objectInputStream = new ObjectInputStream(fr);
            list = (Vector) objectInputStream.readObject();
            fr.close();
            objectInputStream.close();
            Enumeration e = list.elements();
            DefaultTableModel f = new DefaultTableModel();
            if (director.isSelected()) f.setColumnIdentifiers(directorCol);
            else if (manager.isSelected()) f.setColumnIdentifiers(managerCol);
            else if (employee.isSelected()) f.setColumnIdentifiers(employeeCol);
            while(e.hasMoreElements()) {
                Object o = e.nextElement();
                Vector v = new Vector();
                if (o instanceof Director) {
                    Director d = (Director) o;
                    v.add(d.getName());
                    v.add(d.getBirth());
                    v.add(d.getCoefficientsSalary());
                    v.add(d.getPositionCoefficient());
                    if (director.isSelected())
                        f.addRow(v);
                }
                if (o instanceof Manager) {
                    Manager d = (Manager) o;
                    v.add(d.getName());
                    v.add(d.getBirth());
                    v.add(d.getCoefficientsSalary());
                    v.add(d.getNumber_of_employee());
                    if (manager.isSelected())
                        f.addRow(v);
                }
                if (o instanceof Employee) {
                    Employee d = (Employee) o;
                    v.add(d.getName());
                    v.add(d.getBirth());
                    v.add(d.getCoefficientsSalary());
                    v.add(d.getDepartment());
                    if (employee.isSelected())
                        f.addRow(v);
                }
            }
            table.setModel(f);
//            validate();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void panelMenu() {
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(null);
        JTextField tf = new JTextField();

        load = new JButton("Refresh");
        load.setBounds(10,90,80,30);
        load.setFocusable(false);
        load.addActionListener(this);
        panelMenu.add(load);
        add = new JButton("Add");
        add.setBounds(10,10,80,30);
        add.setFocusable(false);
        add.addActionListener(this);
        panelMenu.add(add);

        delete = new JButton("Delete");
        delete.setBounds(10,50,80,30);
        delete.setFocusable(false);
        delete.addActionListener(this);
        panelMenu.add(delete);
        sortBySalaryAsc = new JButton("Sort by Salary ASC");
        sortBySalaryAsc.setBounds(10,360,150,30);
        sortBySalaryAsc.setFocusable(false);
        sortBySalaryAsc.addActionListener(this);
        this.add(sortBySalaryAsc);
        sortBySalaryDesc = new JButton("Sort by Salary DESC");
        sortBySalaryDesc.setBounds(170,360,150,30);
        sortBySalaryDesc.setFocusable(false);
        sortBySalaryDesc.addActionListener(this);
        this.add(sortBySalaryDesc);
        filter_personnel_with_birthdays_month = new JButton("Filter Personnel With Birthdays Month");
        filter_personnel_with_birthdays_month.setBounds(10,400,250,30);
        filter_personnel_with_birthdays_month.setFocusable(false);
        filter_personnel_with_birthdays_month.addActionListener(this);
        this.add(filter_personnel_with_birthdays_month);
        filter_employee_by_department = new JButton("Filter employee by department");
        filter_employee_by_department.setBounds(270,400,250,30);
        filter_employee_by_department.setFocusable(false);
        filter_employee_by_department.addActionListener(this);
        this.add(filter_employee_by_department);
        search = new JButton("Search");
        search.setBounds(330,360,80,30);
        search.setFocusable(false);
        search.addActionListener(this);
        this.add(search);

        panelMenu.setBounds(10,50,100,135);
        panelMenu.setBackground(Color.WHITE);
        add(panelMenu);
    }

    public void createTable() {
        JPanel radioPanel = new JPanel();
        director = new JRadioButton("Director");
        director.addActionListener(this);
        director.setFocusable(false);
        manager = new JRadioButton("Manger");
        manager.addActionListener(this);
        manager.setFocusable(false);
        employee = new JRadioButton("Employee");
        employee.addActionListener(this);
        employee.setFocusable(false);
        employee.setSelected(true);
        ButtonGroup bg = new ButtonGroup();
        bg.add(director); bg.add(manager); bg.add(employee);
        radioPanel.add(director);
        radioPanel.add(manager);
        radioPanel.add(employee);
        radioPanel.setBounds(150,20,300,30);
        add(radioPanel);

        //table
        table = new JTable();

        DefaultTableModel df = new DefaultTableModel();
        df.setColumnIdentifiers(employeeCol);
        table.setModel(df);
//        table.setEnabled(false);
        table.setDefaultEditor(Object.class, null);
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(table);
        scrollPane.setBounds(120,50,500,300);
        add(scrollPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(director)) {
            loadData();
        }
        if (e.getSource().equals(manager)) {
            loadData();
        }
        if (e.getSource().equals(employee)) {
            loadData();
        }
        if (e.getSource().equals(add)) {
            AddForm af = new AddForm(list, this, "Add");
        }
        if (e.getSource().equals(delete)) {
            deleteAll();
        }
        if (e.getSource().equals(sortBySalaryAsc)) {
            sortBySalary("ASC");
        }
        if (e.getSource().equals(sortBySalaryDesc)) {
            sortBySalary("DESC");
        }
        if (e.getSource().equals(load)) {
            updateList();
        }
        if (e.getSource().equals(filter_personnel_with_birthdays_month)) {
            new BirthMonth(this, "Birthday Month");
        }
        if (e.getSource().equals(filter_employee_by_department)) {
            new FilterDepartment(this, "Filter employee by department");
        }
        if (e.getSource().equals(search)) {
            new SearchForm(this, "Search");
        }

    }

    public void deleteAll() {
//        FileOutputStream f = null;
//        ObjectOutputStream o = null;
//        try {
//            f = new FileOutputStream("./src/person.dat");
//            o = new ObjectOutputStream(f);
//            o.defaultWriteObject();
//            o.flush();
//            o.close();
//            f.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        updateList();
        File f = new File("./src/person.dat");
        f.delete();
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sortBySalary(String f) {
        Collections.sort(list);
        if (f == "DESC") {
            Collections.reverse(list);
        }
        updateList();
    }

    public void updateList() {
        FileOutputStream f = null;
        ObjectOutputStream o = null;
        try {
            f = new FileOutputStream("./src/person.dat");
            o = new ObjectOutputStream(f);
            o.writeObject(list);
            o.flush();
            o.close();
            f.close();
            loadData();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateList(Person p) {
        FileOutputStream f = null;
        ObjectOutputStream o = null;
        try {
            f = new FileOutputStream("./src/person.dat");
            o = new ObjectOutputStream(f);
            list.add(p);
            o.writeObject(list);
            o.flush();
            o.close();
            f.close();
            loadData();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchMonthBirth(String text) {
        DefaultTableModel f = new DefaultTableModel();
        Enumeration e = list.elements();
        Vector v ;
        if (director.isSelected()) f.setColumnIdentifiers(directorCol);
        else if (manager.isSelected()) f.setColumnIdentifiers(managerCol);
        else if (employee.isSelected()) f.setColumnIdentifiers(employeeCol);
        while (e.hasMoreElements()) {
            Object o = e.nextElement();
            v = new Vector();
                    if (o instanceof Director) {
                        Director d = (Director) o;
                        String[] birth = d.getBirth().split("/");
                        if (birth[1].equals(text)) {
                            v.add(d.getName());
                            v.add(d.getBirth());
                            v.add(d.getCoefficientsSalary());
                            v.add(d.getPositionCoefficient());
                            if (director.isSelected())
                                f.addRow(v);
                        }
                    }
                    if (o instanceof Manager) {
                        Manager d = (Manager) o;
                        String[] birth = d.getBirth().split("/");
                        if (birth[1].equals(text)) {
                            v.add(d.getName());
                            v.add(d.getBirth());
                            v.add(d.getCoefficientsSalary());
                            v.add(d.getNumber_of_employee());
                            if (manager.isSelected())
                                f.addRow(v);
                        }
                    }
                    if (o instanceof Employee) {
                        Employee d = (Employee) o;
                        String[] birth = d.getBirth().split("/");
                        if (birth[1].equals(text)) {
                            v.add(d.getName());
                            v.add(d.getBirth());
                            v.add(d.getCoefficientsSalary());
                            v.add(d.getDepartment());
                            if (employee.isSelected())
                                f.addRow(v);
                        }
                    }
                }
                table.setModel(f);
    }

    public void searchDepartment(String text) {
        DefaultTableModel f = new DefaultTableModel();
        f.setColumnIdentifiers(employeeCol);
        Enumeration e = list.elements();
        Vector v ;
        employee.setSelected(true);
        while (e.hasMoreElements()) {
            Object o = e.nextElement();
            if (o instanceof  Employee) {
                Employee d = (Employee) o;
                v = new Vector();
                if (d.getDepartment().equals(text)) {
                    v.add(d.getName());
                    v.add(d.getBirth());
                    v.add(d.getCoefficientsSalary());
                    v.add(d.getDepartment());
                    f.addRow(v);
                }
            }
        }
        table.setModel(f);
    }

    public static void main(String[] args) {

        new Ex6("Bao dep trai");
    }

    public void search(String text) {
        DefaultTableModel f = new DefaultTableModel();
        Enumeration e = list.elements();
        Vector v ;
        if (director.isSelected()) f.setColumnIdentifiers(directorCol);
        else if (manager.isSelected()) f.setColumnIdentifiers(managerCol);
        else if (employee.isSelected()) f.setColumnIdentifiers(employeeCol);
        while (e.hasMoreElements()) {
            Object o = e.nextElement();
            v = new Vector();
            if (o instanceof Director) {
                Director d = (Director) o;
                if (d.getName().equals(text)) {
                    v.add(d.getName());
                    v.add(d.getBirth());
                    v.add(d.getCoefficientsSalary());
                    v.add(d.getPositionCoefficient());
                    if (director.isSelected())
                        f.addRow(v);
                }
            }
            if (o instanceof Manager) {
                Manager d = (Manager) o;
                if (d.getName().equals(text)) {
                    v.add(d.getName());
                    v.add(d.getBirth());
                    v.add(d.getCoefficientsSalary());
                    v.add(d.getNumber_of_employee());
                    if (manager.isSelected())
                        f.addRow(v);
                }
            }
            if (o instanceof Employee) {
                Employee d = (Employee) o;
                if (d.getName().equals(text)) {
                    v.add(d.getName());
                    v.add(d.getBirth());
                    v.add(d.getCoefficientsSalary());
                    v.add(d.getDepartment());
                    if (employee.isSelected())
                        f.addRow(v);
                }
            }
        }
        table.setModel(f);
    }
}

class AddForm extends JFrame implements ActionListener {

    private JTextField nameTf;
    private JTextField birthTf;
    private JTextField coffTf;
    private JTextField positionCoefficientTf;
    private JTextField number_of_employeeTf;
    private JTextField departmentTf;

    private JLabel nameLb;
    private JLabel birthLb;
    private JLabel coffLb;
    private JLabel positionCoefficientLb;
    private JLabel number_of_employeeLb;
    private JLabel departmentLb;

    private JRadioButton director;
    private JRadioButton manager;
    private JRadioButton employee;

    private  JButton save = new JButton("Save");
    private JButton cancel = new JButton("Cancel");

    Ex6 mainFrame;
    Vector list;
    public AddForm(Vector list,Ex6 mainFrame, String add) {

        super(add);
        this.list = list;
        this.mainFrame = mainFrame;
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
            }
        });
        setSize(350, 400);
        setLocation(700,0);
        this.setLayout(null);
        Container container = this.getContentPane();
        JPanel radioPanel = new JPanel();
        director = new JRadioButton("Director");
        director.addActionListener(this);
        director.setFocusable(false);
        manager = new JRadioButton("Manger");
        manager.addActionListener(this);
        manager.setFocusable(false);
        employee = new JRadioButton("Employee");
        employee.addActionListener(this);
        employee.setFocusable(false);
        employee.setSelected(true);
        ButtonGroup bg = new ButtonGroup();
        bg.add(director); bg.add(manager); bg.add(employee);
        radioPanel.add(director);
        radioPanel.add(manager);
        radioPanel.add(employee);
        radioPanel.setBounds(0,0,300,30);
        container.add(radioPanel);

        //form add
        nameLb = new JLabel("Name: ");
        nameLb.setBounds(30,40,100,30);
        nameTf = new JTextField();
        nameTf.setBounds(70,45,200,20);
        container.add(nameLb);
        container.add(nameTf);
        birthLb = new JLabel("Birth: ");
        birthLb.setBounds(30,70,100,30);
        birthTf = new JTextField("dd/MM/yyyy");
        birthTf.setForeground(Color.GRAY);
        birthTf.setBounds(70,75,200,20);
        birthTf.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (birthTf.getText().equals("dd/MM/yyyy")) {
                    birthTf.setText("");
                    birthTf.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (birthTf.getText().isEmpty()) {
                    birthTf.setForeground(Color.GRAY);
                    birthTf.setText("dd/MM/yyyy");
                }
            }
        });
        container.add(birthLb);
        container.add(birthTf);
        coffLb = new JLabel("Coefficients Salary: ");
        coffTf = new JTextField();
        coffLb.setBounds(30,100,200,20);
        coffTf.setBounds(70,125,200,20);
        container.add(coffLb);
        container.add(coffTf);
        positionCoefficientLb = new JLabel("Position Coefficient: ");
        positionCoefficientTf = new JTextField();
        positionCoefficientTf.setEditable(false);
        positionCoefficientLb.setForeground(Color.GRAY);
        positionCoefficientLb.setBounds(30,150,200,20);
        positionCoefficientTf.setBounds(70,175,200,20);
        container.add(positionCoefficientTf);
        container.add(positionCoefficientLb);

        number_of_employeeLb = new JLabel("Number of employee: ");
        number_of_employeeTf = new JTextField();
        number_of_employeeTf.setEditable(false);
        number_of_employeeLb.setForeground(Color.GRAY);
        number_of_employeeLb.setBounds(30,200,200,20);
        number_of_employeeTf.setBounds(70,225,200,20);
        container.add(number_of_employeeLb);
        container.add(number_of_employeeTf);

        departmentLb = new JLabel("Department: ");
        departmentTf = new JTextField();
        departmentLb.setBounds(30,250,200,20);
        departmentTf.setBounds(70,275,200,20);
        container.add(departmentTf);
        container.add(departmentLb);

        //panel button
        JPanel panelButton = new JPanel();
        panelButton.add(save);
        save.addActionListener(this);
        cancel.addActionListener(this);
        panelButton.add(cancel);
        panelButton.setBounds(50,310, 200,40);
        container.add(panelButton);
        setVisible(true);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(director)) {
            positionCoefficientTf.setEditable(true);
            positionCoefficientLb.setForeground(Color.BLACK);
            number_of_employeeTf.setEditable(false);
            number_of_employeeLb.setForeground(Color.GRAY);
            departmentTf.setEditable(false);
            departmentLb.setForeground(Color.GRAY);
            clear();
        }
        if (e.getSource().equals(manager)) {
            positionCoefficientTf.setEditable(false);
            positionCoefficientLb.setForeground(Color.GRAY);
            number_of_employeeTf.setEditable(true);
            number_of_employeeLb.setForeground(Color.BLACK);
            departmentTf.setEditable(false);
            departmentLb.setForeground(Color.GRAY);
            clear();
        }
        if (e.getSource().equals(employee)) {
            positionCoefficientTf.setEditable(false);
            positionCoefficientLb.setForeground(Color.GRAY);
            number_of_employeeTf.setEditable(false);
            number_of_employeeLb.setForeground(Color.GRAY);
            departmentTf.setEditable(true);
            departmentLb.setForeground(Color.BLACK);
            clear();
        }

        if (e.getSource().equals(save)) {
            String regex = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
            if (birthTf.getText().matches(regex)) {
                if (director.isSelected()) {
                    Director d = new Director();
                    d.setName(nameTf.getText());
                    d.setBirth(birthTf.getText());
                    d.setCoefficientsSalary(Integer.parseInt(coffTf.getText()));
                    d.setPositionCoefficient(Integer.parseInt(positionCoefficientTf.getText()));
                    mainFrame.updateList(d);
                }
                if (manager.isSelected()) {
                    Manager d = new Manager();
                    d.setName(nameTf.getText());
                    d.setBirth(birthTf.getText());
                    d.setCoefficientsSalary(Integer.parseInt(coffTf.getText()));
                    d.setNumber_of_employee(Integer.parseInt(number_of_employeeTf.getText()));
                    mainFrame.updateList(d);
                }
                if (employee.isSelected()) {
                    Employee d = new Employee();
                    d.setName(nameTf.getText());
                    d.setBirth(birthTf.getText());
                    d.setCoefficientsSalary(Integer.parseInt(coffTf.getText()));
                    d.setDepartment(departmentTf.getText());
                    mainFrame.updateList(d);
                }
                birthLb.setForeground(Color.BLACK);
                clear();
            } else {
                birthTf.setText("");
                birthLb.setForeground(Color.red);
            }
        }

        if (e.getSource().equals(cancel)) {
            dispose();
        }
    }

    void clear() {
        nameTf.setText("");
        birthTf.setText("");
        coffTf.setText("");
        positionCoefficientTf.setText("");
        number_of_employeeTf.setText("");
        departmentTf.setText("");
    }
}

class Person implements Serializable, Comparable<Person> {
    private String name;
    private String birth;
    private int coefficientsSalary;

    public Person() {
    }

    public Person(String name, String birth, int coefficientsSalary) {
        this.name = name;
        this.birth = birth;
        this.coefficientsSalary = coefficientsSalary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public int getCoefficientsSalary() {
        return coefficientsSalary;
    }

    public void setCoefficientsSalary(int coefficientsSalary) {
        this.coefficientsSalary = coefficientsSalary;
    }

    @Override
    public int compareTo(Person o) {
        return this.getCoefficientsSalary() - o.getCoefficientsSalary();
    }
}

class Director extends Person {
    private int positionCoefficient;

    public Director() {
    }

    public Director(int positionCoefficient) {
        this.positionCoefficient = positionCoefficient;
    }

    public Director(String name, String birth, int coefficientsSalary, int positionCoefficient) {
        super(name, birth, coefficientsSalary);
        this.positionCoefficient = positionCoefficient;
    }

    public int getPositionCoefficient() {
        return positionCoefficient;
    }

    public void setPositionCoefficient(int positionCoefficient) {
        this.positionCoefficient = positionCoefficient;
    }
}

class Manager extends Person {
    private int number_of_employee;

    public Manager(int number_of_employee) {
        this.number_of_employee = number_of_employee;
    }

    public Manager(String name, String birth, int coefficientsSalary, int number_of_employee) {
        super(name, birth, coefficientsSalary);
        this.number_of_employee = number_of_employee;
    }

    public Manager() {
    }

    public int getNumber_of_employee() {
        return number_of_employee;
    }

    public void setNumber_of_employee(int number_of_employee) {
        this.number_of_employee = number_of_employee;
    }
}

class Employee extends Person {
    private String department;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Employee() {
    }

    public Employee(String department) {
        this.department = department;
    }

    public Employee(String name, String birth, int coefficientsSalary, String department) {
        super(name, birth, coefficientsSalary);
        this.department = department;
    }
}

class FilterDepartment extends JFrame implements ActionListener {
    private JButton ok;
    private JButton cancel;
    private  JTextField textField;
    Ex6 mainFrame;

    public FilterDepartment(Ex6 ex6, String s) {
        super(s);
        this.mainFrame = ex6;
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
            }
        });
        setSize(230, 130);
        setLocation(700,0);
        this.setLayout(null);
        Container container = this.getContentPane();
        JLabel lb = new JLabel("Department:");
        lb.setBounds(10,15,100,30);
        container.add(lb);
        textField = new JTextField();
        textField.setBounds(100,20,100,20);
        ok = new JButton("OK");
        ok.setBounds(20,50,80,25);
        ok.addActionListener(this);
        cancel = new JButton("Cancel");
        cancel.setBounds(120,50,80,25);
        cancel.addActionListener(this);
        container.add(textField);
        container.add(ok);
        container.add(cancel);
        setVisible(true);
        setResizable(false);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(ok)) {
            mainFrame.searchDepartment(textField.getText());
        }
        if (e.getSource().equals(cancel)) {
            dispose();
        }
    }
}

class BirthMonth extends JFrame implements ActionListener {
    private JButton ok;
    private JButton cancel;
    private  JTextField textField;
    Ex6 mainFrame;

    public BirthMonth(Ex6 ex6, String s) {
        super(s);
        this.mainFrame = ex6;
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
            }
        });
        setSize(230, 130);
        setLocation(700,0);
        this.setLayout(null);
        Container container = this.getContentPane();
        JLabel lb = new JLabel("Month:");
        lb.setBounds(10,15,100,30);
        container.add(lb);
        textField = new JTextField();
        textField.setBounds(60,20,100,20);
        ok = new JButton("OK");
        ok.setBounds(20,50,80,25);
        ok.addActionListener(this);
        cancel = new JButton("Cancel");
        cancel.setBounds(120,50,80,25);
        cancel.addActionListener(this);
        container.add(textField);
        container.add(ok);
        container.add(cancel);
        setVisible(true);
        setResizable(false);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(ok)) {
            mainFrame.searchMonthBirth(textField.getText());
        }
        if (e.getSource().equals(cancel)) {
            dispose();
        }
    }
}

class SearchForm extends JFrame implements ActionListener {
    private JButton ok;
    private JButton cancel;
    private  JTextField textField;
    Ex6 mainFrame;

    public SearchForm(Ex6 ex6, String s) {
        super(s + " Name");
        this.mainFrame = ex6;
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
            }
        });
        setSize(230, 130);
        setLocation(700,0);
        this.setLayout(null);
        Container container = this.getContentPane();
        JLabel lb = new JLabel("Name:");
        lb.setBounds(10,15,100,30);
        container.add(lb);
        textField = new JTextField();
        textField.setBounds(60,20,100,20);
        ok = new JButton("OK");
        ok.setBounds(20,50,80,25);
        ok.addActionListener(this);
        cancel = new JButton("Cancel");
        cancel.setBounds(120,50,80,25);
        cancel.addActionListener(this);
        container.add(textField);
        container.add(ok);
        container.add(cancel);
        setVisible(true);
        setResizable(false);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(ok)) {
            mainFrame.search(textField.getText());
        }
        if (e.getSource().equals(cancel)) {
            dispose();
        }
    }
}