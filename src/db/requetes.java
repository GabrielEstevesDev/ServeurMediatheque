package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import document.Abonne;
import document.DVD;
import document.Document;

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
	
	public static List<Document> getAllDocuments() {
		try {
			Connection connexion = ConnexionBD.getConnexion();
			Statement statement = connexion.createStatement();
			String selectSql = "SELECT * FROM dvd";
			ResultSet resultSet = statement.executeQuery(selectSql);
			List<Document> L=new ArrayList<Document>();
			while (resultSet.next()) {
				Document a =new DVD(resultSet.getInt(1), resultSet.getString(2), resultSet.getBoolean(3));
				L.add(a);
			}
			return L;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static List<Abonne> getAllAbonne() {
		try {
			Connection connexion = ConnexionBD.getConnexion();
			Statement statement = connexion.createStatement();
			String selectSql = "SELECT * FROM abonne";
			ResultSet resultSet = statement.executeQuery(selectSql);
			List<Abonne> L=new ArrayList<Abonne>();
			while (resultSet.next()) {
			   Abonne a =new Abonne(resultSet.getInt(1), resultSet.getString(2),resultSet.getDate(3) );
			   L.add(a);
			}
			return L;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static Abonne selectAbonne(int i) {
		try {
			Connection connexion = ConnexionBD.getConnexion();
			Statement statement = connexion.createStatement();
			String selectSql = "SELECT * FROM abonne Where id="+i;
			ResultSet resultSet = statement.executeQuery(selectSql);
			Abonne  a = null;
			while (resultSet.next()) {
				   a =new Abonne(resultSet.getInt(1), resultSet.getString(2),resultSet.getDate(3) );
				}
			return a;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static Document selectDocument(int i) {
		try {
			Connection connexion = ConnexionBD.getConnexion();
			Statement statement = connexion.createStatement();
			String selectSql = "SELECT * FROM dvd Where id="+i;
			ResultSet resultSet = statement.executeQuery(selectSql);
			Document  a = null;
			while (resultSet.next()) {
				   a =new DVD(resultSet.getInt(1), resultSet.getString(2), resultSet.getBoolean(3));
				}
			return a;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
