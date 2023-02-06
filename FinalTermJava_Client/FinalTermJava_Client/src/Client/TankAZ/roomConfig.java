/*
 * Created by JFormDesigner on Sun Jun 26 10:12:21 ICT 2022
 */

package Client.TankAZ;

import Client.ClientView;

import java.awt.*;
import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;

/**
 * @author unknown
 */
public class roomConfig extends JDialog {
    private Pattern pattern = Pattern.compile("[0-9]+");
    public roomConfig(Window owner) {
        super(owner);
        initComponents();
        this.setVisible(true);
    }

    private void passTfFocusGained(FocusEvent e) {
        // TODO add your code here
        if (passTf.getText().equals("optional")) {
            passTf.setText("");
            passTf.setForeground(Color.BLACK);
        }
    }

    private void passTfFocusLost(FocusEvent e) {
        // TODO add your code here
        if (passTf.getText().isEmpty()) {
            passTf.setForeground(Color.GRAY);
            passTf.setText("optional");
        }
    }

    private void OKBtn(ActionEvent e) {
        // TODO add your code here
        String text = nameTf.getText();
        if (text.equals("")) {
            JOptionPane.showMessageDialog(this, "Please enter name");
        } else {
            Matcher matcher = pattern.matcher(betTf.getText().trim());
            if (matcher.matches()) {
                if (betTf.getText().trim().length() > 6)  JOptionPane.showMessageDialog(this, "Can not bet more than 999999");
                else {
                    ClientView.getInstance().serverCon.service.addNewRoom(text.trim(), passTf.getText().trim(), Integer.parseInt(betTf.getText().trim()));
                    this.dispose();
                }
            } else JOptionPane.showMessageDialog(this, "Syntax number");
        }
//        this.dispose();
    }

    private void cancelBtn(ActionEvent e) {
        // TODO add your code here
        this.dispose();
    }



    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        nameLb = new JLabel();
        nameTf = new JTextField();
        passLb = new JLabel();
        passTf = new JTextField();
        betLb = new JLabel();
        OKBtn = new JButton();
        cancelBtn = new JButton();
        betTf = new JTextField();

        //======== this ========
        setBackground(Color.white);
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- nameLb ----
        nameLb.setText("Name room:");
        nameLb.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(nameLb);
        nameLb.setBounds(35, 35, 95, 25);
        contentPane.add(nameTf);
        nameTf.setBounds(140, 30, 205, 35);

        //---- passLb ----
        passLb.setText("Password:");
        passLb.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(passLb);
        passLb.setBounds(45, 85, 85, 30);

        //---- passTf ----
        passTf.setText(" ");
        passTf.setFont(passTf.getFont().deriveFont(passTf.getFont().getSize() - 1f));
        passTf.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                passTfFocusGained(e);
            }
            @Override
            public void focusLost(FocusEvent e) {
                passTfFocusLost(e);
            }
        });
        contentPane.add(passTf);
        passTf.setBounds(140, 85, 205, 35);

        //---- betLb ----
        betLb.setText("Bet: ");
        betLb.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(betLb);
        betLb.setBounds(75, 140, 45, 30);

        //---- OKBtn ----
        OKBtn.setText("OK");
        OKBtn.addActionListener(e -> OKBtn(e));
        contentPane.add(OKBtn);
        OKBtn.setBounds(100, 195, 85, 35);

        //---- cancelBtn ----
        cancelBtn.setText("Cacel");
        cancelBtn.addActionListener(e -> cancelBtn(e));
        contentPane.add(cancelBtn);
        cancelBtn.setBounds(230, 195, 85, 35);
        contentPane.add(betTf);
        betTf.setBounds(140, 135, 205, 35);

        contentPane.setPreferredSize(new Dimension(400, 275));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel nameLb;
    private JTextField nameTf;
    private JLabel passLb;
    private JTextField passTf;
    private JLabel betLb;
    private JButton OKBtn;
    private JButton cancelBtn;
    private JTextField betTf;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
