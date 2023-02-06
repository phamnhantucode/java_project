/*
 * Created by JFormDesigner on Sun Jun 26 09:11:33 ICT 2022
 */

package Client.TankAZ;

import Client.ChatPanel;
import Client.ClientView;
import Client.MainScreen;
import Client.Servicee;
import Network.Message;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

/**
 * @author unknown
 */
public class RoomAZ extends JPanel {
    public static AZSetUp azSetUp;
    public static RoomAZ instance = new RoomAZ();
    public RoomAZ() {
        initComponents();
    }

    private void backBtn(ActionEvent e) {
        // TODO add your code here'
        ClientView.getInstance().setContentPane(MainScreen.instance);
        ClientView.getInstance().invalidate();
        ClientView.getInstance().validate();
        ClientView.getInstance().repaint();
    }

    private void addRoomBtnMousePressed(MouseEvent e) {
        // TODO add your code here
        roomConfig roomConfig = new roomConfig(ClientView.getInstance());
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        separator1 = new JSeparator();
        searchBtn = new JLabel();
        searchTf = new JTextField();
        addRoomBtn = new JLabel();
        backBtn = new JButton();
        scrollPane1 = new JScrollPane();
        panel1 = new JPanel();
//        roomPn = new JPanel();
//        label1 = new JLabel();
//        label2 = new JLabel();
//        label3 = new JLabel();
        background = new JLabel();

        //======== this ========
        setMinimumSize(new Dimension(500, 500));
        setLayout(null);
        add(separator1);
        separator1.setBounds(5, 105, 490, 5);

        //---- searchBtn ----
        searchBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                searchBtnMousePressed(e);
            }
        });
        add(searchBtn);
        searchBtn.setBounds(155, 35, 25, 25);

        //---- searchTf ----
        searchTf.setBorder(null);
        add(searchTf);
        searchTf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_E) {
                    ClientView.getInstance().serverCon.service.searchRoomAZ(searchTf.getText().trim());
                }
            }
        });
        searchTf.setBounds(180, 34, 155, 30);

        //---- addRoomBtn ----
        addRoomBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addRoomBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                addRoomBtnMousePressed(e);
            }
        });
        add(addRoomBtn);
        addRoomBtn.setBounds(422, 24, 55, 55);

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

        //======== scrollPane1 ========
        {
            scrollPane1.setBorder(null);

            //======== panel1 ========
            {
                panel1.setLayout(null);

//                //======== roomPn ========
//                {
//                    roomPn.setBackground(Color.white);
//                    roomPn.setLayout(null);
//
//                    //---- label1 ----
//                    label1.setIcon(new ImageIcon(getClass().getResource("/TankAZ/lock.png")));
//                    roomPn.add(label1);
//                    label1.setBounds(10, 10, 50, 55);
//
//                    //---- label2 ----
//                    label2.setText("NameRoom");
//                    roomPn.add(label2);
//                    label2.setBounds(75, 15, 220, 35);
//
//                    //---- label3 ----
//                    label3.setText("10000");
//                    label3.setIcon(new ImageIcon(getClass().getResource("/coin-icon.png")));
//                    roomPn.add(label3);
//                    label3.setBounds(325, 15, 150, label3.getPreferredSize().height);
//
//                    {
//                        // compute preferred size
//                        Dimension preferredSize = new Dimension();
//                        for(int i = 0; i < roomPn.getComponentCount(); i++) {
//                            Rectangle bounds = roomPn.getComponent(i).getBounds();
//                            preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
//                            preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
//                        }
//                        Insets insets = roomPn.getInsets();
//                        preferredSize.width += insets.right;
//                        preferredSize.height += insets.bottom;
//                        roomPn.setMinimumSize(preferredSize);
//                        roomPn.setPreferredSize(preferredSize);
//                    }
//                }
//                panel1.add(roomPn);
//                roomPn.setBounds(0, 0, 500, 75);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel1.getComponentCount(); i++) {
                        Rectangle bounds = panel1.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel1.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel1.setMinimumSize(preferredSize);
                    panel1.setPreferredSize(preferredSize);
                }
            }
            scrollPane1.setViewportView(panel1);
        }
        add(scrollPane1);
        scrollPane1.setBounds(0, 110, 525, 390);

        //---- background ----
        background.setIcon(new ImageIcon(getClass().getResource("/room-bg.png")));
        add(background);
        background.setBounds(new Rectangle(new Point(0, 0), background.getPreferredSize()));

        setPreferredSize(new Dimension(500, 500));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    private void searchBtnMousePressed(MouseEvent e) {
        ClientView.getInstance().serverCon.service.searchRoomAZ(searchTf.getText().trim());

    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JSeparator separator1;
    private JLabel searchBtn;
    private JTextField searchTf;
    private JLabel addRoomBtn;
    private JButton backBtn;
    private JScrollPane scrollPane1;
    private JPanel panel1;
//    private JPanel roomPn;
//    private JLabel label1;
//    private JLabel label2;
//    private JLabel label3;
    private JLabel background;


    // JFormDesigner - End of variables declaration  //GEN-END:variables
    ArrayList<RoomPn> list = new ArrayList<>();
    public void setListRoom(Message m) {
        try {
            list.removeAll(list);
            panel1.removeAll();
            int size = m.reader().readInt();
            for (int i = 0; i< size; i++) {
                RoomPn roomPn = new RoomPn(m.reader().readUTF(), m.reader().readUTF(), m.reader().readInt());
                list.add(roomPn);
                panel1.add(roomPn);
                roomPn.setBounds(0, i*75 , 500, 75);
            }
            {
                // compute preferred size of panel1
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel1.getComponentCount(); i++) {
                    Rectangle bounds = panel1.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel1.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel1.setMinimumSize(preferredSize);
                panel1.setPreferredSize(preferredSize);
                panel1.repaint();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void enterRoom(Message m) throws IOException {
        azSetUp = new AZSetUp(600, 600, m.reader().readUTF(), m.reader().readUTF(), m.reader().readInt());
        azSetUp.start();
    }

    class RoomPn extends JPanel {
        JLabel lock;
        JLabel name;
        JLabel bet;
        String nameR;
        String pass;
        int coin;

        public RoomPn(String nameR, String pass, int coin) {
            this.nameR = nameR;
            this.pass = pass;
            this.coin = coin;
            lock = new JLabel();
            name = new JLabel();
            bet = new JLabel();
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (!pass.equals("")) {
                        String s = JOptionPane.showInputDialog(RoomAZ.instance, "Enter pass room: ");
                        if (s.equals(pass)) {
                            //enter room
                            ClientView.getInstance().serverCon.service.enterRoomAZ(nameR);
//                            azSetUp = new AZSetUp(700, 700, nameR, pass, coin);
//                            azSetUp.start();
                        }
                    }
                }
            });
            init();
        }

        private void init() {
                this.setBackground(Color.white);
                this.setLayout(null);

                //---- label1 ----
                lock.setIcon(new ImageIcon(getClass().getResource("/lock.png")));
                this.add(lock);
                lock.setBounds(10, 10, 50, 55);
                if(pass.equals("")) lock.setVisible(false);

                //---- label2 ----
                name.setText(nameR);
                this.add(name);
                name.setBounds(75, 15, 220, 35);

                //---- label3 ----
                bet.setText(String.valueOf(coin));
                bet.setIcon(new ImageIcon(getClass().getResource("/coin-icon.png")));
                this.add(bet);
                bet.setBounds(325, 15, 150, bet.getPreferredSize().height);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < this.getComponentCount(); i++) {
                        Rectangle bounds = this.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = this.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    this.setMinimumSize(preferredSize);
                    this.setPreferredSize(preferredSize);
                }
//                panel1.add(this);
//                this.setBounds(0, 0, 500, 75);
        }

    }
}
