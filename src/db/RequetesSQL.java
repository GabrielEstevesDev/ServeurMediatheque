package db;

import java.sql.SQLException;

public interface RequetesSQL {
	void insert();
	
	void update();
	
	void delete();
	
	void closeConnexion() throws SQLException;
	
}
