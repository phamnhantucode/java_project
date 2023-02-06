package Client;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class LoginGUI extends JFrame implements ActionListener{

	JLabel lbLogin;
	JLabel lbUser;
	 JLabel lbPass;
	 JTextField txUser;
	 JPasswordField txPass;
	 JButton btnLogin;
	 JButton btnClear;
	 Socket sk;
	public LoginGUI(){
		try {
			sk = new Socket("localhost",5678);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setTitle("Grocery management");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 210);
		
		lbLogin = new JLabel("LOGIN");
		lbLogin.setBounds(100, 10, 100, 20);
		lbLogin.setFont(new Font("MV Boli", Font.BOLD, 20));
		lbLogin.setForeground(Color.BLUE);
		
		lbUser = new JLabel("Account ID:");
		lbUser.setBounds(10, 45, 100, 20);
		lbUser.setFont(new Font("Consolas", Font.ITALIC, 15));
		
		lbPass = new JLabel("Password:");
		lbPass.setBounds(10, 85, 100, 20);
		lbPass.setFont(new Font("Consolas", Font.ITALIC, 15));
		
		btnClear = new JButton("Clear");
		btnClear.setBounds(185, 125, 75, 30);
		btnClear.setFocusable(false);
		btnClear.setBackground(Color.white);
		btnClear.addActionListener(this);
		
		btnLogin = new JButton("Login");
		btnLogin.setBounds(100, 125, 75, 30);
		btnLogin.setFocusable(false);
		btnLogin.addActionListener(this);
		btnLogin.setBackground(Color.white);
		
		
		txUser = new JTextField(20);
		txUser.setBounds(90, 41, 180, 30);
		txUser.setFont(new Font("Consolas", Font.PLAIN, 15));
	
		txPass = new JPasswordField();
		txPass.setBounds(90, 79, 180, 30);
		txPass.setFont(new Font("Consolas", Font.ITALIC, 15));
		
		
		this.add(lbLogin);
		this.add(lbUser);
		this.add(lbPass);
		this.add(btnLogin);
		this.add(btnClear);
		this.add(txUser);
		this.add(txPass);
		this.setLayout(null);
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnLogin) {
			try {
				if (txUser.getText().equals("") || txPass.getText().equals(""))
					JOptionPane.showMessageDialog(this, "Please enter enough information");
				else {

						BufferedReader stream_in = new BufferedReader(new InputStreamReader(sk.getInputStream()));
				        PrintWriter stream_out = new PrintWriter(sk.getOutputStream(), true);
				       
				        //Tạo luồng nhận dữ liệu từ bàn phím
				      
				       //Nhập chuổi từ bàn phím
				      //Gửi dữ liệu đến server
				       stream_out.println("login" +"//"+ txUser.getText()+ "//" + txPass.getText());
				       stream_out.flush();
				     
				      //nhận dữ liệu từ server và hiển thị lên màn hình
				       String st = stream_in.readLine(); 
				       String[] result = st.split("//");
				       if (result[0].equals("0")) {
				    	   JOptionPane.showMessageDialog(null, result[1]);

				       } else {
				    	   ATMClient cl = new ATMClient();
				    	   cl.setAccount(new Account(result[1], Integer.parseInt(result[2])));
						   cl.setSocket(sk);
						   this.hide();

				       }
				       
				         
				        }
					
					
				}
			 catch (Exception e1) {
				// TODO Auto-generated catch block
				 JOptionPane.showMessageDialog(this, "Connect fail");
			}
		}
		if (e.getSource() == btnClear) {
			txUser.setText("");
			txPass.setText("");
		}
		
	}
	public static void main(String[] args) {
		new LoginGUI();
	}
}