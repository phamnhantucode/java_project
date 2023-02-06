/*
 * Created by JFormDesigner on Sat Jun 11 20:28:41 ICT 2022
 */

package Client;

import Network.Message;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * @author unknown
 */
public class UserPanel extends JPanel {
    public static UserPanel instance = new UserPanel();
    ChangeAvatar changeAvatar;

    public UserPanel() {
        initComponents();
    }

    private void backBtn(ActionEvent e) {
        // TODO add your code here

        ClientView.getInstance().setContentPane(MainScreen.instance);
        ClientView.getInstance().invalidate();
        ClientView.getInstance().validate();
        ClientView.getInstance().repaint();
    }

    private void nameMouseClicked(MouseEvent e) {
        // TODO add your code here
        if (e.getClickCount() == 2) {
            this.name.setFocusable(true);
        }
    }

    private void name(ActionEvent e) {
        // TODO add your code here
        ClientView.getInstance().serverCon.service.reName(name.getText());
        System.out.println(name.getText());
        this.name.setFocusable(false);
    }

    private void settingMouseClicked(MouseEvent e) {
        // TODO add your code here
//        ClientView.getInstance().serverCon.service.getListAvatar();
         changeAvatar = new ChangeAvatar();

    }

//    private void nameKeyPressed(KeyEvent e) {
//        // TODO add your code here
//        if (e.getKeyCode() == 13) {
//            this.name.setFocusable(false);
//        }
//    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        backBtn = new JButton();
        setting = new JLabel();
        exp = new JLabel();
        coin = new JLabel();
        name = new JTextField();
        ranking = new JLabel();
        master = new JLabel();
        frame_avatar = new JLabel();
        avatar = new JLabel();
        background = new JLabel();

        //======== this ========
        setLayout(null);

        //---- backBtn ----
        backBtn.setIcon(new ImageIcon(getClass().getResource("/back-icon.png")));
        backBtn.setSelectedIcon(new ImageIcon(getClass().getResource("/back-icon1.png")));
        backBtn.setBackground(Color.white);
        backBtn.setBorder(null);
        backBtn.setBorderPainted(false);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.setContentAreaFilled(false);
        backBtn.addActionListener(e -> backBtn(e));
        add(backBtn);
        backBtn.setBounds(15, 25, 50, 50);

        //---- setting ----
        setting.setIcon(new ImageIcon(getClass().getResource("/setting.png")));
        setting.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setting.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                settingMouseClicked(e);
            }
        });
        add(setting);
        setting.setBounds(new Rectangle(new Point(365, 155), setting.getPreferredSize()));

        //---- exp ----
        exp.setText("0");
        exp.setFont(new Font("Fira Code Retina", Font.PLAIN, 20));
        exp.setIcon(new ImageIcon(getClass().getResource("/exp-icon.png")));
        exp.setToolTipText("Exp of player");
        add(exp);
        exp.setBounds(new Rectangle(new Point(280, 300), exp.getPreferredSize()));

        //---- coin ----
        coin.setIcon(new ImageIcon(getClass().getResource("/coin-icon.png")));
        coin.setText("111111");
        coin.setFont(new Font("Fira Code Retina", Font.PLAIN, 20));
        coin.setHorizontalAlignment(SwingConstants.CENTER);
        coin.setToolTipText("Coin");
        add(coin);
        coin.setBounds(new Rectangle(new Point(50, 300), coin.getPreferredSize()));

        //---- name ----
        name.setText("TranBao");
        name.setFont(new Font("Jokerman", Font.BOLD, 48));
        name.setBorder(null);
        name.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        name.setDisabledTextColor(Color.black);
        name.setToolTipText("Double click to rename");
        name.setHorizontalAlignment(SwingConstants.CENTER);
        name.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                nameMouseClicked(e);
            }
        });
        name.addActionListener(e -> name(e));
        add(name);
        name.setBounds(5, 195, 490, name.getPreferredSize().height);

        //---- ranking ----
        ranking.setText("0");
        ranking.setFont(new Font("Fira Code Retina", Font.PLAIN, 20));
        ranking.setIcon(new ImageIcon(getClass().getResource("/top.png")));
        ranking.setToolTipText("Money ranking");
        add(ranking);
        ranking.setBounds(285, 400, 56, 40);

        //---- master ----
        master.setText("Caro");
        master.setFont(new Font("Fira Code Retina", Font.PLAIN, 20));
        master.setIcon(new ImageIcon(getClass().getResource("/master.png")));
        master.setToolTipText("Master of something");
        master.setHorizontalAlignment(SwingConstants.LEFT);
        add(master);
        master.setBounds(45, 400, 175, 40);

        //---- frame_avatar ----
        frame_avatar.setIcon(new ImageIcon(getClass().getResource("/frame-avatar.png")));
        add(frame_avatar);
        frame_avatar.setBounds(new Rectangle(new Point(205, 60), frame_avatar.getPreferredSize()));

        //---- avatar ----
        avatar.setIcon(new ImageIcon(getClass().getResource("/user-icon.png")));
        avatar.setHorizontalAlignment(SwingConstants.CENTER);
        add(avatar);
        avatar.setBounds(205, 60, 90, 90);

        //---- background ----
        background.setIcon(new ImageIcon(getClass().getResource("/user-bg.gif")));
        background.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 22));
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
    private JButton backBtn;
    private JLabel setting;
    private JLabel exp;
    private JLabel coin;
    private JTextField name;
    private JLabel ranking;
    private JLabel master;
    private JLabel frame_avatar;
    private JLabel avatar;
    private JLabel background;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public void setInfor(String name, long coin, int exp, String master, int ranking) {
        this.coin.setText(String.valueOf(coin));
        this.name.setText(name);
        ClientView.getInstance().setTitle(name);
        this.exp.setText(String.valueOf(exp));
        this.ranking.setText(String.valueOf(ranking));
        this.master.setText(master);
        this.name.setFocusable(false);
        this.repaint();
//        this.name.setEditable(false);
//        this.name.setSelectionColor(Color.white);
//        this.name.setSelectionColor(Color.white);
    }

    public void setAvatar(Message m) throws IOException {
        int size = m.reader().readInt();
        byte[] image = new byte[size];
        m.reader().read(image, 0, size);
        BufferedImage avatar = ImageIO.read(new ByteArrayInputStream(image));
//        System.out.println(image.length);
        int width = m.reader().readInt();
        int height = m.reader().readInt();
        ImageIcon imageIcon = new ImageIcon(avatar);
        Image i = imageIcon.getImage();
        this.avatar.setIcon( new ImageIcon(i.getScaledInstance(width, height, Image.SCALE_SMOOTH)));
        this.avatar.repaint();
    }

    public void setAvatarPlayer(Message m) throws IOException {
        this.setting.setVisible(false);
        int size = m.reader().readInt();
        if (size > 0) {
            byte[] image = new byte[size];
            m.reader().read(image, 0, size);
            BufferedImage avatar = ImageIO.read(new ByteArrayInputStream(image));
//        System.out.println(image.length);
            ImageIcon imageIcon = new ImageIcon(avatar);
            Image i = imageIcon.getImage();
            this.avatar.setIcon( new ImageIcon(i.getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
            this.avatar.repaint();
        }
    }
}
