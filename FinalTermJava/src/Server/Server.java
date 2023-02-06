package Server;

//import Client.Client;
import Network.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class Server {
    public ServerSocket serverSocket;
    public static Server instance;
    public final static int post = 9999;
    private boolean start = false;
    private int id;
    public ArrayList<ClientCon> clients;
    public GameTaiXiu gameTaiXiu;
    public ArrayList<RoomAZService> roomAZServices;

    public void start() {
        clients = new ArrayList<ClientCon>();
        try {

            serverSocket = new ServerSocket(post);
            if (SQLManager.create("vuigame", "sa", "123456")) {
                System.out.println("Connect database success!");

                new Thread() {
                    @Override
                    public void run() {
                        try {
                            while (true) {
                                Thread.sleep(100);
                                if (SQLManager.conn.isClosed()) {
                                    SQLManager.create("vuigame", "sa", "123456");
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                SQLManager.stat.executeUpdate("update inforUser set connected = 0");

                start = true;
            } else
                System.out.println("Cannot connect database");
            id = 0;
            gameTaiXiu = new GameTaiXiu(this);
            gameTaiXiu.start();
            SQLManager.conn.createStatement().executeUpdate("delete from roomAZ");
            roomAZServices = new ArrayList<>();
            while (start) {
                Socket socket = serverSocket.accept();
                ClientCon clientCon = new ClientCon(socket, ++id);
                System.out.println("Client " + id + " connected!");
                clients.add(clientCon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void stop() {
        if (start) {
            start = false;
            close();
        }
    }

    public void close() {
        try {
            serverSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        instance = new Server();
        instance.start();
    }

    public void disconnect(ClientCon clientCon) {
        synchronized(clients) {
            clients.remove(clientCon);
            try {
                SQLManager.stat.executeUpdate("update inforUser set connected = 0 where id = " + clientCon.idPlayer );
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Disconnect client: " + clientCon.idPlayer);
        }
    }

    public void outRoomAZ(Message m) throws IOException {
        String name = m.reader().readUTF();
        int idPlayer = m.reader().readInt();
        for (int i = 0; i < roomAZServices.size(); i++) {
            if (roomAZServices.get(i).name.equals(name)) {
                if(roomAZServices.get(i).idPlayer1 == idPlayer) {
                	roomAZServices.get(i).clientCon1.roomAZService = null;
                    roomAZServices.get(i).idPlayer1 = -1;
                    roomAZServices.get(i).clientCon1 = null;
                    return;
                }
                else if (roomAZServices.get(i).idPlayer2 == idPlayer) {
                	roomAZServices.get(i).clientCon2.roomAZService = null;
                    roomAZServices.get(i).idPlayer2 = -1;
                    roomAZServices.get(i).clientCon2 = null;
                    return;
                }
            }
        }

    }

    public void removeRoomAZ(String name) {
        for (int i = 0; i < roomAZServices.size(); i++) {
            if (roomAZServices.get(i).name.equals(name)) {
                roomAZServices.remove(i);
                try {
                    SQLManager.stat.executeUpdate("delete from roomAZ where nameRoom = '" + name + "'");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void enterRoomAZ(String readUTF, ClientCon clientCon) {
        for (int i = 0; i < roomAZServices.size(); i++) {
            if (roomAZServices.get(i).name.equals(readUTF)) {
                if (roomAZServices.get(i).idPlayer1 == -1) {
                    roomAZServices.get(i).clientCon1 = clientCon;
                    roomAZServices.get(i).idPlayer1 = clientCon.idPlayer;
                    clientCon.roomAZService = roomAZServices.get(i);
                    clientCon.service.enterRoomAZ(roomAZServices.get(i).name, roomAZServices.get(i).pass, roomAZServices.get(i).bet);
//                  pos set2;
                        clientCon.service.setPosPlayerAZ(roomAZServices.get(i).x2, roomAZServices.get(i).y2, roomAZServices.get(i).d2);
                        roomAZServices.get(i).isSet2 = true;
                    roomAZServices.get(i).createEnemy(roomAZServices.get(i).clientCon2);
                    return;
                }
                else if (roomAZServices.get(i).idPlayer2 == -1) {
                    roomAZServices.get(i).clientCon2 = clientCon;
                    roomAZServices.get(i).idPlayer2 = clientCon.idPlayer;
                    clientCon.roomAZService = roomAZServices.get(i);
                    clientCon.service.enterRoomAZ(roomAZServices.get(i).name, roomAZServices.get(i).pass, roomAZServices.get(i).bet);
                    clientCon.service.setPosPlayerAZ(roomAZServices.get(i).x2, roomAZServices.get(i).y2, roomAZServices.get(i).d2);
                    roomAZServices.get(i).isSet2 = true;
                    roomAZServices.get(i).createEnemy(roomAZServices.get(i).clientCon1);
                    return;
                }
                else {
                    try {
                        clientCon.service.start_ok_dialog("Room is full");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

	public void sendListChat(int readInt) {
		// TODO Auto-generated method stub
		for (int i = 0; i < clients.size(); i++) {
			if (clients.get(i).idPlayer == readInt) {
				clients.get(i).service.sendListChat();
			}
		}
	}
}
