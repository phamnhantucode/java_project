package Network;

//import Client.ServerCon;
import Server.ClientCon;
import Server.Server;

import java.io.IOException;
import java.util.ArrayList;

public class Sender implements Runnable {
    private final ArrayList<Message> sendingMessage = new ArrayList();
    private ClientCon clientCon;
//    private ServerCon serverCon;
    private boolean flag;

    public Sender(ClientCon clientCon) {
        flag = true;
        this.clientCon = clientCon;
    }

//    public Sender(ServerCon serverCon) {
//        flag = false;
//        this.serverCon = serverCon;
//    }

    public void AddMessage(Message message) {
        this.sendingMessage.add(message);
    }

    public void run() {
        while(true) {
            try {
//                if (ClientEntry.this.isConnected()) {
                    while(this.sendingMessage.size() > 0) {
                        Message m = (Message)this.sendingMessage.get(0);
//                        ClientEntry.this.doSendMessage(m);
//                        if (flag) {
                            clientCon.doSendMessage(m);
//                        }
//                        else {
//                            serverCon.doSendMessage(m);
//                        }
                        this.sendingMessage.remove(0);
                    }

                    try {
                        Thread.sleep(10L);
                    } catch (InterruptedException var2) {
                    }
                    continue;
//                }
            } catch (Exception e) {

                e.printStackTrace();
            }

            return;
        }
    }
}
