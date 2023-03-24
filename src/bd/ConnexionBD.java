package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

 public class ConnexionBD {
	private static Connection connect;
	static {
		try {
			Class.forName ("com.mysql.cj.jdbc.Driver");
			try {
				 connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/mediatheque", "root", "");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException("bug to connect at database");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("driver jdbc not found");
		}
		
	}
	
	public static Connection getConnexion(){
		return connect;
	}

	public static void close() throws SQLException {
		connect.close();
		
	}
	
}
