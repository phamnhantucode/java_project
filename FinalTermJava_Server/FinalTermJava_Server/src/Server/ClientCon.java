package Server;

import Network.Message;
import Network.MessageCollect;
import Network.Sender;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.sql.*;
import java.util.concurrent.ThreadLocalRandom;

public class ClientCon {
    public Socket socket;
    public int id;
    public int idPlayer;
    public String nameUser;
    public DataInputStream dis;
    public DataOutputStream dos;
    private MessageCollect messageCollect;
    public MessageHandle messageHandle;
    public Sender sender;
    public Service service;
    Thread messCollectThread;
    Thread sendThread;
    RoomAZService roomAZService;
//    public boolean connected;


    public ClientCon(Socket socket, int id) throws IOException {
        this.socket = socket;
        this.id = id;
        this.dis = new DataInputStream(socket.getInputStream());
        this.dos = new DataOutputStream(socket.getOutputStream());
        this.messageHandle = new MessageHandle(this);
        this.messageCollect = new MessageCollect(this);
         messCollectThread = new Thread(messageCollect);
        messCollectThread.start();
         sendThread = new Thread(sender = new Sender(this));
        sendThread.start();
        service = new Service(this);
    }

    public void login(Message m) {
        try {
            DataInputStream dis = m.reader();
            String user = dis.readUTF().trim();
            System.out.println(user);
            String pass = dis.readUTF().trim();
            System.out.println(pass);
            ResultSet rs = SQLManager.stat.executeQuery("select * from Account where userName = '" + user + "' and pass = '" + pass + "'");
            if (rs.next()) {
//                System.out.println("Login success!");
                idPlayer = rs.getInt("idPlayer");
                SQLManager.stat.executeUpdate("update inforUser set connected = 1 where id = " + idPlayer);
                service.load();
                //send infor
                rs = SQLManager.stat.executeQuery("select * from inforUser where id = " + idPlayer);
                if (rs.next()) {
                    service.sendInforUser(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(5), rs.getString(6), rs.getInt(7));
                    this.nameUser = rs.getString(2);
                }
                dis.close();
                rs =  SQLManager.stat.executeQuery("select avatar from avatar where idPlayer = " + idPlayer);
                if (rs.next()) {
                    byte[] image = rs.getBytes(1);
                    System.out.println(image.length);
                    service.sendAvatarUser(0, image, 90, 90);
                }
            } else {
//                System.out.println("Fail to login!");
                service.start_ok_dialog("Wrong username or password!");
                service.loginFail();
                dis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void doSendMessage(Message m) {
        try {
            this.dos.writeByte(m.getCommand());
            byte[] data = m.getData();
            int size = data.length;
            this.dos.writeInt(size);
            this.dos.write(data);
            this.dos.flush();
            m.cleanup();
        } catch (Exception e) {
            this.close();
            e.printStackTrace();
        }
    }

    public void close() {
        Server.instance.disconnect(this);
        this.cleanNetwork();
    }

    private void cleanNetwork() {
        try {
            if (this.socket != null) {
                this.socket.close();
                this.socket = null;
            }

            if (this.dos != null) {
                this.dos.close();
                this.dos = null;
            }

            if (this.dis != null) {
                this.dis.close();
                this.dis = null;
            }

            this.sendThread.stop();
            this.sendThread = null;
            this.messCollectThread.stop();
            this.messCollectThread = null;
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message m) {
        this.sender.AddMessage(m);
    }

    public void register(Message m) {
        try {
            String user = m.reader().readUTF().trim();
            String pass = m.reader().readUTF().trim();
            ResultSet rs = SQLManager.stat.executeQuery("select * from Account where userName = '" + user + "'");
            if (rs.next()) {
                service.start_ok_dialog("This account is already registered, please try again");
                service.loginFail();
            } else {

                ResultSet rs1 = SQLManager.stat.executeQuery("select count(*) from Account");
                rs1.next();
                idPlayer = rs1.getInt(1);
                System.out.println(idPlayer);
                SQLManager.stat.executeUpdate("insert into Account values('" + user + "', '"+pass+"', " +idPlayer + ")");
                int name = ThreadLocalRandom.current().nextInt();
                SQLManager.stat.executeUpdate("insert into inforUser values(" + idPlayer + ", "+ String.valueOf(name) +", 10000, 0, 0, 'KhÃ´ng', 0, '-1')");
                service.start_ok_dialog("Registration successful, please login again");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUser() throws SQLException, IOException {
        ResultSet rs = SQLManager.stat.executeQuery("select * from inforUser where id = " + idPlayer);
        if (rs.next()) {
            service.sendInforUser(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(5), rs.getString(6), rs.getInt(7));
        }
        this.nameUser = rs.getString(2);
    }

    public void reName(Message message) {
        try {
            String name = message.reader().readUTF();
            if (!SQLManager.stat.executeQuery("select * from inforUser where namePlayer = '" + name + "'").next()) {
                SQLManager.stat.executeUpdate("update inforUser set namePlayer = '" + name + "' where id = " + this.idPlayer);
                service.start_ok_dialog("Rename success!");
                System.out.println(this.nameUser);
                System.out.println(name);
                ResultSet rs = SQLManager.stat.executeQuery("select * from inforUser where id = " + idPlayer);
                if (rs.next()) {
                    service.sendInforUser(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(5), rs.getString(6), rs.getInt(7));
                }
                rs = SQLManager.stat.executeQuery("select chatIds from inforUser where id = " + this.idPlayer);
                rs.next();
                if (rs.getString(1).equals("-1"))  return;
                String[] list = rs.getString(1).split("//");
                for (int i = 0; i < list.length; i++) {
                    rs = SQLManager.stat.executeQuery("select nameChat, content from chat where idChat = " + Integer.parseInt(list[i]));
                    rs.next();
                    String n = rs.getString(1);
                    String text = rs.getString(2);
                    n = n.replaceAll(this.nameUser, name);
                    text = text.replaceAll(this.nameUser, name);
                    SQLManager.stat.executeUpdate("update chat set nameChat = '" + n + "', content = '" + text + "' where idChat = " + Integer.parseInt(list[i]));
                }
                this.nameUser = name;
            } else {
                service.start_ok_dialog("Name already exists, please choose another name!");
                ResultSet rs = SQLManager.stat.executeQuery("select * from inforUser where id = " + idPlayer);
                if (rs.next()) {
                    service.sendInforUser(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(5), rs.getString(6), rs.getInt(7));
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startChat(int idPlayer) {
        ResultSet rs;
        try {
            rs = SQLManager.stat.executeQuery("select namePlayer, chatIds from inforUser where id = " + idPlayer);
            if (!rs.next()) {
                service.start_ok_dialog("Error, please restart app");
            } else {

                String chatIdsPlayer = rs.getString(2);
                String namePlayer = rs.getString(1);
                rs = SQLManager.stat.executeQuery("select chatIds from inforUser where id = " + this.idPlayer);
                rs.next();
                String chatIdsUser = rs.getString(1);
                int count;
                rs = SQLManager.stat.executeQuery("select count(*) from chat");
                rs.next();
                count = rs.getInt(1);
                if (chatIdsPlayer.equals("-1") || chatIdsUser.equals("-1"))
                {

                    SQLManager.stat.executeUpdate("insert into chat values (" + (count + 1) + ", '" + namePlayer + "//" + this.nameUser + "', '', '" + idPlayer + "//" + this.idPlayer + "')");
                    if (chatIdsPlayer.equals("-1"))
                        SQLManager.stat.executeUpdate("update inforUser set chatIds = '" + String.valueOf(count+1) + "' where id = " + idPlayer);
                    else SQLManager.stat.executeUpdate("update inforUser set chatIds = '" + String.valueOf(count+1) + "//"   + chatIdsPlayer +"' where id = " + idPlayer);

                    if (chatIdsUser.equals("-1"))
                        SQLManager.stat.executeUpdate("update inforUser set chatIds = '" + String.valueOf(count+1) + "' where id = " + this.idPlayer);
                    else SQLManager.stat.executeUpdate("update inforUser set chatIds = '"+ String.valueOf(count+1) + "//"   + chatIdsUser + "' where id = " + this.idPlayer);
                    service.sendListChat();
                    //gui thong diep ve sever de mo chat
                    service.sendOpenChat(count+1);
                }
                else {
//                    boolean flag = false;
                    String[] chatIdsPlayers = chatIdsPlayer.split("//");
                    for (int i = 0; i < chatIdsPlayers.length; i++) {
                        rs = SQLManager.stat.executeQuery("select paticipant from chat where idChat = " + Integer.parseInt(chatIdsPlayers[i]));
                        rs.next();
                        String[] paticipants = rs.getString(1).split("//");
                        if (paticipants.length == 2 && (Integer.parseInt(paticipants[0]) == idPlayer || Integer.parseInt(paticipants[1]) == this.idPlayer)) {
                            //gui thÃ´ng Ä‘iá»‡p vá»� client Ä‘á»ƒ má»Ÿ chat
                            service.sendListChat();
                            service.sendOpenChat(Integer.parseInt(chatIdsPlayers[i]));
                            return;
                        }
                    }
                    SQLManager.stat.executeUpdate("insert into chat values (" + (count + 1) + ", '" + namePlayer + "//" + this.nameUser + "', null, '" + namePlayer + "//" + this.nameUser + "')");
                    SQLManager.stat.executeUpdate("update inforUser set chatIds = '" + chatIdsPlayer + "//"  + String.valueOf(count+1) + "' where id = " + idPlayer);
                    SQLManager.stat.executeUpdate("update inforUser set chatIds = '" + chatIdsUser + "//"  + String.valueOf(count+1) + "' where id = " + this.idPlayer);
                    service.sendListChat();
                    service.sendOpenChat(count);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void chatSend(Message m) {
        try {
            int idChat = m.reader().readInt();
            String text = m.reader().readUTF();
//            Láº¥y chatID cÅ©
            ResultSet rs;
            rs = SQLManager.stat.executeQuery("select content, paticipant from chat where idChat = " + idChat);
            rs.next();
            String content = rs.getString(1);
            String[] paticipants = rs.getString(2).split("//");
            if (content.equals("")) {
                content = text;
                SQLManager.stat.executeUpdate("update chat set content = '" + text + "' where idChat = " + idChat);
            }
            else {
                content = content + "//" + text;
                SQLManager.stat.executeUpdate("update chat set content = '" + content + "' where idChat = " + idChat);
            }


            for (int i = 0; i < paticipants.length; i++) {
                rs = SQLManager.stat.executeQuery("select connected from inforUser where id = " + Integer.parseInt(paticipants[i]));
                rs.next();
                if (rs.getInt(1) == 1) {
                    for (int j = 0; j < Server.instance.clients.size(); j++) {
                        if (Server.instance.clients.get(j).idPlayer == Integer.parseInt(paticipants[i]))
                            Server.instance.clients.get(j).service.chatReceive(idChat, content);
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAvatar(Message message) {
        try {
            int size = message.reader().readInt();
            System.out.println(size);
            byte[] image = new byte[size];
            message.reader().read(image);
            ResultSet rs = SQLManager.stat.executeQuery("select * from avatar where idPlayer = " + idPlayer);
            if (rs.next()) {
                PreparedStatement preparedStatement = SQLManager.conn.prepareStatement("update avatar set avatar = ? where idPlayer = " + idPlayer);
                preparedStatement.setBytes(1, image);
                preparedStatement.executeUpdate();
            } else {
                PreparedStatement preparedStatement = SQLManager.conn.prepareStatement("insert into avatar values (?,?)");
                preparedStatement.setInt(1, idPlayer);
                preparedStatement.setBytes(2, image);
                preparedStatement.executeUpdate();
            }
            rs =  SQLManager.stat.executeQuery("select avatar from avatar where idPlayer = " + idPlayer);
            if (rs.next()) {
                byte[] images = rs.getBytes(1);
                service.sendAvatarUser(0, images, 90, 90);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addFriend(int readInt) {
        try {
            ResultSet rs = SQLManager.stat.executeQuery("select * from friend where idPlayer = " + idPlayer  + " or idFriend = " + idPlayer);
            while (rs.next()) {
                if (rs.getInt(1) == readInt || rs.getInt(2) == readInt) {
                    service.start_ok_dialog("You already friend with this Player!");
                    return;
                }
            }
            SQLManager.stat.executeUpdate("insert into friend values (" + idPlayer +", " + readInt +" , 0)");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void removeFriend(int readInt) {
        try {
            SQLManager.stat.executeUpdate("delete from friend where idPlayer = " + idPlayer + " or idPlayer = " + readInt + " and idFriend = " + idPlayer + " or idFriend = " + readInt);
            service.sendListFriends();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
