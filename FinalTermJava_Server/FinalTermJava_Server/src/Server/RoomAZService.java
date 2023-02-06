package Server;

//import Client.TankAZ.MainObject;
import Network.Message;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class RoomAZService extends Thread{
    String name;
    String pass;
    int bet;
    int idPlayer1;
    int idPlayer2;

    boolean start = false, end = false;

    boolean isSet1, isSet2;
    ClientCon clientCon1;
    boolean isReady1, isReady2;
    ClientCon clientCon2;
    int x1 = 250, y1 = 440, d1 = 1;
    int x2 = 250, y2 = 70, d2 = 3;

    public RoomAZService(String name, String pass, int bet, int idPlayer1, int idPlayer2) throws SQLException {
        this.name = name;
        this.pass = pass;
        this.bet = bet;
        this.idPlayer1 = idPlayer1;
        this.idPlayer2 = idPlayer2;

        for (int i = 0; i < Server.instance.clients.size(); i++) {
            if (Server.instance.clients.get(i).idPlayer == idPlayer1) {
                this.clientCon1 = Server.instance.clients.get(i);
                clientCon1.roomAZService = this;

            }
            if (Server.instance.clients.get(i).idPlayer == idPlayer2) {
                this.clientCon2 = Server.instance.clients.get(i);
                clientCon2.roomAZService = this;
            }
        }
//        ResultSet rs = SQLManager.stat.executeQuery("select  * from roomAZ");
        SQLManager.stat.executeUpdate("INSERT INTO roomAZ VALUES('" + name + "','" + bet + "', '" + pass +"', 1)");
//        clientCon1.service.setPosPlayerAZ(x1, y1, d1);

    }

    public void run() {
        while (idPlayer1 != -1 || idPlayer2 != -1) {
            try {
                Thread.sleep(300);
                if (idPlayer1 != -1&& idPlayer2 == -1) {
                    clientCon1.service.setPosPlayerAZ(x1, y1, d1);
                    isSet1 = true;
                    clientCon1.service.stringAZ("Please wait for another player");
                }
                else if (idPlayer1 == -1&& idPlayer2 != -1){
                    clientCon2.service.setPosPlayerAZ(x1, y1, d1);
                    isSet1 = true;
                    clientCon2.service.stringAZ("Please wait for another player");
                }
                if (idPlayer1 != -1&& idPlayer2 != -1) {
                    if (!isReady1 && !isReady2) {
                        clientCon1.service.stringAZ("Ready to start new game");
                        clientCon2.service.stringAZ("Ready to start new game");
                    }
                    if (start) {
                        for (int i = 5; i > 0; i--) {
                            clientCon1.service.stringAZ("The match will start in " + i + " seconds");
                            clientCon2.service.stringAZ("The match will start in " + i + " seconds");
                            tick();
                        }
                        clientCon1.service.stringAZ("Start!");
                        clientCon2.service.stringAZ("Start!");
                        tick();
                        start = false;
                        clientCon1.service.stringAZ("");
                        clientCon2.service.stringAZ("");
                    }
                }
                if (end) {
                    Thread.sleep(5000);

                    isReady1 = false;
                    isReady2 = false;
                    end = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Server.instance.removeRoomAZ(name);
    }

    public void tick() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendPos(ClientCon clientCon, Message m) throws IOException {
        int x = m.reader().readInt();
        int y = m.reader().readInt();
        int d = m.reader().readInt();
        if (x < 0 || x > 460) {
            if (d == 2) {
                x = 460;
                clientCon.service.setPosPlayerAZ(x, y, d);
            }
            else if (d == 4) {
                x = 0;
                clientCon.service.setPosPlayerAZ(x, y, d);
            }
        }
        if (y < 0 || y > 460) {
            if (d == 3) {
                y = 460;
                clientCon.service.setPosPlayerAZ(x, y, d);
            }
            else if (d == 1) {
                y = 0;
                clientCon.service.setPosPlayerAZ(x, y, d);
            }
        }
        if (idPlayer1 != clientCon.idPlayer && idPlayer1 != -1) {
            clientCon1.service.sendPosEnemyAZ(x, y, d);
        }
        if (idPlayer2 != clientCon.idPlayer && idPlayer2 != -1) {
            clientCon2.service.sendPosEnemyAZ(x, y, d);
        }
    }

    public void createEnemy(ClientCon clientCon) {
        clientCon.service.sendPosEnemyAZ(x2, y2, d2);
    }

    public void addBullet(ClientCon clientCon, Message m) throws IOException {
        int x = m.reader().readInt();
        int y = m.reader().readInt();
        int d = m.reader().readInt();
        if (idPlayer1 != clientCon.idPlayer && idPlayer1 != -1) {
            clientCon1.service.sendBulletAZ(x, y, d);
        }
        if (idPlayer2 != clientCon.idPlayer && idPlayer2 != -1) {
            clientCon2.service.sendBulletAZ(x, y, d);
        }

    }

    public void win(ClientCon clientCon) {
        end = true;
        clientCon.service.stringAZ("You win");
        try {
			ResultSet rs = SQLManager.stat.executeQuery("select coin from inforUser where id = " +  clientCon.idPlayer);
			rs.next();
			int coin = rs.getInt(1);
			SQLManager.stat.executeUpdate("update inforUser set coin = " + (coin + bet) + "where id = " + clientCon.idPlayer);
			clientCon.updateUser();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        if (idPlayer1 != clientCon.idPlayer && idPlayer1 != -1) {
            clientCon1.service.stringAZ("You loss");
            try {
    			ResultSet rs = SQLManager.stat.executeQuery("select coin from inforUser where id = " +  idPlayer1);
    			rs.next();
    			int coin = rs.getInt(1);
//    			if (coin-bet)
    			SQLManager.stat.executeUpdate("update inforUser set coin = " + (coin - bet) + "where id = " + idPlayer1);
    			clientCon1.updateUser();
    		} catch (Exception e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
        }
        if (idPlayer2 != clientCon.idPlayer && idPlayer2 != -1) {
            clientCon2.service.stringAZ("You loss");
            try {
    			ResultSet rs = SQLManager.stat.executeQuery("select coin from inforUser where id = " +  idPlayer2);
    			rs.next();
    			int coin = rs.getInt(1);
    			SQLManager.stat.executeUpdate("update inforUser set coin = " + (coin - bet) + "where id = " + idPlayer2);
    			clientCon2.updateUser();
    		} catch (Exception e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void ready(ClientCon clientCon) {
        if (clientCon.idPlayer == idPlayer1) {
            isReady1 = true;
        }
        else isReady2 = true;
        if (isReady1 && isReady2) {
            start = true;
        }
    }
}
