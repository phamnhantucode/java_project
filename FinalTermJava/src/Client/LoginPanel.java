/*
 * Created by JFormDesigner on Tue Jun 07 10:57:26 ICT 2022
 */

package Client;

import Network.Message;
//import Server.ClientCon;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.*;
import javax.swing.border.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * @author TranBao
 */
public class LoginPanel extends JPanel {
    public LoginPanel()
    {
        initComponents();
    }

    private void registerBtn(ActionEvent e) {
    	ClientView.getInstance().serverCon.connect();
        // TODO add your code here
        if (!userTf.getText().equals("") && !passTf.getPassword().equals(""))
            ClientView.getInstance().serverCon.service.register(userTf.getText(), parse(String.valueOf(passTf.getPassword())));
        else JOptionPane.showMessageDialog(this, "Please fill it out completely");

    }

    private String parse(String valueOf) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] pass = md.digest(valueOf.getBytes());
            BigInteger no = new BigInteger(1, pass);
            String hass = no.toString(16);
            while (hass.length() < 32) {
                hass = "0" + hass;
            }
            return hass;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void loginBtn(ActionEvent e) {
        // TODO add your code here
    	ClientView.getInstance().serverCon.connect();
        if (!userTf.getText().equals("") && !passTf.getPassword().equals(""))
            ClientView.getInstance().serverCon.service.login(userTf.getText(), parse(String.valueOf(passTf.getPassword())));
        else JOptionPane.showMessageDialog(this, "Please fill it out completely");
        if (rememberCb.isSelected()) {
            storedPassAndUsername();
        }
        if (!rememberCb.isSelected()) {
            new File("account.xml").delete();
        }
    }

    public void setPassTf(String s) {
        this.passTf.setText(s);
    }

    private void rememberCb(ActionEvent e) {
        // TODO add your code here

    }



    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        rememberCb = new JCheckBox();
        loginBtn = new JButton();
        registerBtn = new JButton();
        userTf = new JTextField();
        passTf = new JPasswordField();
        background = new JLabel();

        //======== this ========
        setLayout(null);

        //---- rememberCb ----
        rememberCb.setText("Remember me");
        rememberCb.setBackground(new Color(237, 233, 207));
        rememberCb.addActionListener(e -> rememberCb(e));
        add(rememberCb);
        rememberCb.setBounds(195, 320, 240, rememberCb.getPreferredSize().height);

        //---- loginBtn ----
        loginBtn.setText("Login");
        loginBtn.addActionListener(e -> loginBtn(e));
        add(loginBtn);
        loginBtn.setBounds(295, 380, 100, 35);

        //---- registerBtn ----
        registerBtn.setText("Register");
        registerBtn.addActionListener(e -> registerBtn(e));
        add(registerBtn);
        registerBtn.setBounds(100, 380, 115, 35);
        add(userTf);
        userTf.setBounds(195, 195, 255, 40);
        add(passTf);
        passTf.setBounds(195, 270, 255, 40);

        //---- background ----
        background.setIcon(new ImageIcon(getClass().getResource("/VUI Game!.png")));
        add(background);
        background.setBounds(new Rectangle(new Point(0, 0), background.getPreferredSize()));

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < getComponentCount(); i++) {
                Rectangle bounds = getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            setMinimumSize(preferredSize);
            setPreferredSize(preferredSize);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JCheckBox rememberCb;
    private JButton loginBtn;
    private JButton registerBtn;
    private JTextField userTf;
    private JPasswordField passTf;
    private JLabel background;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    private JLabel confirmLb;
    private JPasswordField confirmTf;

    private void storedPassAndUsername() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbFactory.newDocumentBuilder();
            Document document = builder.newDocument();

            //create content
            Element rootElement = document.createElement("account");
            document.appendChild(rootElement);
            //store username;
            Element username =  document.createElement("username");
            username.appendChild(document.createTextNode(userTf.getText().trim()));
            rootElement.appendChild(username);
            //store pass
            Element pass =  document.createElement("password");
            pass.appendChild(document.createTextNode(new String(passTf.getPassword()).trim()));
            rootElement.appendChild(pass);


            //write XML
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(document);
            File f = new File("account.xml");
            if (!f.exists()) {
                f.createNewFile();
            } else {
                f.delete();
                f.createNewFile();
            }
            StreamResult streamResult = new StreamResult(f);
            transformer.transform(source, streamResult);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void readStoredAccount() {
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbFactory.newDocumentBuilder();
            File f = new File("account.xml");
            if (!f.exists()) return;
            Document document = builder.parse(f);

            //read XML
            Element element = (Element) document.getElementsByTagName("account").item(0);
            userTf.setText(element.getElementsByTagName("username").item(0).getTextContent());
            passTf.setText(element.getElementsByTagName("password").item(0).getTextContent());
            rememberCb.setSelected(true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
