package Client;

import Network.Message;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.rmi.server.ExportException;

public class Servicee {
    private ServerCon serverCon;
    public Servicee(ServerCon serverCon) {
        this.serverCon = serverCon;
    }

    public void login(String user, String pass) {
        try {
            Message m = new Message(0);
            m.writer().writeByte(0);
            m.writer().writeUTF(user);
            m.writer().writeUTF(pass);
            m.writer().flush();
            serverCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void register(String user, String pass) {
        try {
            Message m = new Message(0);
            m.writer().writeByte(1);
            m.writer().writeUTF(user);
            m.writer().writeUTF(pass);
            m.writer().flush();
            serverCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getListFriends() {
        try {
            Message m = new Message(1); //list
            m.writer().writeByte(1); //list friends
            m.writer().flush();
            serverCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getListPlayers() {
        try {
            Message m = new Message(1); //list
            m.writer().writeByte(2); //list players
            m.writer().flush();
            serverCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getListFind(String text) {
        try {
            Message m = new Message(1); //list
            m.writer().writeByte(3); //list finds
            m.writer().writeUTF(text);
            m.writer().flush();
            serverCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getInforPlayer() {
        try {
            Message m = new Message(2);
            m.writer().writeByte(0);
            m.writer().flush();
            serverCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void reName(String text) {
        try {
            Message m = new Message(2); //change infor user
            m.writer().writeByte(1); //rename
            m.writer().writeUTF(text);
            m.writer().flush();
            serverCon.sendMessage(m);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getListChat() {
        try {
            Message m  = new Message(1);//list
            m.writer().writeByte(4);
            m.writer().flush();
            serverCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void startChat(int id) {
        try {
            Message m = new Message(3); //action to server
            m.writer().writeByte(1); //start chat with player
            m.writer().writeInt(id);
            m.writer().flush();
            serverCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void chatSend(int chatId, String text) {
        try {
            Message m = new Message(3);
            m.writer().writeByte(2);
            m.writer().writeInt(chatId);
            m.writer().writeUTF(text);
            m.writer().flush();
            serverCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getNamePlayer(int parseInt) {
        try {
            System.out.println("ask name");
            Message m = new Message(3);
            m.writer().writeByte(3); //get name and store in tmp;
            m.writer().writeInt(parseInt);
            m.writer().flush();
            serverCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendBet(int i, int parseInt) {
        try {
            Message m = new Message(4); // Game Tai xiu
            m.writer().writeByte(1); //bet
            m.writer().writeInt(i); // selection
            m.writer().writeInt(parseInt); // money
            m.writer().flush();
            serverCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getListRoomAZ() {
        try {
            Message m = new Message(5); // AZ;
            m.writer().writeByte(1); //get list room
            m.writer().flush();
            serverCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchRoomAZ(String trim) {
        try {
            Message m = new Message(5); // AZ;
            m.writer().writeByte(2); //search room
            m.writer().writeUTF(trim);
            m.writer().flush();
            serverCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addNewRoom(String trim, String trim1, int parseInt) {
        try {
            Message m = new Message(5); // AZ;
            m.writer().writeByte(3); //add room
            m.writer().writeUTF(trim);
            m.writer().writeUTF(trim1);
            m.writer().writeInt(parseInt);
            m.writer().writeInt(ClientView.id);
            m.writer().flush();
            serverCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void outRoomAZ(String name) {
        try {
            Message m = new Message(5); // AZ;
            m.writer().writeByte(4); //out room
            m.writer().writeUTF(name);
            m.writer().writeInt(ClientView.id);
            m.writer().flush();
            serverCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendPosAZ(int x, int y, int direction) {
        try {
            Message m = new Message(5); // AZ;
            m.writer().writeByte(5); //send pos game AZ
            m.writer().writeInt(x);
            m.writer().writeInt(y);
            m.writer().writeInt(direction);
            m.writer().flush();
            serverCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enterRoomAZ(String nameR) {
        try {
            Message m = new Message(5); // AZ;
            m.writer().writeByte(6); //enter room
            m.writer().writeUTF(nameR);
            m.writer().flush();
            serverCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addBullet(int x, int y, int direction) {
        try {
            Message m = new Message(5); // AZ;
            m.writer().writeByte(7); //
            m.writer().writeInt(x);
            m.writer().writeInt(y);
            m.writer().writeInt(direction);
            m.writer().flush();
            serverCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void defeatEnemyAZ() {
        try {
            Message m = new Message(5);
            m.writer().writeByte(8);
            m.writer().flush();
            serverCon.sendMessage(m);
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    public void readyAZ() {
        try {
            Message m = new Message(5);
            m.writer().writeByte(9);
            m.writer().flush();
            serverCon.sendMessage(m);
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    public void sendImageAvatar(ByteArrayOutputStream byteArrayOutputStream) {
        try {
            Message m = new Message(0);
            m.writer().writeByte(2);
            m.writer().writeInt(byteArrayOutputStream.size());
            m.writer().write(byteArrayOutputStream.toByteArray());
            m.writer().flush();
            serverCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addFriend(int idPlayer) {
        try {
            Message m = new Message(3);
            m.writer().writeByte(4);
            m.writer().writeInt(idPlayer);
            m.writer().flush();
            serverCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeFriend(int idPlayer) {
        try {
            Message m = new Message(3);
            m.writer().writeByte(5);
            m.writer().writeInt(idPlayer);
            m.writer().flush();
            serverCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showInfor(int idPlayer) {
        try {
            Message m = new Message(3);
            m.writer().writeByte(6);
            m.writer().writeInt(idPlayer);
            m.writer().flush();
            serverCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public void updateListChat(int idPlayer) {
		// TODO Auto-generated method stub
		try {
            Message m = new Message(3);
            m.writer().writeByte(7);
            m.writer().writeInt(idPlayer);
            m.writer().flush();
            serverCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
