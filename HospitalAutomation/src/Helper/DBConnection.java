package Helper;

import java.sql.*;

public class DBConnection {
	Connection c = null;

	public DBConnection() {
	}

	public Connection connDb() {
		try {
			this.c = DriverManager.getConnection("jdbc:mysql://localhost:3325/hospital?user=root&password=12345");
			return c;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

}
