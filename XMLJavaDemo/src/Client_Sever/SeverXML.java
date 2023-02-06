
import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeverXML {
    public final static int PORT = 9888;
    static Controller controller = new Controller();
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            System.out.println("Binding to port " + PORT + ", please wait ...");
            Thread.sleep(1000);
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started: " + serverSocket);
            System.out.println("Waiting for a client");
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("Client accepted: " + socket);
                    //in and out
                    PrintWriter out = new PrintWriter(socket.getOutputStream());

                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line = null;
                    while (true) {
                        line = in.readLine();
                        System.out.println("Receive: " + line);
                        String[] data = line.split("//");
                        switch (data[0]) {
                            case "add":
                                        controller.add(data, socket);
                                break;
                            case "getData":
                                        controller.send(socket, in, out);
                                break;
                            default:
                                break;
                        }
//                        socket.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

}

class Controller {

    public void add(String[] data, Socket socket) {
        Connection conn = Connect.getConnect();
        if (conn == null) {
            System.out.println("Can not connect to database");
            return;
        }
        try {
            File f = new File("src//Client_Sever//student_server.xml");
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();

//            Thread.sleep(1000);
//            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(f));
            byte[] file = data[1].getBytes();
            dataOutputStream.write(file, 0, file.length);
            dataOutputStream.flush();
            List<String[]> list = CreateXML.readXML(f);
            for (int i = 0; i < list.size(); i++) {
                String[] row = list.get(i);
                PreparedStatement cs = conn.prepareStatement("insert into StudentInformation values (?,?,?,?,?)");
                cs.setString(1, row[0]);
                cs.setString(2, row[1]);
                cs.setString(3, row[2]);
                cs.setString(4, row[3]);
                cs.setString(5, row[4]);
                cs.execute();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void send(Socket socket, BufferedReader in, PrintWriter out) {

        List<String[]> l = new ArrayList<>();
        Connection connection = Connect.getConnect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from StudentInformation");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String[] data = {rs.getString(1).trim(), rs.getString(2).trim(), rs.getString(3).trim(), rs.getString(4).trim(), rs.getString(5).trim()};
                l.add(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        File f = new File("src//Client_Sever//student_server.xml");
        if (f.exists()) {
            f.delete();
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        CreateXML.create(l, f);

//        DataInputStream dataInputStream = new DataInputStream(f);
        try {
            boolean t = true;
            while (t) {

                DataInputStream dataInputStream = new DataInputStream(new FileInputStream(f));
                byte[] file = dataInputStream.readAllBytes();
                out.println(new String(file, "UTF-8"));
                out.flush();
                if (in.readLine().equals("OK")) t = false;
//            String decoded = new String(file, "UTF-8");
//            decoded.replaceAll("[" + System.lineSeparator() + "]+", "baode");
//            dataOutputStream.write(file, 0, file.length);
//            dataOutputStream.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Connect {
    public static Connection connection = null;
    public static Connection getConnect() {
        try {
            //load driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            //start connecting DB;
            connection = DriverManager.getConnection("jdbc:sqlserver://LAPTOP-MDR2DOI2\\SQLEXPRESS:1433;databaseName=Student;user=sa;password=123456");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
