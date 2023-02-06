package Client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;

public class ATMClient extends JFrame implements ActionListener{
	
	Socket sk;
	Account acc;
	JLabel lb = new JLabel();
	JButton checkBtn = new JButton("Check");
	JButton transferBtn = new JButton("Transfer");
	JButton getBtn = new JButton("Rút tiền");
	JButton pushBtn = new JButton("Nạp tiền");
	public ATMClient() {
		
		setSize(300,200);
		addWindowListener((WindowListener) new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
            }
        });
		lb.setBounds(20, 20, 200, 30);
		lb.setBackground(Color.white);

		add(lb);
		checkBtn.setBounds(10,60,100,25);
		add(checkBtn);
		checkBtn.addActionListener(this);
		transferBtn.setBounds(110,60,100,25);
		add(transferBtn);
		getBtn.setBounds(10,90,100,25);
		add(getBtn);
		getBtn.addActionListener(this);
		pushBtn.setBounds(110,90,100,25);
		add(pushBtn);
		setLayout(null);
		setVisible(true);
		setResizable(false);
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {

			BufferedReader stream_in = new BufferedReader(new InputStreamReader(sk.getInputStream()));
			PrintWriter stream_out = new PrintWriter(sk.getOutputStream(), true);
			if (e.getSource().equals(checkBtn)) {
				JOptionPane.showMessageDialog(null, String.valueOf(acc.getBalance()));
			}
			if (e.getSource().equals(getBtn)) {
				String i = JOptionPane.showInputDialog("Nhập số tiền muốn rút");
				stream_out.println("get" +"//"+ acc.getID() + "//" + i);
				stream_out.flush();
			}
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void setAccount(Account acc) {
		this.acc = acc;
		lb.setText("AccountID: " + acc.getID());
	}
	public void setSocket(Socket sk) {
		this.sk = sk;
	}


}
