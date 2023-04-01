package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

 public class ConnexionBD {
	private static Connection connect;
	static { //block static pour la connexion à la base de donnée
		try {
			Class.forName ("com.mysql.cj.jdbc.Driver");
			try {
				 connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/mediatheque", "root", ""); //utilisation du driver jdbc
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException("bug to connect at database");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("driver jdbc not found");
		}
		
	}
	
	protected static Connection getConnexion(){
		return connect; //on retourne la connexion avec une visibilité package
	}

	protected static void close() throws SQLException {
		connect.close();
		
	}
	
}
