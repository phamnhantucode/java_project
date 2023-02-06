package Client;

import Client.GameTaiXiu.PlayScreen;
import Client.TankAZ.RoomAZ;
//import Server.Server;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/*
 * Created by JFormDesigner on Wed Jun 08 21:08:25 ICT 2022
 */



/**
 * 
 */
public class MainScreen extends JPanel {
    public static final MainScreen instance = new MainScreen();
    public MainScreen() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        initComponents();
    }

    private void userLbMouseEntered(MouseEvent e) {
        // TODO add your code here
        userLb.setIcon(new ImageIcon(getClass().getResource("/user-icon1.png")));
        userLb.setBounds(new Rectangle(new Point(12, 8), userLb.getPreferredSize()));
//        userLb.repaint();
    }

    private void userLbMouseExited(MouseEvent e) {
        // TODO add your code here
        userLb.setIcon(new ImageIcon(getClass().getResource("/user-icon.png")));
        userLb.setBounds(new Rectangle(new Point(15, 10), userLb.getPreferredSize()));
//        userLb.repaint();
    }

    private void friendLbMouseEntered(MouseEvent e) {
        // TODO add your code here
        friendLb.setIcon(new ImageIcon(getClass().getResource("/friend-icon1.png")));
        friendLb.setBounds(new Rectangle(new Point(12, 73), friendLb.getPreferredSize()));
//        friendLb.repaint();
    }

    private void friendLbMouseExited(MouseEvent e) {
        // TODO add your code here
        friendLb.setIcon(new ImageIcon(getClass().getResource("/friend-icon.png")));
        friendLb.setBounds(new Rectangle(new Point(15, 75), friendLb.getPreferredSize()));
//        friendLb.repaint();
    }

    private void messLbMouseEntered(MouseEvent e) {
        // TODO add your code here
        messLb.setIcon(new ImageIcon(getClass().getResource("/mess-icon1.png")));
        messLb.setBounds(new Rectangle(new Point(12, 143), messLb.getPreferredSize()));
//        messLb.repaint();
    }

    private void messLbMouseExited(MouseEvent e) {
        // TODO add your code here
        messLb.setIcon(new ImageIcon(getClass().getResource("/mess-icon.png")));
        messLb.setBounds(new Rectangle(new Point(15, 145), messLb.getPreferredSize()));
//        messLb.repaint();
    }

//    private void messLbMousePressed(MouseEvent e) {
//        // TODO add your code here
//        ClientView.getInstance().setContentPane(friendPanel.instance);
//        ClientView.getInstance().invalidate();
//        ClientView.getInstance().validate();
//        ClientView.getInstance().repaint();
//    }

    private void friendLbMousePressed(MouseEvent e) {
        // TODO add your code here
        ClientView.getInstance().serverCon.service.getListFriends();
        ClientView.getInstance().setContentPane(friendPanel.instance);
        ClientView.getInstance().invalidate();
        ClientView.getInstance().validate();
        ClientView.getInstance().repaint();

    }

    private void userLbMouseClicked(MouseEvent e) {
        // TODO add your code here
        ClientView.getInstance().setContentPane(UserPanel.instance);
        ClientView.getInstance().invalidate();
        ClientView.getInstance().validate();
        ClientView.getInstance().repaint();
    }

    private void messLbMouseClicked(MouseEvent e) {
        // TODO add your code here
        ClientView.getInstance().serverCon.service.getListChat();
        ClientView.getInstance().setContentPane(ChatPanel.instance);
        ClientView.getInstance().invalidate();
        ClientView.getInstance().validate();
        ClientView.getInstance().repaint();

    }

    private void TaiXiuMouseClicked(MouseEvent e) {
        // TODO add your code her
        JFrame clientView = new JFrame();
        clientView.setTitle(ClientView.name);
        clientView.setContentPane(PlayScreen.instance);
        clientView.setSize(PlayScreen.instance.getPreferredSize().width,PlayScreen.instance.getPreferredSize().height + 20 );
        ClientView.getInstance().setLocation(1400, 0);
        clientView.setVisible(true);
        clientView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                clientView.dispose();
            }
        });
        PlayScreen.instance.clientView = clientView;
//        ClientView.getInstance().invalidate();
//        ClientView.getInstance().validate();
//        ClientView.getInstance().repaint();
    }

    private void AZMouseClicked(MouseEvent e) {
        // TODO add your code here
        ClientView.getInstance().serverCon.service.getListRoomAZ();
        ClientView.getInstance().setContentPane(RoomAZ.instance);
        ClientView.getInstance().invalidate();
        ClientView.getInstance().validate();
        ClientView.getInstance().repaint();
    }




    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        TaiXiu = new JLabel();
        AZ = new JLabel();
        userLb = new JLabel();
        friendLb = new JLabel();
        coinLb = new JLabel();
        messLb = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        label5 = new JLabel();
        background = new JLabel();

        //======== this ========
        setLayout(null);

        //---- TaiXiu ----
        TaiXiu.setIcon(new ImageIcon(getClass().getResource("/logo-taixiu.png")));
        TaiXiu.setText("Sic bo");
        TaiXiu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        TaiXiu.setVerticalTextPosition(SwingConstants.BOTTOM);
        TaiXiu.setHorizontalTextPosition(SwingConstants.CENTER);
        TaiXiu.setFont(new Font("Segoe Print", Font.BOLD, 14));
        TaiXiu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TaiXiuMouseClicked(e);
            }
        });
        add(TaiXiu);
        TaiXiu.setBounds(new Rectangle(new Point(125, 235), TaiXiu.getPreferredSize()));

        //---- AZ ----
        AZ.setIcon(new ImageIcon(getClass().getResource("/icon-game.png")));
        AZ.setText("Tank AZ");
        AZ.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        AZ.setVerticalTextPosition(SwingConstants.BOTTOM);
        AZ.setHorizontalTextPosition(SwingConstants.CENTER);
        AZ.setFont(new Font("Segoe Print", Font.BOLD, 14));
        AZ.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AZMouseClicked(e);
            }
        });
        add(AZ);
        AZ.setBounds(220, 235, 50, 80);

        //---- userLb ----
        userLb.setIcon(new ImageIcon(getClass().getResource("/user-icon.png")));
        userLb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        userLb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                userLbMouseClicked(e);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                userLbMouseEntered(e);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                userLbMouseExited(e);
            }
        });
        add(userLb);
        userLb.setBounds(new Rectangle(new Point(15, 10), userLb.getPreferredSize()));

        //---- friendLb ----
        friendLb.setIcon(new ImageIcon(getClass().getResource("/friend-icon.png")));
        friendLb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        friendLb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                friendLbMouseEntered(e);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                friendLbMouseExited(e);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                friendLbMousePressed(e);
            }
        });
        add(friendLb);
        friendLb.setBounds(new Rectangle(new Point(15, 75), friendLb.getPreferredSize()));

        //---- coinLb ----
        coinLb.setText("0");
        coinLb.setHorizontalTextPosition(SwingConstants.LEFT);
        coinLb.setFocusable(false);
        coinLb.setHorizontalAlignment(SwingConstants.RIGHT);
        coinLb.setFont(new Font("Arial", Font.BOLD, 16));
        coinLb.setForeground(Color.white);
        add(coinLb);
        coinLb.setBounds(385, 20, 95, 30);

        //---- messLb ----
        messLb.setIcon(new ImageIcon(getClass().getResource("/mess-icon.png")));
        messLb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                messLbMouseClicked(e);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                messLbMouseEntered(e);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                messLbMouseExited(e);
            }
        });
        add(messLb);
        messLb.setBounds(new Rectangle(new Point(15, 145), messLb.getPreferredSize()));

        //---- label3 ----
        label3.setIcon(new ImageIcon("/logo-taixiu.png"));
        add(label3);
        label3.setBounds(325, 235, 50, 50);

        //---- label4 ----
        label4.setIcon(new ImageIcon("/logo-taixiu.png"));
        add(label4);
        label4.setBounds(120, 330, 50, 50);

        //---- label5 ----
        label5.setIcon(new ImageIcon("/logo-taixiu.png"));
        add(label5);
        label5.setBounds(225, 330, 50, 50);

        //---- background ----
        background.setIcon(new ImageIcon(getClass().getResource("/gameroomBG.png")));
        background.setFont(new Font("Droid Sans Mono Dotted", Font.PLAIN, 14));
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
    private JLabel TaiXiu;
    private JLabel AZ;
    private JLabel userLb;
    private JLabel friendLb;
    private JLabel coinLb;
    private JLabel messLb;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel background;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public void setCoin(long coin) {
        this.coinLb.setText(String.valueOf(coin));
        System.out.println(coin);
//        this.coinLb.repaint();
    }
}
