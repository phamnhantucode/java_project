package Client;

import Network.Message;
import Network.MessageCollect;
import Network.Sender;
//import Server.MessageHandle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerCon {
	boolean isConnect;
    public Socket sc;
    public DataInputStream dis;
    public DataOutputStream dos;
    public MessHandle messHandle;
    public Sender sender;
    private MessageCollect messageCollect;
    public Servicee service;


    public ServerCon() {
        
    }
    
    public void connect() {
    	try {
            if (!isConnect) {
            	sc = new Socket("localhost", 9999);
                if (sc != null) { //check socket is connected to server

                    dis = new DataInputStream(sc.getInputStream());
                    dos = new DataOutputStream(sc.getOutputStream());
                }
                messHandle = new MessHandle(this);
                this.messageCollect = new MessageCollect(this);
                Thread messCollectThread = new Thread(messageCollect);
                messCollectThread.start();
                sender = new Sender(this);
                Thread sendThread = new Thread(sender);
                sendThread.start();
                service = new Servicee(this);
            }
            isConnect = true;
        } catch (IOException e) {
            e.printStackTrace();
            isConnect = false;
        }
    }

    public void sendMessage(Message message) {
        this.sender.AddMessage(message);
    }

    public void doSendMessage(Message m) {
        try {
            this.dos.writeByte(m.getCommand());
            byte[] data = m.getData();
            int size = data.length;
            this.dos.writeInt(size);
            this.dos.write(data);
            this.dos.flush();
//            Thread.sleep(50);
            m.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
