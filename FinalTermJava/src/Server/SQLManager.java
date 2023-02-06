package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLManager {
    public static Connection conn;
    public static Statement stat;

    public static boolean create(String database, String user, String pass) {
        try {
            //load driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://LAPTOP-MDR2DOI2\\SQLEXPRESS:1433;databaseName=" + database + ";user=" + user + ";password=" + pass);
            stat = conn.createStatement();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void close() {
        try {
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                conn.close();
            }


        } catch (Exception e) {

        }
        System.out.println("Close Database");
    }


}
