import java.rmi.server.ExportException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DOTNETGROUP
 */
public class KetNoiSQL {

    private Connection con;

    public KetNoiSQL() {
        String url = "sun.jdbc.odbc.JdbcOdbcDriver";
        try {
            Class.forName(url);
//            String dbUrl = "jdbc:odbc:ATM.mdb";
            String Durl="jdbc:odbc:ATM";
            con = DriverManager.getConnection(Durl,"sa","123456");
        } catch (Exception ex) {
            Logger.getLogger(KetNoiSQL.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(KetNoiSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet GetResultSet(String tableName) throws SQLException {
        ResultSet rs = null;
        Statement stmt = con.createStatement();
        String sql = "select * from " + tableName;
        rs = stmt.executeQuery(sql);
        return rs;
    }

    public void Close() throws Exception {
        con.close();
    }

    public static void main(String[] args) {
        KetNoiSQL kn = new KetNoiSQL();
        try {
            ResultSet rs= kn.GetResultSet("Account");//Table Name
            while(rs.next())
            {
                System.out.println(rs.getString("ID"));//Field Name
            }
            kn.Close();
        } catch (SQLException ex) {
            Logger.getLogger(KetNoiSQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(KetNoiSQL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
