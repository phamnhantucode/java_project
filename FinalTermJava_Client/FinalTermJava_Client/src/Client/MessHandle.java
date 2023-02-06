package Client;

import Client.GameTaiXiu.PlayScreen;
import Client.TankAZ.AZManager;
import Client.TankAZ.AZSetUp;
import Client.TankAZ.Bullet;
import Client.TankAZ.RoomAZ;
import Network.Message;
//import Server.ClientCon;
//import Server.GameTaiXiu;

import java.io.IOException;

public class MessHandle {
    private ServerCon serverCon;
    public MessHandle(ServerCon serverCon) {
        this.serverCon = serverCon;
    }

    public void onMessage(Message message) {
        try {
            if (message != null) {
//                System.out.println("Recive command: " + message.getCommand());
                switch (message.getCommand()) {
                    case 0: { //Account
                        this.handleAccount(message);
                        break;
                    }
                    case 1: { //action view
                        this.handleAction(message);
                        break;
                    }
                    case 2: {
                        this.handleInfor(message);
                        break;
                    }
                    case 3: { //game taixiu
                        this.handleTaiXiu(message);
                        break;
                    }
                    case 4: {
                        handleTankAZ(message);
                        break;
                    }
                    default:
                        System.out.println("Recive command: " + message.getCommand());
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleTankAZ(Message m) throws IOException {
        byte command = m.reader().readByte();
        switch (command) {
            case 1:
                RoomAZ.instance.setListRoom(m);
                break;
            case 2:
                RoomAZ.instance.enterRoom(m);
                break;
            case 3:
                RoomAZ.azSetUp.manager.setPosEnemy(m);
                break;
            case 4:
                RoomAZ.azSetUp.manager.setPosPlayer(m);
                break;
            case 5:
                RoomAZ.azSetUp.manager.drawString(m.reader().readUTF());
                break;
            case 6:
                AZManager.bullets.add(new Bullet(m.reader().readInt(), m.reader().readInt(), m.reader().readInt(), false));
                break;
        }
    }

    private void handleTaiXiu(Message m) throws IOException {
        byte command = m.reader().readByte();
//        System.out.println("Recive sub command: "  + command);
        switch (command) {
            case 0: //notification
                PlayScreen.instance.setNotif(m.reader().readUTF());
                break;
            case 1:
                PlayScreen.instance.setTimeCount(m.reader().readUTF());
                break;
            case 2:
                PlayScreen.instance.setDice(m);
                break;
            case 3:
                PlayScreen.instance.setMoney(m);
                break;
        }
    }

    private void handleInfor(Message m) throws IOException {
        byte command = m.reader().readByte();
        System.out.println("Recive sub command: "  + command);
        switch (command) {
            case 1: //infor user
                    ClientView.setInforUser(m);
                break;
            case 2: //list friends
                friendPanel.instance.setInforList(m);
                break;
            case 3: //list players
                friendPanel.instance.setInforList(m);
                break;
            case 4: //list find
                friendPanel.instance.setInforList(m);
                break;
            case 5:
                ChatPanel.instance.setChatList(m);
                break;
            case 6:
                ClientView.tmp = m.reader().readUTF();
//                System.out.println(ClientView.tmp);
                break;
            case 7:
                UserPanel.instance.setAvatar(m);
                break;
            case 8:
                ClientView.getInstance().showInfor(m);
                break;
        }
    }

    private void handleAction(Message m) throws IOException {
        byte command = m.reader().readByte();
        switch (command) {
            case 0:
                ClientView.getInstance().startLoad();
                break;
            case 1:
                String text = m.reader().readUTF();
                ClientView.getInstance().startOKDialog(text);
                break;
            case 2:
                ChatPanel.instance.startChat(m.reader().readInt());
                break;
            case 3:
                ChatPanel.instance.updateChat(m);
                break;
        }
    }

    public void handleAccount(Message m) throws IOException {
        byte command = m.reader().readByte();
        switch (command) {
            case -1:
                ClientView.getInstance().loginPanel.setPassTf("");
                break;
        }
    }

}
