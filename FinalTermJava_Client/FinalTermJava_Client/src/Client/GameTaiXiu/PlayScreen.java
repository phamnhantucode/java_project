/*
 * Created by JFormDesigner on Thu Jun 16 20:15:40 ICT 2022
 */

package Client.GameTaiXiu;

import Client.ClientView;
import Client.MainScreen;
import Client.ServerCon;
import Network.Message;
//import Server.Server;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author unknown
 */
public class PlayScreen extends JPanel {
    public JFrame clientView;
    private int startX;
    private int startY;
    private int curX;
    private int curY;
    private boolean inDrag;
    private boolean isRoll = false;
    private ServerCon serverCon;
    public static PlayScreen instance = new PlayScreen(ClientView.getInstance().serverCon);
    private Pattern pattern = Pattern.compile("[0-9]+");
    private int sum;
//    public int timeCount = 0;

    public PlayScreen(ServerCon serverCon) {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    switch (notification.getText()) {
                        case "Starting!": {
                        	isRoll = false;
                            betTai.setEnabled(true);
                            betXiu.setEnabled(true);
                            winTai.setVisible(false);
                            winXiu.setVisible(false);
                            bowl.setVisible(true);
                            break;
                        }
                        case "See result!": {
                            betTai.setEnabled(false);
                            betXiu.setEnabled(false);
                            break;
                        }
                        case "Ready!" : {
                            bowl.setVisible(false);
                            if (sum >= 11) {
                                winTai.setVisible(true);
                            } else {
                                winXiu.setVisible(true);
                            }
                            break;
                        }
                    }
                }

            }
        }.start();
        this.serverCon = serverCon;
        initComponents();
    }

    private void bowlMouseDragged(MouseEvent e) {
        // TODO add your code here
//        bowl.setBounds(new Rectangle(e.getPoint(), bowl.getPreferredSize()));
//        Point p = e.getPoint();
        Point p = this.bowl.getLocation();
//        System.out.println("Drag :" + p);
//        this.curX = p.x;
        if ((p.x > 850 || p.x < 250) || (p.y < -150 || p.y > 430)) {
        	if (!isRoll) {
        		Thread t = new Thread() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 20; i++) {
                            
                            try {
                                Thread.sleep(50);
                                Random r = new Random();
                                changeDice(r.nextInt(6) + 1, dice1);
                                changeDice(r.nextInt(6) + 1, dice2);
                                changeDice(r.nextInt(6) + 1, dice3);
                         
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                          
                        }
                        changeDice(dice11, dice1);
                        changeDice(dice22, dice2);
                        changeDice(dice33, dice3);
                        isRoll = true;
                        if (sum >= 11) {
                            winTai.setVisible(true);
                        } else {
                            winXiu.setVisible(true);
                        }
                    }
                };
                t.start();
                
        	}
            
            serverCon.service.getInforPlayer();
        }
//        this.curY = p.y;
        if (notification.getText().equals("See result!")){
            keotha(e);
        }

    }

    private void keotha(MouseEvent e) {
        Point p = new Point(e.getXOnScreen() - startX, e.getYOnScreen() - startY - 20);
        bowl.setLocation(p);
        bowl.repaint();
    }

    private void bowlMousePressed(MouseEvent e) {
        // TODO add your code here
        Point p = e.getPoint();
//        System.out.println("Press: " + p);
        this.startX = p.x;
        this.startY = p.y;
    }

    private void bowlMouseReleased(MouseEvent e) {
        // TODO add your code here
//        this.inDrag = false;
        bowl.setBounds(new Rectangle(new Point(545, 135), bowl.getPreferredSize()));
        bowl.repaint();
    }

    private void backBtn(ActionEvent e) {
        // TODO add your code here
        ClientView.getInstance().setContentPane(MainScreen.instance);
        ClientView.getInstance().setSize(MainScreen.instance.getPreferredSize().width, MainScreen.instance.getPreferredSize().height + 20 );
        ClientView.getInstance().invalidate();
        ClientView.getInstance().validate();
        ClientView.getInstance().repaint();
    }

    private void betTai(ActionEvent e) {
        // TODO add your code here
        String text = moneyTai.getText();
        if (text.equals("")) {
            JOptionPane.showMessageDialog(this, "Input empty!");
            return;
        }
        Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) {
            if (text.length() > 9) {
                JOptionPane.showMessageDialog(this, "Not more than one million coin!");
                return;
            }
            serverCon.service.sendBet(0, Integer.parseInt(text));
            moneyTai.setText("");
//            .out.println(Integer.parseInt(text));
        }
    }

    private void betXiu(ActionEvent e) {
        // TODO add your code here
        String text = moneyXiu.getText();
        if (text.equals("")) {
            JOptionPane.showMessageDialog(this, "Input empty!");
//            System.out.println("hehe");
            return;
        }
        Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) {
//            System.out.println("hehe");
            if (text.length() > 9) {
                JOptionPane.showMessageDialog(this, "Not more than one million coin!");
                return;
            }
            serverCon.service.sendBet(1, Integer.parseInt(text));
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        bowl = new JLabel();
        winTai = new JLabel();
        winXiu = new JLabel();
        backBtn = new JButton();
        betTai = new JButton();
        betXiu = new JButton();
        time = new JLabel();
        dice1 = new JLabel();
        dice2 = new JLabel();
        dice3 = new JLabel();
        moneyXiu = new JTextField();
        moneyTai = new JTextField();
        sumTai = new JLabel();
        sumXiu = new JLabel();
        notification = new JLabel();
        label1 = new JLabel();
        background = new JLabel();

        //======== this ========
        setBackground(Color.white);
        setLayout(null);

        //---- bowl ----
        bowl.setIcon(new ImageIcon(getClass().getResource("/tai-xiu/bat.png")));
        bowl.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                bowlMouseDragged(e);
            }
        });
        bowl.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                bowlMousePressed(e);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                bowlMouseReleased(e);
            }
        });
        add(bowl);
        bowl.setBounds(new Rectangle(new Point(540, 135), bowl.getPreferredSize()));

        //---- winTai ----
        winTai.setIcon(new ImageIcon(getClass().getResource("/tai-xiu/winTai.png")));
        winTai.setVisible(false);
        add(winTai);
        winTai.setBounds(new Rectangle(new Point(140, 40), winTai.getPreferredSize()));

        //---- winXiu ----
        winXiu.setIcon(new ImageIcon(getClass().getResource("/tai-xiu/winXiu.png")));
        winXiu.setVisible(false);
        add(winXiu);
        winXiu.setBounds(1035, 40, 300, 300);

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
        backBtn.setBounds(20, 15, 50, 50);

        //---- betTai ----
        betTai.setText("Place a bet");
        betTai.setFocusable(false);
        betTai.addActionListener(e -> betTai(e));
        add(betTai);
        betTai.setBounds(350, 420, 110, 40);

        //---- betXiu ----
        betXiu.setText("Place a bet");
        betXiu.setFocusable(false);
        betXiu.addActionListener(e -> betXiu(e));
        add(betXiu);
        betXiu.setBounds(1020, 420, 110, 40);

        //---- time ----
        time.setText("05:00");
        time.setForeground(Color.white);
        time.setFont(new Font("Segoe UI", Font.BOLD, 41));
        time.setHorizontalAlignment(SwingConstants.CENTER);
        add(time);
        time.setBounds(new Rectangle(new Point(695, 595), time.getPreferredSize()));

        //---- dice1 ----
        dice1.setIcon(new ImageIcon(getClass().getResource("/tai-xiu/mot.png")));
        add(dice1);
        dice1.setBounds(new Rectangle(new Point(655, 230), dice1.getPreferredSize()));

        //---- dice2 ----
        dice2.setIcon(new ImageIcon(getClass().getResource("/tai-xiu/hai.png")));
        add(dice2);
        dice2.setBounds(785, 310, 70, 70);

        //---- dice3 ----
        dice3.setIcon(new ImageIcon(getClass().getResource("/tai-xiu/ba.png")));
        add(dice3);
        dice3.setBounds(685, 360, 70, 70);

        //---- moneyXiu ----
        moneyXiu.setBorder(new BevelBorder(BevelBorder.LOWERED));
        moneyXiu.setBackground(Color.white);
        moneyXiu.setFont(moneyXiu.getFont().deriveFont(moneyXiu.getFont().getSize() + 3f));
        moneyXiu.setHorizontalAlignment(SwingConstants.CENTER);
        add(moneyXiu);
        moneyXiu.setBounds(1150, 422, 200, 35);

        //---- moneyTai ----
        moneyTai.setBorder(new BevelBorder(BevelBorder.LOWERED));
        moneyTai.setBackground(Color.white);
        moneyTai.setHorizontalAlignment(SwingConstants.CENTER);
        moneyTai.setFont(moneyTai.getFont().deriveFont(moneyTai.getFont().getSize() + 3f));
        add(moneyTai);
        moneyTai.setBounds(133, 422, 200, 35);

        //---- sumTai ----
        sumTai.setHorizontalAlignment(SwingConstants.CENTER);
        sumTai.setFont(new Font("Arial", Font.BOLD, 30));
        sumTai.setForeground(new Color(51, 102, 255));
        add(sumTai);
        sumTai.setBounds(136, 320, 315, 60);

        //---- sumXiu ----
        sumXiu.setFont(new Font("Arial", Font.BOLD, 30));
        sumXiu.setHorizontalAlignment(SwingConstants.CENTER);
        sumXiu.setForeground(new Color(51, 102, 255));
        add(sumXiu);
        sumXiu.setBounds(1030, 320, 315, 60);

        //---- notification ----
        notification.setHorizontalAlignment(SwingConstants.CENTER);
        notification.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        add(notification);
        notification.setBounds(320, 30, 835, 60);

        //---- label1 ----
        label1.setIcon(new ImageIcon(getClass().getResource("/tai-xiu/dragon.png")));
        add(label1);
        label1.setBounds(new Rectangle(new Point(585, 190), label1.getPreferredSize()));

        //---- background ----
        background.setIcon(new ImageIcon(getClass().getResource("/tai-xiu/background.gif")));
        add(background);
        background.setBounds(0, 0, 1500, 700);

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
    private JLabel bowl;
    private JLabel winTai;
    private JLabel winXiu;
    private JButton backBtn;
    private JButton betTai;
    private JButton betXiu;
    private JLabel time;
    private JLabel dice1;
    private JLabel dice2;
    private JLabel dice3;
    private JTextField moneyXiu;
    private JTextField moneyTai;
    private JLabel sumTai;
    private JLabel sumXiu;
    private JLabel notification;
    private JLabel label1;
    private JLabel background;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public void setNotif(String readUTF) {
        this.notification.setText(readUTF);
    }

    public void setTimeCount(String time) {
        this.time.setText(time);
    }

    public void setMoney(Message m) {
        try {
            this.sumTai.setText(ClientView.getDotNumber(m.reader().readInt()));
            this.sumXiu.setText(ClientView.getDotNumber(m.reader().readInt()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    int dice11, dice22, dice33;
    public void setDice(Message m) {
        try {
            dice11 = m.reader().readInt();
            dice22 = m.reader().readInt();
            dice33 = m.reader().readInt();
            sum = dice11 + dice22 + dice33;
            changeDice(dice11, this.dice1);
            changeDice(dice22, this.dice2);
            changeDice(dice33, this.dice3);
//            System.out.println(dice1 + " " + dice2 + " " + dice3);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeDice(int dice, JLabel diceImg) {
        switch (dice) {
            case 1:
                diceImg.setIcon(new ImageIcon(getClass().getResource("/tai-xiu/mot.png")));
                break;
            case 2:
                diceImg.setIcon(new ImageIcon(getClass().getResource("/tai-xiu/hai.png")));
                break;
            case 3:
                diceImg.setIcon(new ImageIcon(getClass().getResource("/tai-xiu/ba.png")));
                break;
            case 4:
                diceImg.setIcon(new ImageIcon(getClass().getResource("/tai-xiu/bon.png")));
                break;
            case 5:
                diceImg.setIcon(new ImageIcon(getClass().getResource("/tai-xiu/nam.png")));
                break;
            case 6:
                diceImg.setIcon(new ImageIcon(getClass().getResource("/tai-xiu/sau.png")));
                break;
        }
        diceImg.repaint();
    }


//    public static void main(String[] args) {
//        PlayScreen playScreen = new PlayScreen();
//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setContentPane(playScreen);
//        frame.pack();
//        frame.setVisible(true);
//    }
}
