/*
 * Created by JFormDesigner on Sun Jun 12 15:08:46 ICT 2022
 */

package Client;

import Network.Message;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * @author unknown
 */
public class ChatPanel extends JPanel {
    public static ChatPanel instance = new ChatPanel();
    public ChatPanel() {
        initComponents();
    }

    private void backBtn(ActionEvent e) {
        // TODO add your code here
        ClientView.getInstance().setContentPane(MainScreen.instance);
        ClientView.getInstance().invalidate();
        ClientView.getInstance().validate();
        ClientView.getInstance().repaint();
    }



    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        backBtn = new JButton();
        label2 = new JLabel();
        chatScroll = new JScrollPane();
        chatsPanel = new JPanel();
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
        backBtn.setBounds(10, 15, 50, 50);

        //---- label2 ----
        label2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(label2);
        label2.setBounds(400, 25, 75, 50);

        //======== chatScroll ========
        {
            chatScroll.setBorder(null);

            //======== chatsPanel ========
            {
                chatsPanel.setBackground(Color.white);
                chatsPanel.setLayout(null);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < chatsPanel.getComponentCount(); i++) {
                        Rectangle bounds = chatsPanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = chatsPanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    chatsPanel.setMinimumSize(preferredSize);
                    chatsPanel.setPreferredSize(preferredSize);
                }
            }
            chatScroll.setViewportView(chatsPanel);
        }
        add(chatScroll);
        chatScroll.setBounds(5, 150, 515, 350);

        //---- background ----
        background.setIcon(new ImageIcon(getClass().getResource("/chat-bg.gif")));
        add(background);
        background.setBounds(new Rectangle(new Point(0, 0), background.getPreferredSize()));

        setPreferredSize(new Dimension(500, 500));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JButton backBtn;
    private JLabel label2;
    private JScrollPane chatScroll;
    private JPanel chatsPanel;
    private JLabel background;
    // JFormDesigner - End of variables declaration  //GEN-END:variables


    ArrayList<TabChatPanel> tabChatPanels = new ArrayList<>();
    public void setChatList(Message m) {
        try {
            chatsPanel.removeAll();
            tabChatPanels.removeAll(tabChatPanels);
            int size = m.reader().readInt();

            for (int i = 0; i < size; i++) {
                int id = m.reader().readInt();
                String name = m.reader().readUTF();
                String content = m.reader().readUTF();
                int sizeI = m.reader().readInt();

                TabChatPanel tabChatPanel = new TabChatPanel(id, name, content);
                if (sizeI != 0) {
                    byte[] image = new byte[sizeI];
                    int len = 0;
                    int byteRead = 0;
                    while(len != -1 && byteRead < sizeI) {
                        len = m.reader().read(image, byteRead, sizeI - byteRead);
                        if(len > 0)
                            byteRead += len;
                    }
                        tabChatPanel.setAvatar(image);
                }
                tabChatPanels.add(tabChatPanel);
                chatsPanel.add(tabChatPanel);
                tabChatPanel.setBounds(0, i*75, 510, 75);
            }
            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < chatsPanel.getComponentCount(); i++) {
                    Rectangle bounds = chatsPanel.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = chatsPanel.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                chatsPanel.setMinimumSize(preferredSize);
                chatsPanel.setPreferredSize(preferredSize);
            }
            chatsPanel.repaint();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startChat(int readInt) {
        for (int i = 0; i < tabChatPanels.size(); i++) {
            if (tabChatPanels.get(i).chatId == readInt) {
                tabChatPanels.get(i).onChatPanelM();
                return;
            }
        }
    }

    public void updateChat(Message m) {
        try {
            int idChat = m.reader().readInt();
            String content = m.reader().readUTF();
            System.out.println(content);
            for (int i = 0; i < tabChatPanels.size(); i++) {
                if(tabChatPanels.get(i).chatId == idChat) {
                    tabChatPanels.get(i).content = content;
                    System.out.println(idChat);
                    tabChatPanels.get(i).refresh();
//                    tabChatPanels.get(i).invalidate();
//                    tabChatPanels.get(i).validate();

//                    tabChatPanels.get(i).onChatPanel.invalidate();
//                    tabChatPanels.get(i).onChatPanel.validate();
//                    tabChatPanels.get(i).onChatPanel.repaint();
//                    chatsPanel.repaint();

                    return;
                }
            }
            ClientView.getInstance().serverCon.service.getListChat();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class TabChatPanel extends JPanel {

        int chatId;
        String nameChat;
        String content;
        OnChatPanel onChatPanel;


        private JLabel avatar;
        private JLabel name;
        private JLabel mess;
        private JLabel frame_avatar;

        public TabChatPanel(int chatId, String nameChat, String content) {
            this.chatId = chatId;
            this.nameChat = nameChat;
            this.content = content;
            //GUI
            frame_avatar = new JLabel();
            avatar = new JLabel();

            name = new JLabel();
                    mess = new JLabel();
            this.setBackground(Color.white);
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    tabChatPanelMouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    tabChatPanelMouseExited(e);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    tabChatPanelMouseClicked(e);
                }
            });
            this.setLayout(null);

            //
            frame_avatar.setIcon(new ImageIcon(getClass().getResource("/frame-avatar-mess.png")));
            this.add(frame_avatar);
            frame_avatar.setBounds(new Rectangle(new Point(5, 5), frame_avatar.getPreferredSize()));
            //---- avatar ----
            avatar.setIcon(new ImageIcon(getClass().getResource("/player-icon.png")));
            this.add(avatar);
            avatar.setBounds(new Rectangle(new Point(5, 5), avatar.getPreferredSize()));

            //---- name ----
            name.setText("TranBao");
            name.setFont(new Font("Segoe Script", Font.PLAIN, 20));
            this.add(name);
            name.setBounds(new Rectangle(new Point(80, 10), name.getPreferredSize()));

            //---- mess ----
            mess.setText("chat message here");
            mess.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            this.add(mess);
            mess.setBounds(85, 40, 390, mess.getPreferredSize().height);

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
            System.out.println(nameChat.split("//").length);

            if (nameChat.split("//").length == 2) {
                name.setText(nameChat.split("//")[0].equals(ClientView.name) ? nameChat.split("//")[1] : nameChat.split("//")[0]);
            } else {
                name.setText(nameChat);
            }

//            String chat
            if (content.equals("")) {
                mess.setText("");
            }
            else {
                String[] contents = content.split("//");
                String[] lastchat = contents[contents.length-1].split("/");
                if (lastchat[0].equals(ClientView.name))
                    mess.setText("You: " + lastchat[1]);
                else mess.setText(lastchat[0] + ": " + lastchat[1]);
            }



            onChatPanel = new OnChatPanel(chatId, nameChat, content);

        }

        private void tabChatPanelMouseClicked(MouseEvent e) {
            onChatPanelM();
        }

         void onChatPanelM() {

            ClientView.getInstance().setContentPane(onChatPanel);
            ClientView.getInstance().invalidate();
            ClientView.getInstance().validate();
            ClientView.getInstance().repaint();
        }

        private void tabChatPanelMouseEntered(MouseEvent e) {
            // TODO add your code here
            this.setBackground(new Color(252,252,252));
            this.frame_avatar.setIcon(new ImageIcon(getClass().getResource("/frame-avatar-mess-hover.png")));
            this.frame_avatar.repaint();
        }

        private void tabChatPanelMouseExited(MouseEvent e) {
            // TODO add your code here
            this.setBackground(Color.white);
            this.frame_avatar.setIcon(new ImageIcon(getClass().getResource("/frame-avatar-mess.png")));
            this.frame_avatar.repaint();
        }


        public void refresh() {
            if (content.equals("")) {
                mess.setText("");
            }
            else {
                String[] contents = content.split("//");
                String[] lastchat = contents[contents.length-1].split("/");
                if (lastchat.length > 1) {
                    if (lastchat[0] == ClientView.name)
                        mess.setText("You: " + lastchat[1]);
                    else mess.setText(lastchat[0] + " " + lastchat[1]);
                }
                else {
                }
            }
            this.repaint();
            onChatPanel.content = this.content;
            onChatPanel.refresh();
        }

        public void setAvatar(byte[] image) throws IOException {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(image));
            ImageIcon imageIcon = new ImageIcon(bufferedImage);
            Image i = imageIcon.getImage();
                this.avatar.setIcon(new ImageIcon(i.getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
                this.avatar.repaint();
        }

		public boolean have(String namePlayer) {
			// TODO Auto-generated method stub
			if (namePlayer.equals(nameChat)) return true;
			return false;
		}

    }
}
