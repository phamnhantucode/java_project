package Client.TankAZ;

import Client.ClientView;
//import Server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameAZScreen {
    public static final int TOP = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 3;
    public static final int LEFT = 4;

    private int width, height;

    public static JFrame frame = new JFrame();
    public static Canvas canvas;
    public static JPanel panel;
    private JLabel label;
    private JLabel label1;
    private AZSetUp azSetUp;
    private  JButton readyBtn;

    public GameAZScreen(int width, int height, AZSetUp azSetUp) {
        this.width = width;
        this.height = height;
        this.azSetUp = azSetUp;
        init();
    }

    private void init() {
        frame.setSize(width, height + 40);
        frame.setTitle(ClientView.name);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ClientView.getInstance().serverCon.service.outRoomAZ(azSetUp.name);
                azSetUp.stop();
                RoomAZ.azSetUp = null;
                
                frame.dispose();
                ClientView.getInstance().serverCon.service.getListRoomAZ();

            }
        });
        frame.setResizable(false);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(500, 500));
        canvas.setFocusable(false);

        frame.add(canvas);
        canvas.setBounds(40,40,500,500);

        readyBtn = new JButton("Ready");
        readyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientView.getInstance().serverCon.service.readyAZ();
                readyBtn.setEnabled(false);
            }
        });
        frame.add(readyBtn);
        readyBtn.setBounds(250, 540, 100, 40);

        label = new JLabel();
        label.setIcon(new ImageIcon(getClass().getResource("/bullet1.png")));
        label.setText(String.valueOf(RoomAZ.azSetUp.manager.player.stack));
        label.setFont(new Font("Aria", Font.BOLD, 16));
        frame.add(label);
        label.setBounds(450, 0, 80, 40);
        
        label1 = new JLabel();
        label1.setIcon(new ImageIcon(getClass().getResource("/coin-icon.png")));
        label1.setFont(new Font("Aria", Font.BOLD, 16));
        label1.setText(String.valueOf(azSetUp.bet));
        frame.add(label1);
        label1.setBounds(250, 0, 80, 40);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setStackBullet(String valueOf) {
        label.setText(valueOf);
//        label.setBounds(450, 0, 80, 40);
//        label.repaint();
    }

    public void setEnableBtnReady() {
        readyBtn.setEnabled(true);
    }
}
