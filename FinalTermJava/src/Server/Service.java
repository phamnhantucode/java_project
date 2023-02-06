package Server;

import Network.Message;

import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Service {
    private static final int AZ = 4;
    public ClientCon clientCon;


    private static final byte ACTION_VIEW = 1;
    private static final byte ACCOUNT = 0;
    private static final byte INFOR = 2;
    private static final byte TAIXIU = 3;

    public Service(ClientCon clientCon) {
        this.clientCon = clientCon;
    }

    public void load() throws IOException {
        Message m = new Message(ACTION_VIEW);
        DataOutputStream dos = m.writer();
        dos.writeByte(0); //load Page
        dos.flush();
        this.clientCon.sendMessage(m);
    }

    public void start_ok_dialog(String s) throws IOException{
        Message m = new Message(ACTION_VIEW);
        DataOutputStream dos = m.writer();
        dos.writeByte(1); // open ok dialog
        dos.writeUTF(s);
        dos.flush();
        this.clientCon.sendMessage(m);
    }

    public void loginFail() throws IOException {
        Message m = new Message(ACCOUNT);
        DataOutputStream dos = m.writer();
        dos.writeByte(-1);
        dos.flush();
        this.clientCon.sendMessage(m);
    }



    public void sendInforUser(int id, String name, int coin, int exp, String master, int ranking) throws IOException {
        Message m = new Message(INFOR);
        m.writer().writeByte(1);
        m.writer().writeInt(id);
        m.writer().writeUTF(name);
        m.writer().writeLong(coin);
        m.writer().writeInt(exp);
        m.writer().writeUTF(master);
        m.writer().writeInt(ranking);
        m.writer().flush();
        this.clientCon.sendMessage(m);
    }

    public void sendListFriends(){
        try {
            Message m = new Message(INFOR);
            m.writer().writeByte(2);
//            Lấy kích thước của list Friends
            ResultSet rs1 = SQLManager.stat.executeQuery("select count(*) from friend where idPlayer = " + clientCon.idPlayer + " or idFriend = " + clientCon.idPlayer);
            rs1.next();
            int size = rs1.getInt(1);
//            System.out.println(size);
            m.writer().writeInt(size);
            //Lấy danh sách
            ResultSet rs = SQLManager.stat.executeQuery("select * from friend where idPlayer = " + clientCon.idPlayer + " or idFriend = " + clientCon.idPlayer);
            ArrayList<Integer[]> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Integer[]{rs.getInt(1), rs.getInt(2)});
            }
//            System.out.println(clientCon.idPlayer);
//            System.out.println(size);
            for (int i = 0;i < size;i++) {

                int idFriend = (list.get(i)[0] == clientCon.idPlayer) ? list.get(i)[1] : list.get(i)[0];
                ResultSet rs2 = SQLManager.stat.executeQuery("select * from inforUser where id = " + idFriend);
                rs2.next();
                m.writer().writeInt(rs2.getInt(1));
                m.writer().writeUTF(rs2.getString(2));

//                System.out.println(rs2.getString(1));
                m.writer().writeInt(rs2.getInt(4));
                ResultSet k = SQLManager.conn.createStatement().executeQuery("select avatar from avatar where idPlayer = " + rs2.getInt(1));
                if (k.next()) {
                    byte[] image = k.getBytes(1);
                    m.writer().writeInt(image.length);
                    m.writer().write(image);
                    System.out.println(image.length);
                }
                else m.writer().writeInt(0);
            }
            m.writer().flush();
            this.clientCon.sendMessage(m);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendListPlayers() {
        try {
            Message m = new Message(INFOR);
            m.writer().writeByte(3);
            //kích thước
            int size = 2;
            m.writer().writeInt(size);
            //danh sách
            ResultSet rs = SQLManager.stat.executeQuery("select top 5 * from inforUser order by NEWID()");
            while (rs.next()) {
                m.writer().writeInt(rs.getInt(1));
                m.writer().writeUTF(rs.getString(2).trim());
                m.writer().writeInt(rs.getInt(4));
                ResultSet k = SQLManager.conn.createStatement().executeQuery("select avatar from avatar where idPlayer = " + rs.getInt(1));
                if (k.next()) {
                    byte[] image = k.getBytes(1);
                    m.writer().writeInt(image.length);
                    m.writer().write(image);
                    System.out.println(image.length);
                }
                else m.writer().writeInt(0);
            }
            m.writer().flush();
            this.clientCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendListFinds(String readUTF) {
        try {
            Message m = new Message(INFOR);
            m.writer().writeByte(4);
            //kích thước
            ResultSet rs1 = SQLManager.stat.executeQuery("select count(*) from inforUser where namePlayer like '%" + readUTF + "%'");
            rs1.next();
            int size = rs1.getInt(1);
            if (size == 0) {
                start_ok_dialog("Không thể tìm thấy");
            } else {
                m.writer().writeInt(size);
                //danh sách
                ResultSet rs = SQLManager.stat.executeQuery("select * from inforUser where namePlayer like '%" + readUTF + "%'");
                while (rs.next()) {
                    m.writer().writeInt(rs.getInt(1));
                    m.writer().writeUTF(rs.getString(2));
                    m.writer().writeInt(rs.getInt(4));
                    ResultSet k = SQLManager.conn.createStatement().executeQuery("select avatar from avatar where idPlayer = " + rs.getInt(1));
                    if (k.next()) {
                        byte[] image = k.getBytes(1);
                        m.writer().writeInt(image.length);
                        m.writer().write(image);
                        System.out.println(image.length);
                    }
                    else m.writer().writeInt(0);
                }
                m.writer().flush();
                this.clientCon.sendMessage(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendListChat() {
        try {
            Message m = new Message(INFOR);
            m.writer().writeByte(5);
            String chatIds = null;
            //Lay danh sach chatID
            ResultSet rs = SQLManager.stat.executeQuery("select chatIds from inforUser where id = " + clientCon.idPlayer);
            if (rs.next()) {
                chatIds = rs.getString(1);
            }
            if (chatIds.equals("-1") || chatIds == null) {
                return;
            }
            System.out.println(chatIds);
            String[] listChat = chatIds.split("//");
            m.writer().writeInt(listChat.length); //send size
            System.out.println(listChat[0]);
            //content
            for (int i = 0; i < listChat.length; i++) {
                rs = SQLManager.stat.executeQuery("select * from chat where idChat = " + Integer.parseInt(listChat[i]));
                rs.next();
                m.writer().writeInt(rs.getInt(1));
                m.writer().writeUTF(rs.getString(2));
                m.writer().writeUTF(rs.getString(3) == null ? "" : rs.getString(3) );
                String[] paticipants = rs.getString(4).split("//");
                rs = SQLManager.stat.executeQuery("select avatar from avatar where idPlayer = " + (clientCon.idPlayer != Integer.parseInt(paticipants[0]) ? Integer.parseInt(paticipants[0]) : Integer.parseInt(paticipants[1])));
                if (rs.next()) {
                    byte[] image = rs.getBytes(1);
                    m.writer().writeInt(image.length);
                    m.writer().write(image);
                    System.out.println(image.length);
                }
                else m.writer().writeInt(0);
            }
            m.writer().flush();
            clientCon.sendMessage(m);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendOpenChat(int idChat) {
        try {
            Message m = new Message(ACTION_VIEW);
            m.writer().writeByte(2); //command openchat id
            m.writer().writeInt(idChat);
            m.writer().flush();
            clientCon.sendMessage(m);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void chatReceive(int idChat, String content) {
        try {
            Message m = new Message(ACTION_VIEW);
            m.writer().writeByte(3); //update chat;
            m.writer().writeInt(idChat);
            m.writer().writeUTF(content);
            m.writer().flush();
            clientCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendNotification(String s) {
        try {
            Message m = new Message(TAIXIU);
            m.writer().writeByte(0);
            m.writer().writeUTF(s);
            m.writer().flush();
            clientCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendTimeGameTaiXiu(String time) {
        try {
            Message m = new Message(TAIXIU);
            m.writer().writeByte(1);
            m.writer().writeUTF(time);
            m.writer().flush();
            clientCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendDice(int dice1, int dice2, int dice3) {
        try {
            Message m = new Message(TAIXIU);
            m.writer().writeByte(2);
            m.writer().writeInt(dice1);
            m.writer().writeInt(dice2);
            m.writer().writeInt(dice3);
            m.writer().flush();
            clientCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMoney(int tai, int xiu) {
        try {
            Message m = new Message(TAIXIU);
            m.writer().writeByte(3);
            m.writer().writeInt(tai);
            m.writer().writeInt(xiu);
            m.writer().flush();
            clientCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendListRoom() {
        try {
            Message m = new Message(AZ);
            m.writer().writeByte(1); //list room
            ResultSet rs = SQLManager.stat.executeQuery("select count(*) from roomAZ");
            rs.next();
            int size = rs.getInt(1);
            m.writer().writeInt(size);
            rs = SQLManager.stat.executeQuery("select * from roomAZ");
            while (rs.next()) {
                m.writer().writeUTF(rs.getString(1));
                m.writer().writeUTF(rs.getString(3));
                m.writer().writeInt(rs.getInt(2));
            }
            m.writer().flush();
            clientCon.sendMessage(m);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void sendRoomSearch(String name) {
        try {
            Message m = new Message(AZ);
            m.writer().writeByte(1); //list room
            ResultSet rs = SQLManager.stat.executeQuery("select count(*) from roomAZ where nameRoom = '%" + name + "%'");
            rs.next();
            int size = rs.getInt(1);
            m.writer().writeInt(size);
            rs = SQLManager.stat.executeQuery("select * from roomAZ where nameRoom = '%\" + name + \"%'");
            while (rs.next()) {
                m.writer().writeUTF(rs.getString(2));
                m.writer().writeUTF(rs.getString(4));
                m.writer().writeInt(rs.getInt(3));
            }
            m.writer().flush();
            clientCon.sendMessage(m);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void addNewRoom(Message message) {
        try {
            String name = message.reader().readUTF();
            ResultSet rs = SQLManager.stat.executeQuery("select * from roomAZ where nameRoom = '" + name + "'");
            if (rs.next()) {
                start_ok_dialog("Name room is exist!");
                return;
            }
            String pass = message.reader().readUTF();
            int bet = message.reader().readInt();
            RoomAZService roomAZService = new RoomAZService(name, pass, bet, clientCon.idPlayer, -1);
            Server.instance.roomAZServices.add(roomAZService);
            roomAZService.start();
            sendListRoom();
            enterRoomAZ(name, pass, bet);
//            clientCon.service.setPosPlayerAZ(350, 650, 1);
//            m.writer().writeByte();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendPosEnemyAZ(int readInt, int readInt1, int readInt2) {
        try {
            Message m = new Message(AZ);
            m.writer().writeByte(3); //pos enemy;
            m.writer().writeInt(readInt);
            m.writer().writeInt(readInt1);
            m.writer().writeInt(readInt2);
            m.writer().flush();
            clientCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enterRoomAZ(String name, String pass, int bet) {
        try {
            Message m = new Message(AZ);
            m.writer().writeByte(2); //enter to room after create;
            m.writer().writeUTF(name);
            m.writer().writeUTF(pass);
            m.writer().writeInt(bet);
            m.writer().flush();
            clientCon.sendMessage(m);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setPosPlayerAZ(int x1, int y1, int d1) {
        try {
            Message m = new Message(AZ);
            m.writer().writeByte(4); //pos player;
            m.writer().writeInt(x1);
            m.writer().writeInt(y1);
            m.writer().writeInt(d1);
            m.writer().flush();
            clientCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stringAZ(String s) {
        try {
            Message m = new Message(AZ);
            m.writer().writeByte(5); //notification;
            m.writer().writeUTF(s);
            m.writer().flush();
            clientCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendBulletAZ(int x, int y, int d) {
        try {
            Message m = new Message(AZ);
            m.writer().writeByte(6); //
            m.writer().writeInt(x);
            m.writer().writeInt(y);
            m.writer().writeInt(d);
            m.writer().flush();
            clientCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendAvatarUser(int i, byte[] image, int i1, int i2) {
        try {
            Message m = new Message(INFOR);
            m.writer().writeByte(7);
            m.writer().writeInt(image.length);
            m.writer().write(image);
            m.writer().writeInt(i1);
            m.writer().writeInt(i2);
            m.writer().flush();
            clientCon.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showInforUser(int readInt) {
        try {
            ResultSet rs = SQLManager.stat.executeQuery("select * from inforUser where id = " + readInt);
            rs.next();
            Message m =  new Message(INFOR);
            m.writer().writeByte(8);
            m.writer().writeUTF(rs.getString(2));
            m.writer().writeInt(rs.getInt(3));
            rs = SQLManager.stat.executeQuery("select avatar from avatar where idPlayer = " + readInt);
            if (rs.next()) {
                byte[] image = rs.getBytes(1);
                m.writer().writeInt(image.length);
                m.writer().write(image);
            } else m.writer().writeInt(0);
            m.writer().flush();
            clientCon.sendMessage(m);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//
//    public void sendName(int readInt) {
//        try {
//            Message m = new Message(INFOR);
//            m.writer().writeByte(6); // send name playid
//            ResultSet rs = SQLManager.stat.executeQuery("select namePlayer from inforUser where id = " + readInt);
//            rs.next();
//            m.writer().writeUTF(rs.getString(1));
//            m.writer().flush();
//            clientCon.sendMessage(m);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
