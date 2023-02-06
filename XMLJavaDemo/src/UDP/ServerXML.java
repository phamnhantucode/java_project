package UDP;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServerXML {
    static DatagramSocket serverSocket;

    static {
        try {
            serverSocket = new DatagramSocket(9876);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    static InetAddress inetAddress;

    {
        try {
            inetAddress = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public ServerXML() throws SocketException {
    }

    public static void main(String[] args) {
        while (true) {
            byte[] receive = new byte[100000];
            byte[] send = new byte[100000];
            DatagramPacket receivePacket = new DatagramPacket(receive, receive.length);
            try {
                serverSocket.receive(receivePacket);

                String rec = new String(receive, receivePacket.getOffset(), receivePacket.getLength());
                String[] data = rec.split("///");
                switch (data[0]) {
                    case "load":
                        loadData(receivePacket, serverSocket);
                        break;
                    case "add":
                        addData(data, serverSocket);
                        break;
                    case "delete":
                        delete(data[1], serverSocket);
                        break;
                    default:
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void delete(String datum, DatagramSocket serverSocket) {
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from StudentInformation where ID=?");
            preparedStatement.setString(1, datum);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void addData(String[] data, DatagramSocket serverSocket) {
        List<String[]> l = new ArrayList<>();
        Connection connection = getConnection();
        byte[] r = data[1].getBytes(StandardCharsets.UTF_8);
        try {
            //Xu ly du lieu nhan
            File f = new File("src//UDP//student_server.xml");
            try {
                if (f.exists()) {
                    f.delete();
                    f.createNewFile();
                } else f.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(f));
            dataOutputStream.write(r, 0, r.length);
            dataOutputStream.flush();
            dataOutputStream.close();
            //Lay du lieu vua nhan duoc o XML
            l = CreateXML.readXML(f);

            //Ghi du lieu vao database
            PreparedStatement preparedStatement = connection.prepareStatement("insert into StudentInformation values (?,?,?)");

            for (int i = 0; i < l.size(); i++) {
                preparedStatement.setString(1, l.get(i)[0]);
                preparedStatement.setString(2, l.get(i)[1]);
                preparedStatement.setString(3, l.get(i)[2]);
                preparedStatement.execute();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadData(DatagramPacket receivePacket, DatagramSocket serverSocket) {
        List<String[]> l = new ArrayList<>();
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from StudentInformation");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String[] data = {rs.getString(1).trim(), rs.getString(2).trim(), rs.getString(3).trim()};
                l.add(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        File f = new File("src//UDP//student_server.xml");
        try {
            if (f.exists()) {
                f.delete();
                f.createNewFile();
            } else f.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        CreateXML.create(l, f);


        //Send data
        try {

            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(f));
            byte[] data = dataInputStream.readAllBytes();
//            for (int i = 0; i < data.length; i++) {
//                System.out.print(data[i]);
//            }
//            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
//            System.out.println(bf.readLine().getBytes());
//            BufferedReader bf = new BufferedReader(new Fi
//            leReader(f));
//            DatagramPacket sendPacket = new DatagramPacket(bf.readLine().getBytes(StandardCharsets.UTF_8), bf.readLine().getBytes(StandardCharsets.UTF_8).length, receivePacket.getAddress(), 6789);
            DatagramPacket sendPacket = new DatagramPacket(data.clone(), data.length, receivePacket.getAddress(), 6789);
            serverSocket.send(sendPacket);
            System.out.println(sendPacket.getData() + " " + sendPacket.getLength());
        } catch (Exception e) {
            e.printStackTrace();

    }


}

    private static Connection getConnection() {
        Connection connect = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connect = DriverManager.getConnection("jdbc:sqlserver://LAPTOP-MDR2DOI2\\SQLEXPRESS:1433;databaseName=Student;user=sa;password=123456");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connect;
    }
    }
