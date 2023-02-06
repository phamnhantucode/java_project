package Network;

import Client.Client;
//import Server.*;
import Client.ServerCon;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MessageCollect implements Runnable{

    private DataInputStream dis;
    private DataOutputStream dos;
//    private ClientCon clientCon;
    private ServerCon serverCon;
    private boolean flag;
//
//    public MessageCollect(ClientCon clientCon) {
//        this.clientCon = clientCon;
//        this.dis = clientCon.dis;
//        this.dos = clientCon.dos;
//        this.serverCon = null;
//        flag = true;//Message collect is on server
//    }

    public MessageCollect(ServerCon serverCon) {
        this.serverCon  = serverCon;
        this.dis = serverCon.dis;
        this.dos = serverCon.dos;
//        this.clientCon = null;
        flag = false; //Message collect is on client
    }


    @Override
    public void run() {
        while (true) {
            Message message = this.readMessage();
            if (message != null) {
//                if (flag) {
//                    clientCon.messageHandle.onMessage(message);
//                    message.cleanup();
//                }
//                else {
                    serverCon.messHandle.onMessage(message);
                    message.cleanup();
//                }
            }
            try {
                Thread.sleep(10);
//                if (clientCon != null)
//
//                if (clientCon.dis.readLine() == null) {
//                    Server.instance.disconnect(clientCon);
//                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Message readMessage() {
        try {
            byte command = dis.readByte();
            int size = dis.readInt();
//            System.out.println(size);
            byte[] data = new byte[size];
            int len = 0;
            int byteRead = 0;
            while(len != -1 && byteRead < size) {
                len = dis.read(data, byteRead, size - byteRead);
                if(len > 0)
                    byteRead += len;
            }
//            dis.read(data);
            Message m = new Message(command, data);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
//            clientCon.close();

        }
        return null;
    }
}
