package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class requetes implements RequetesSQL{

	public void insert() {
		// TODO Auto-generated method stub
		
	}

	public void update() {
		// TODO Auto-generated method stub
		
	}

	public  void delete() {
		// TODO Auto-generated method stub
		
	}
	
	public static String getAllDocuments() {
		try {
			Connection connexion = ConnexionBD.getConnexion();
			Statement statement = connexion.createStatement();
			String selectSql = "SELECT * FROM dvd";
			ResultSet resultSet = statement.executeQuery(selectSql);
			String s = "";
			while (resultSet.next()) {
			   s+="numero: " + resultSet.getString(1) + " titre: " + resultSet.getString(2) + " adulte : " + resultSet.getString(3) + "\n²";
			}
			return s;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
