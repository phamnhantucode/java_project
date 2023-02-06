/*
 * Created by JFormDesigner on Sun Jun 12 22:40:27 ICT 2022
 */

package Client;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author unknown
 */
public class OnChatPanel extends JPanel {
    int chatId;
    String name;
    String content;
    ArrayList<ContentChat> contentChats = new ArrayList<>();
    public OnChatPanel(int chatId, String name, String content) {
        this.chatId = chatId;
        this.name = name;
        this.content = content;
        initComponents();
        update();
    }

    private void update() {

        chatScreen.removeAll();
        chatScreen.revalidate();
        contentChats.removeAll(contentChats);
        if (!content.equals("")) {
            String[] contents = content.split("//");
            for (int i = 0; i < contents.length; i++) {
                ContentChat contentChat = new ContentChat(contents[i].split("/")[0],contents[i].split("/")[1], contents[i].split("/")[2]);
                contentChats.add(contentChat);
                chatScreen.add(contentChat);
                contentChat.setBounds(0, (i)*85, 500, 78);
            }
            {
                // compute preferred size of chatScreen
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < chatScreen.getComponentCount(); i++) {
                    Rectangle bounds = chatScreen.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = chatScreen.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                chatScreen.setMinimumSize(preferredSize);
                chatScreen.setPreferredSize(preferredSize);
            }
        }
        //name chat
        if (name.split("//").length == 2) {
            chatName.setText(name.split("//")[0].equals(ClientView.name) ? name.split("//")[1] : name.split("//")[0]);
        } else {
            chatName.setText(name);
        }

        //lăn xuống dưới
//        scrollPane1.removeAll();
//        scrollPane1.add(chatScreen);
        scrollPane1.revalidate();
//        chatScreen.repaint();
        scrollPane1.repaint();
        JScrollBar vertical = scrollPane1.getVerticalScrollBar();
//        System.out.println(vertical.getMaximum());
        vertical.setValue(contentChats.size() * 75);
        vertical.setValue(contentChats.size() * 75);
//        System.out.println(contentChats.size() * 75);

//        this.repaint();
    }

    private void backBtn(ActionEvent e) {
        // TODO add your code here
        ClientView.getInstance().setContentPane(ChatPanel.instance);
        ClientView.getInstance().invalidate();
        ClientView.getInstance().validate();
        ClientView.getInstance().repaint();
    }

    private void chatSend(ActionEvent e) {
        // TODO add your code here

    	if (chatSend.getText().equals("")) {
    		JOptionPane.showMessageDialog(this, "Empty messeage");
    		return;
    	};
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String time = dateTimeFormatter.format(LocalDateTime.now());
        String text = ClientView.name + "/" + chatSend.getText() + "/" + time;
        ClientView.getInstance().serverCon.service.chatSend(chatId, text);
        chatSend.setText("");
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        chatName = new JLabel();
        backBtn = new JButton();
        infor = new JLabel();
        separator1 = new JSeparator();
        scrollPane1 = new JScrollPane();
        chatScreen = new JPanel();
        panel2 = new JPanel();
        chatSend = new JTextField();
        label2 = new JLabel();

        //======== this ========
        setBackground(Color.white);
        setLayout(null);

        //======== panel1 ========
        {
            panel1.setBackground(Color.white);
            panel1.setLayout(null);

            //---- chatName ----
            chatName.setText("NameHere");
            chatName.setFont(new Font("Franklin Gothic Demi Cond", Font.PLAIN, 27));
            chatName.setHorizontalTextPosition(SwingConstants.RIGHT);
            panel1.add(chatName);
            chatName.setBounds(80, 20, 178, chatName.getPreferredSize().height);

            //---- backBtn ----
            backBtn.setIcon(new ImageIcon(getClass().getResource("/back-icon.png")));
            backBtn.setSelectedIcon(new ImageIcon(getClass().getResource("/back-icon1.png")));
            backBtn.setBackground(Color.white);
            backBtn.setBorder(null);
            backBtn.setBorderPainted(false);
            backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            backBtn.setContentAreaFilled(false);
            backBtn.addActionListener(e -> backBtn(e));
            panel1.add(backBtn);
            backBtn.setBounds(10, 5, 50, 56);

            //---- infor ----
            infor.setIcon(new ImageIcon(getClass().getResource("/infor-icon.png")));
            panel1.add(infor);
            infor.setBounds(new Rectangle(new Point(445, 15), infor.getPreferredSize()));
            panel1.add(separator1);
            separator1.setBounds(0, 65, 500, 13);

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
        add(panel1);
        panel1.setBounds(0, 0, 500, 70);

        //======== scrollPane1 ========
        {
            scrollPane1.setBackground(Color.white);
            scrollPane1.setBorder(null);

            //======== chatScreen ========
            {
                chatScreen.setBackground(Color.white);
                chatScreen.setLayout(null);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < chatScreen.getComponentCount(); i++) {
                        Rectangle bounds = chatScreen.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = chatScreen.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    chatScreen.setMinimumSize(preferredSize);
                    chatScreen.setPreferredSize(preferredSize);
                }
            }
            scrollPane1.setViewportView(chatScreen);
        }
        add(scrollPane1);
        scrollPane1.setBounds(0, 70, 520, 355);

        //======== panel2 ========
        {
            panel2.setBackground(Color.white);
            panel2.setBorder(null);
            panel2.setLayout(null);

            //---- chatSend ----
            chatSend.setBorder(new EtchedBorder());
            chatSend.addActionListener(e -> chatSend(e));
            panel2.add(chatSend);
            chatSend.setBounds(15, 15, 410, 45);

            //---- label2 ----
            label2.setIcon(new ImageIcon(getClass().getResource("/send-icon.png")));
            label2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            panel2.add(label2);
            label2.setBounds(new Rectangle(new Point(440, 15), label2.getPreferredSize()));

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel2.getComponentCount(); i++) {
                    Rectangle bounds = panel2.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel2.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel2.setMinimumSize(preferredSize);
                panel2.setPreferredSize(preferredSize);
            }
        }
        add(panel2);
        panel2.setBounds(0, 425, 500, 75);

        setPreferredSize(new Dimension(500, 500));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JLabel chatName;
    private JButton backBtn;
    private JLabel infor;
    private JSeparator separator1;
    private JScrollPane scrollPane1;
    private JPanel chatScreen;
    private JPanel panel2;
    private JTextField chatSend;
    private JLabel label2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public void refresh() {
        update();
        JScrollBar vertical = scrollPane1.getVerticalScrollBar();
        vertical = scrollPane1.getVerticalScrollBar();
        try {
            Thread.sleep(70);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println(vertical.getMaximum());
        vertical.setValue(contentChats.size() * 150);
        vertical.setValue(contentChats.size() * 150);
//        repaint();
    }



    class ContentChat extends JPanel {
        String text;
        String timeChat;
        String playerName;
        JLabel name;
        JLabel textChat;
        JLabel time;

        public ContentChat(String playerName, String text1, String timeChat1) {
            textChat = new JLabel();
            time = new JLabel();
            name = new JLabel();
            this.playerName = "  " + playerName;
            this.text = "    " + text1 + "    ";
            this.timeChat = "  " +  timeChat1 + "  ";

            this.setBackground(Color.white);
            this.setLayout(new BorderLayout());

            if (playerName.equals(ClientView.name)) {
                setRight();
            } else {
                //name
                name.setText(this.playerName);
                this.add(name, BorderLayout.NORTH);

                //---- textChat ----
                textChat.setText(text);
                textChat.setOpaque(true);
                textChat.setBackground(new Color(200, 255, 230));
                this.add(textChat, BorderLayout.WEST);

                //---- time ----
                time.setText(timeChat);
                this.add(time, BorderLayout.SOUTH);
            }
        }

        private void setRight() {
            //name
            name.setText("You  ");
            this.add(name, BorderLayout.NORTH);
            name.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

            //---- textChat ----
            textChat.setText(text);
            textChat.setOpaque(true);
            textChat.setBackground(new Color(146, 204, 255));
            this.add(textChat, BorderLayout.EAST);

            //---- time ----
            time.setText(timeChat);
            this.add(time, BorderLayout.SOUTH);
            time.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }

    }
}
