/*
 * Created by JFormDesigner on Tue Jun 07 10:56:04 ICT 2022
 */

package Client;

import java.awt.event.*;
import Network.Message;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

/**
 * @author unknown
 */
public class ClientView extends JFrame {
    static String tmp;
    public static String name;
    static long coin;
    static int id;
    static int exp;
    static String master;
    static int ranking;
    public ServerCon serverCon;
    private static ClientView instance = new ClientView();
    public LoginPanel loginPanel;
    public ClientView() {
    	this.setIconImage(new ImageIcon(getClass().getResource("/logo.png")).getImage());
        //
//        this.serverCon = serverCon;
        //
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        loginPanel = new LoginPanel();
        loginPanel.readStoredAccount();
        this.setContentPane(loginPanel);

        setVisible(true);
        pack();
    }

    public static ClientView getInstance() {
        return instance;
    }


    public static void setInforUser(Message m) {
        try {
            id = m.reader().readInt();
            System.out.println(id);
            name = m.reader().readUTF();
            coin = m.reader().readLong();
            exp = m.reader().readInt();
            master = m.reader().readUTF();
            ranking = m.reader().readInt();
            MainScreen.instance.setCoin(coin);
            UserPanel.instance.setInfor(name, coin, exp, master, ranking);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public void startOKDialog(String text) {
        JOptionPane.showMessageDialog(this, text);
    }

    public void startLoad() {
        this.setContentPane(LoadPanel.instance);
        this.invalidate();
        this.validate();
        this.repaint();
        try {
            Thread.sleep(2000);
            this.setContentPane(MainScreen.instance);
            this.invalidate();
            this.validate();
            this.repaint();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public static String getDotNumber(long m)
    {
        String text = "";
        long num = m / 1000L + 1L;
        int num2 = 0;
        while ((long)num2 < num)
        {
            if (m < 1000L)
            {
                text = m + text;
                break;
            }
            long num3 = m % 1000L;
            if (num3 == 0L)
            {
                text = ".000" + text;
            }
            else if (num3 < 10L)
            {
                text = ".00" + num3 + text;
            }
            else if (num3 < 100L)
            {
                text = ".0" + num3 + text;
            }
            else
            {
                text = "." + num3 + text;
            }
            m /= 1000L;
            num2++;
        }
//        System.out.println(text);
        return text;
    }

    public void showInfor(Message m) throws IOException {
        UserPanel u = new UserPanel();
        u.setInfor(m.reader().readUTF(), m.reader().readInt(), 0, "Không", 0);
        u.setAvatarPlayer(m);
        instance.setContentPane(u);
        instance.invalidate();
        instance.validate();
        instance.repaint();
    }
}
