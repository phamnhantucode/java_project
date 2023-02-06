package Server;

import Network.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

public class MessageHandle {
    private ClientCon clientCon;
    public MessageHandle(ClientCon clientCon) {
        this.clientCon = clientCon;
    }

    public void onMessage(Message message) {
        if (message != null ) {
            System.out.println("Recive command: " + message.getCommand());
            try {
                switch (message.getCommand()) {
                    case 0:
                        accountHandle(message);

                        break;
                    case 1:
                        listHandle(message);
                        break;
                    case 2:
                        inforUserHandle(message);
                        break;
                    case 3:
                        actionHandle(message);
                        break;
                    case 4:
                        gameTaiXiuHandle(message);
                        break;
                    case 5:
                        gameTankAZHandle(message);
                        break;
                    default:
//                        System.out.println(message.getCommand());
                        break;
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void gameTankAZHandle(Message m) throws IOException {
        byte command = m.reader().readByte();
        switch (command) {
            case 1:
                clientCon.service.sendListRoom();
                break;
            case 2:
                clientCon.service.sendRoomSearch(m.reader().readUTF());
                break;
            case 3:
                clientCon.service.addNewRoom(m);
                break;
            case 4:
                Server.instance.outRoomAZ(m);
                break;
            case 5:
                clientCon.roomAZService.sendPos(clientCon, m);
                break;
            case 6:
                Server.instance.enterRoomAZ(m.reader().readUTF(), clientCon);
                break;
            case 7:
                clientCon.roomAZService.addBullet(clientCon, m);
                break;
            case 8:
                clientCon.roomAZService.win(clientCon);
                break;
            case 9:
                clientCon.roomAZService.ready(clientCon);
                break;
        }

    }

    private void gameTaiXiuHandle(Message m) throws Exception {
        byte commnad = m.reader().readByte();
        switch (commnad) {
            case 1:
                Server.instance.gameTaiXiu.bet(m, this.clientCon);
                break;
        }

    }

    private void actionHandle(Message message) throws IOException {
        byte commnad = message.reader().readByte();
        switch (commnad) {
            case 1:
//                System.out.println("start chat");
                clientCon.startChat(message.reader().readInt());
                break;
            case 2:
                clientCon.chatSend(message);
                break;
            case 3:
//                clientCon.service.sendName(message.reader().readInt());
                break;
            case 4:
                clientCon.addFriend(message.reader().readInt());
                break;
            case 5:
                clientCon.removeFriend(message.reader().readInt());
                break;
            case 6:
                clientCon.service.showInforUser(message.reader().readInt());
                break;
            case 7:
            	clientCon.service.sendListChat();
            	Server.instance.sendListChat(message.reader().readInt());
            	break;

        }
    }

    private void inforUserHandle(Message message) throws IOException, SQLException {
        byte command = message.reader().readByte();
        switch (command) {
            case 0: {
                clientCon.updateUser();
                break;
            }
            case 1:
                clientCon.reName(message);
                break;
        }
    }

    private void listHandle(Message message) throws IOException {
        byte command = message.reader().readByte();
        switch (command) {
            case 1:
                clientCon.service.sendListFriends();
                break;
            case 2:
                clientCon.service.sendListPlayers();
                break;
            case 3:
                clientCon.service.sendListFinds(message.reader().readUTF());
                break;
            case 4:
                clientCon.service.sendListChat();
                break;
        }
    }

    private void accountHandle(Message message) throws IOException {
        byte command = message.reader().readByte();
        switch (command) {
            case 0: clientCon.login(message);
                    break;
            case 1: clientCon.register(message);
                    break;
            case 2:
                clientCon.setAvatar(message);
                break;

        }
    }

}
