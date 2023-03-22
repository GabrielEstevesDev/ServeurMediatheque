package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import document.Abonne;
import document.ConcurrentDocument;
import document.DVD;
import document.IDocument;

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
	
	public static List<IDocument> getAllDocuments() {
		try {
			Connection connexion = ConnexionBD.getConnexion();
			Statement statement = connexion.createStatement();
			String selectSql = "SELECT * FROM dvd";
			ResultSet resultSet = statement.executeQuery(selectSql);
			List<IDocument> L=new ArrayList<IDocument>();
			while (resultSet.next()) {
				IDocument a =new ConcurrentDocument(new DVD(resultSet.getInt(1), resultSet.getString(2), resultSet.getBoolean(3)));
				L.add(a);
			}
			resultSet.close();
			statement.close();
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
	
	public static void setEmprunteur(int numDoc, Integer numAb) {
	    try {
	        Connection connexion = ConnexionBD.getConnexion();
	        Statement statement = connexion.createStatement();
	        String update = "UPDATE dvd SET emprunteur = " + numAb + " WHERE id = " + numDoc;
	        statement.executeUpdate(update);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void closeConnexion() throws SQLException {
		ConnexionBD.close();
		
	}

}
