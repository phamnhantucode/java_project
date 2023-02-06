/*
 * Created by JFormDesigner on Thu Jun 09 15:30:43 ICT 2022
 */

package Client;

import java.awt.event.*;
import Network.Message;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author unknown
 */
public class friendPanel extends JPanel {
    public static final friendPanel instance = new friendPanel();

    public friendPanel() {
        initComponents();
    }

    private void backBtn(ActionEvent e) {
        // TODO add your code here
        ClientView.getInstance().setContentPane(MainScreen.instance);
        ClientView.getInstance().invalidate();
        ClientView.getInstance().validate();
        ClientView.getInstance().repaint();
    }

    private void friendBtnMouseClicked(MouseEvent e) {
        // TODO add your code here
        flag = true;
        ClientView.getInstance().serverCon.service.getListFriends();
        isFriend = true;
    }

    private void worldBtnMouseClicked(MouseEvent e) {
        // TODO add your code here
        flag = false;
        ClientView.getInstance().serverCon.service.getListPlayers();
        isFriend = false;
    }

    private void searchBtnMouseClicked(MouseEvent e) {
        // TODO add your code here
        ClientView.getInstance().serverCon.service.getListFind(searchTf.getText());
    }

    private void initComponents() {
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
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        friendBtn = new JLabel();
        worldBtn = new JLabel();
        searchBtn = new JLabel();
        backBtn = new JButton();
        scrollPane1 = new JScrollPane();
        friendListPanel = new JPanel();
        searchTf = new JTextField();
        background = new JLabel();

        //======== this ========
        setBackground(Color.white);
        setLayout(null);

        //---- friendBtn ----
        friendBtn.setText("Friends");
        friendBtn.setHorizontalAlignment(SwingConstants.CENTER);
        friendBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        friendBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        friendBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                friendBtnMouseClicked(e);
            }
        });
        add(friendBtn);
        friendBtn.setBounds(20, 150, 85, 30);

        //---- worldBtn ----
        worldBtn.setText("World");
        worldBtn.setHorizontalAlignment(SwingConstants.CENTER);
        worldBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        worldBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        worldBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                worldBtnMouseClicked(e);
            }
        });
        add(worldBtn);
        worldBtn.setBounds(125, 150, 85, 30);

        //---- searchBtn ----
        searchBtn.setHorizontalAlignment(SwingConstants.CENTER);
        searchBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                searchBtnMouseClicked(e);
            }
        });
        add(searchBtn);
        searchBtn.setBounds(420, 35, 30, 30);

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
        backBtn.setBounds(10, 25, 50, backBtn.getPreferredSize().height);

        //======== scrollPane1 ========
        {
            scrollPane1.setBorder(null);
            scrollPane1.setViewportBorder(LineBorder.createBlackLineBorder());
            scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

            //======== friendListPanel ========
            {
                friendListPanel.setBackground(Color.white);
                friendListPanel.setLayout(null);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < friendListPanel.getComponentCount(); i++) {
                        Rectangle bounds = friendListPanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = friendListPanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    friendListPanel.setMinimumSize(preferredSize);
                    friendListPanel.setPreferredSize(preferredSize);
                }
            }
            scrollPane1.setViewportView(friendListPanel);
        }
        add(scrollPane1);
        scrollPane1.setBounds(-5, 190, 530, 315);

        //---- searchTf ----
        searchTf.setBorder(null);
        add(searchTf);
        searchTf.setBounds(240, 37, 180, 26);

        //---- background ----
        background.setIcon(new ImageIcon(getClass().getResource("/friend-bg.png")));
        add(background);
        background.setBounds(new Rectangle(new Point(0, 0), background.getPreferredSize()));

        setPreferredSize(new Dimension(500, 500));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel friendBtn;
    private JLabel worldBtn;
    private JLabel searchBtn;
    private JButton backBtn;
    private JScrollPane scrollPane1;
    private JPanel friendListPanel;
    private JTextField searchTf;
    private JLabel background;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    boolean flag;
    boolean isFriend = true;
    ArrayList<tabFriendPanel> tabFriendPanels;
    public void setInforList(Message m) throws IOException {

        friendListPanel.removeAll();
        tabFriendPanels = new ArrayList<>();
        int size = m.reader().readInt();
//        System.out.println(size);
        int count = 0;
        for (int i = 0; i < size; i++) {

            boolean flag = false;
            int idPlayer = m.reader().readInt();
            System.out.println(idPlayer);
            String name = m.reader().readUTF();
            System.out.println(name);

            int on = m.reader().readInt();
            if (on == 1)
                flag = true;
//            System.out.println(flag);
            tabFriendPanel tabFriendPanel = new tabFriendPanel(name, flag, idPlayer);
            if (idPlayer != ClientView.id) {
                int sizeI = m.reader().readInt();
                if (sizeI!=0) {
                    byte[] image = new byte[sizeI];
                    int len = 0;
                    int byteRead = 0;
                    while (len != -1 && byteRead < sizeI) {
                        len = m.reader().read(image, byteRead, sizeI-byteRead);
                        if (len > 0) {
                            byteRead += len;
                        }
                    }
                    tabFriendPanel.setAvatar(image);
                }
                tabFriendPanels.add(tabFriendPanel);
                friendListPanel.add(tabFriendPanel);

                tabFriendPanel.setBounds(0, count * 80, 510, 75);
                count ++;
            }

        }
        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < friendListPanel.getComponentCount(); i++) {
                Rectangle bounds = friendListPanel.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = friendListPanel.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            friendListPanel.setMinimumSize(preferredSize);
            friendListPanel.setPreferredSize(preferredSize);
        }
        friendListPanel.repaint();

    }


    class tabFriendPanel extends JPanel {
        public int idPlayer;
        private JLabel avatar ;
        private JLabel frame;
        private JLabel name ;
        private JLabel chat;
        private JRadioButton onoff;
		private String namePlayer;
        public tabFriendPanel(String namePlayer, boolean flag, int idPlayer) {//flag = true => on
            //init
            avatar = new JLabel();
            frame = new JLabel();
            name = new JLabel();
            chat = new JLabel();
            onoff = new JRadioButton();



            this.idPlayer = idPlayer;
            this.setBackground(Color.white);
            this.setBorder(new LineBorder(Color.lightGray, 1, true));
            this.setLayout(null);

            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    mouseClickedTab(e);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                }
            });



            frame.setIcon(new ImageIcon(getClass().getResource("/frame-avatar-mess.png")));
            frame.setHorizontalAlignment(SwingConstants.CENTER);
            this.add(frame);
            frame.setBounds(0, 0, 80, 75);
            //---- avatarLb ----
            avatar.setIcon(new ImageIcon(getClass().getResource("/player-icon.png")));
            avatar.setHorizontalAlignment(SwingConstants.CENTER);
            this.add(avatar);
            avatar.setBounds(0, 0, 80, 75);

            //---- nameLb ----]
            this.namePlayer = namePlayer;
            name.setText(namePlayer);
            name.setFont(new Font("Consolas", Font.BOLD, 20));
            this.add(name);
            name.setBounds(85, 20, 145, 40);

            //---- chatLb ----
            chat.setIcon(new ImageIcon(getClass().getResource("/chat-icon.gif")));
            this.add(chat);
            chat.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            chat.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    chatLbMouseClicked(e);
                }
            });
            chat.setBounds(new Rectangle(new Point(430, 20), chat.getPreferredSize()));
            if (!isFriend) {
                chat.setVisible(false);
            }

            //---- onOff ----
            onoff.setSelectedIcon(new ImageIcon(getClass().getResource("/online-icon.png")));
            onoff.setBorder(null);
            onoff.setDisabledIcon(new ImageIcon(getClass().getResource("/off-icon.png")));
            onoff.setFocusPainted(false);
            onoff.setPressedIcon(new ImageIcon(getClass().getResource("/online-icon.png")));
            onoff.setFocusable(false);
            onoff.setIcon(new ImageIcon(getClass().getResource("/off-icon.png")));
            onoff.setBackground(Color.white);
            onoff.setSelected(flag);
            this.add(onoff);
//            onoff.setEnabled(false);
            onoff.setBounds(new Rectangle(new Point(375, 25), onoff.getPreferredSize()));

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
        }

        private void mouseClickedTab(MouseEvent e) {
            Popup p = new Popup();
            p.show(this, e.getX(), e.getY());
        }

        private void chatLbMouseClicked(MouseEvent e) {
            for (int i = 0; i < ChatPanel.instance.tabChatPanels.size(); i++) {
            	if (ChatPanel.instance.tabChatPanels.get(i).have(this.namePlayer)) {
            		ChatPanel.instance.tabChatPanels.get(i).onChatPanelM();
            		ClientView.getInstance().serverCon.service.updateListChat(this.idPlayer);
            		return;
            	}
            }
            ClientView.getInstance().serverCon.service.startChat(this.idPlayer);
            ClientView.getInstance().serverCon.service.updateListChat(this.idPlayer);
//            System.out.println(idPlayer);
        }

        public void setAvatar(byte[] image) throws IOException {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(image));
            ImageIcon imageIcon = new ImageIcon(bufferedImage);
            Image i = imageIcon.getImage();
            this.avatar.setIcon(new ImageIcon(i.getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
            this.avatar.repaint();
        }

        class Popup extends JPopupMenu {
            JMenuItem addFriend;
            JMenuItem infor;
            JMenuItem remove;
            public Popup() {
                addFriend = new JMenuItem("Add Friend");
                addFriend.setActionCommand("addFriend");
                addFriend.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ClientView.getInstance().serverCon.service.addFriend(idPlayer);
                    }
                });
                //
                infor = new JMenuItem("Infor");
                infor.setActionCommand("infor");
                infor.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ClientView.getInstance().serverCon.service.showInfor(idPlayer);
                    }
                });
                //
                remove = new JMenuItem("Delete Friend");
                remove.setActionCommand("remove");
                remove.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ClientView.getInstance().serverCon.service.removeFriend(idPlayer);
                    }
                });
                add(addFriend);
                add(infor);
                add(remove);
                if (isFriend) {
                    addFriend.setEnabled(false);
                }
                else {
                    remove.setEnabled(false);
                }
            }
        }
    }
}
