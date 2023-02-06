package Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import Connect.Connect;


public class ATMServer {
	String text;
	ServerSocket sv;
	Connection conn;
	public ATMServer() {
		try {
			sv = new ServerSocket(5678);
			System.out.println("Server is starting...");
            
	        Socket socket = sv.accept();   
	        BufferedReader stream_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        PrintWriter stream_out = new PrintWriter(socket.getOutputStream(), true);
	        while(true)
            {
                text = stream_in.readLine();
                System.out.println("Received: " + text);
                
                String[] accounts = text.split("//");
                conn = Connect.connect();
                if (accounts[0].equals("login")) {
					if (conn == null){
						stream_out.println("0" + "//" + "Error system");
						stream_out.flush();
					}
					else {
						PreparedStatement stm = conn.prepareStatement("SELECT * FROM Account WHERE ID = ? and Pass= ?");
						stm.setString(1, accounts[1]);
						stm.setString(2, accounts[2]);
						ResultSet rs = stm.executeQuery();
						if (rs.next()) {
							stream_out.println("1" + "//" + accounts[1] + "//" + rs.getString("Balance"));
							stream_out.flush();
						}
						else
							stream_out.println("0" + "//" + "Wrong pass or ID");
						stream_out.flush();
					}
				}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new ATMServer();
	}
}
