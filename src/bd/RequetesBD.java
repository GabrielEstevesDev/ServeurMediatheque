package bd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import document.DVD;
import mediatheque.Abonne;
import mediatheque.ConcurrentDocument;

public class RequetesBD {

	public static List<ConcurrentDocument> getAllDocuments() {
		try {
			Connection connexion = ConnexionBD.getConnexion();
			Statement statement = connexion.createStatement();
			String selectSql = "SELECT * FROM dvd";
			ResultSet resultSet = statement.executeQuery(selectSql);
			List<ConcurrentDocument> L=new ArrayList<ConcurrentDocument>();
			while (resultSet.next()) {
				Abonne ab=selectAbonne(resultSet.getInt(4));
				ConcurrentDocument a =new ConcurrentDocument(new DVD(resultSet.getInt(1), resultSet.getString(2), resultSet.getBoolean(3), ab));
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
			System.out.println("aaaa");
			String update = "UPDATE dvd SET emprunteur = " + numAb + " WHERE id = " + numDoc;
			statement.executeUpdate(update);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeConnexion() throws SQLException {
		ConnexionBD.close();
	}
	

}
