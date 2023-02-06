package Connect;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connect {
	static Connection conn = null;
	//connect from java to sql
	public static Connection connect() {
		try {
			//load driver
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			//start connecting EmployeeDB database
			conn = DriverManager.getConnection("jdbc:sqlserver://LAPTOP-MDR2DOI2\\SQLEXPRESS:1433;databaseName=ATM;user=sa;password=123456");
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}

