package Server;

import Network.Message;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class GameTaiXiu extends Thread{
    private final Server server;
    private final static int TIMEROUND = 5;
    private String text = "";
//    CloneSQL cloneSQL = new CloneSQL("vuigame", "sa", "123456");

    public GameTaiXiu(Server server) {
        this.server = server;
    }
    @Override
    public void run() {
        new Thread() {
            @Override
            public void run() {
                CloneSQL cloneSQL = new CloneSQL("vuigame", "sa", "123456");
                while (true) {
                    try {
                        Thread.sleep(200);
//                    System.out.println("Start");

                        ResultSet rs = cloneSQL.conn.createStatement().executeQuery("select sum(bet) from taixiu where selection = " + 1);
                        rs.next();
                        int xiu = rs.getInt(1);
                        rs = cloneSQL.conn.createStatement().executeQuery("select sum(bet) from taixiu where selection = " + 0);
                        rs.next();
                        int tai = rs.getInt(1);
//                        System.out.println(xiu + " : " + tai);
                        for (int i = 0; i < server.clients.size(); i++) {
                            server.clients.get(i).service.sendMoney(tai, xiu);
                            server.clients.get(i).service.sendNotification(text);
                        }


                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        try {
            SQLManager.conn.createStatement().executeUpdate("delete from taixiu");
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        CloneSQL cloneSQL = new CloneSQL("vuigame", "sa", "123456");
        while (true) {
            try {

                ResultSet rs = SQLManager.conn.createStatement().executeQuery("select sum(bet) from taixiu");
                if (rs.next()) {
                    if (rs.getLong(1) > 0) {
                        text = "Starting!";
                        for (int i = 0; i < server.clients.size(); i++) {
                            server.clients.get(i).service.sendNotification(text);
                            server.clients.get(i).service.sendTimeGameTaiXiu(this.timer(TIMEROUND));
                        }
                        //Bắt đầu
                        for (int j = 0; j <= TIMEROUND; j++) {
                            String time = timer(TIMEROUND - j);
                            for (int i = 0; i < server.clients.size(); i++) {
                                server.clients.get(i).service.sendTimeGameTaiXiu(time);
                            }
                            tick();
                        }

                        Random ran = new Random();
                        int dice1 = ran.nextInt(6) + 1;
                        int dice2 = ran.nextInt(6) + 1;
                        int dice3 = ran.nextInt(6) + 1;
                        if (dice1 + dice2 + dice3 >= 11) {
                            congtienTai();
                            for (int i = 0; i < server.clients.size(); i++) {
                                server.clients.get(i).service.sendDice(dice1, dice2, dice3);
                            }
                        } else {
                            congtienXiu();
                            for (int i = 0; i < server.clients.size(); i++) {
                                server.clients.get(i).service.sendDice(dice1, dice2, dice3);
                            }
                        }

                        text = "See result!";
                        for (int i = 0; i < server.clients.size(); i++) {
                            server.clients.get(i).service.sendNotification(text);
                        }

                        //Nghi 1 phut
                        for (int j = 0; j <= 5; j++) {
                            String time = timer(5 - j);
                            for (int i = 0; i < server.clients.size(); i++) {
                                server.clients.get(i).service.sendTimeGameTaiXiu(time);
                            }
                            tick();
                        }

                        for (int i = 0; i < server.clients.size(); i++) {
                            server.clients.get(i).updateUser();
                        }

                        reset();
                        text = "Ready!";
                        for (int i = 0; i < server.clients.size(); i++) {
                            server.clients.get(i).service.sendNotification(text);
                        }

                        //Nghi 1 phut
                        for (int j = 0; j <= 5; j++) {
                            String time = timer(5 - j);
                            for (int i = 0; i < server.clients.size(); i++) {
                                server.clients.get(i).service.sendTimeGameTaiXiu(time);
                            }
                            tick();
                        }

                        text = "Starting!";
                        for (int i = 0; i < server.clients.size(); i++) {
                            server.clients.get(i).service.sendNotification(text);
                            server.clients.get(i).service.sendTimeGameTaiXiu(this.timer(TIMEROUND));
                        }

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private String timer(int i) {
        String phut = i / 60 < 10 ? "0" + String.valueOf((i / 60)) : String.valueOf(i/60);
        String giay = i % 60 < 10 ? "0" + String.valueOf((i % 60)) : String.valueOf(i%60);
        return phut + ":" + giay;
    }

    private void tick() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void reset() throws SQLException {
        SQLManager.conn.createStatement().executeUpdate("delete from taixiu");
    }

    private void congtienXiu() throws SQLException {
        ResultSet rs1 = SQLManager.conn.createStatement().executeQuery("select * from taixiu where selection = " + 1);
        while (rs1.next()) {
            ResultSet rs2 = SQLManager.conn.createStatement().executeQuery("select coin from inforUser where id = " + rs1.getInt(1));
            rs2.next();
            int coin = rs2.getInt(1);
            SQLManager.conn.createStatement().executeUpdate("update inforUser set coin = " + (coin + rs1.getInt(3)*2));
        }

    }

    private void congtienTai() throws SQLException {
        ResultSet rs1 = SQLManager.conn.createStatement().executeQuery("select * from taixiu where selection = " + 0);
        while (rs1.next()) {
            ResultSet rs2 = SQLManager.conn.createStatement().executeQuery("select coin from inforUser where id = " + rs1.getInt(1));
            rs2.next();
            int coin = rs2.getInt(1);
            SQLManager.conn.createStatement().executeUpdate("update inforUser set coin = " + (coin + rs1.getInt(3)*2));
        }
    }

    public void bet(Message m, ClientCon clientCon) throws Exception {

        int selection = m.reader().readInt();
        int money = m.reader().readInt();
//        System.out.println(selection + " " + money);
        ResultSet rs = SQLManager.conn.createStatement().executeQuery("select coin from inforUser where id = " + clientCon.idPlayer);
        rs.next();
        int coin = rs.getInt(1);
        if (money <= coin) {
        	SQLManager.conn.createStatement().executeUpdate("update inforUser set coin = " + (coin - money) + " where id = " + clientCon.idPlayer );
            SQLManager.conn.createStatement().executeUpdate("insert into taixiu values (" + clientCon.idPlayer + ", " + selection + ", " + money + ")");
            clientCon.updateUser();
        } else clientCon.service.start_ok_dialog("You cannot bet an amount that exceeds the amount you own");
//        cloneSQL.close();
    }
}
