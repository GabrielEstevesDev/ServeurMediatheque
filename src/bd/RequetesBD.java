package bd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import documentAbstract.ConcurrentDocument;
import documents.DVD;
import mediatheque.Abonne;
import mediatheque.IDocument;

public class RequetesBD {
	//Méthode qui retourne tous les documents de la médiathèque
	public static List<IDocument> getAllDocuments() {
		try {
			Connection connexion = ConnexionBD.getConnexion();
			Statement statement = connexion.createStatement();
			String selectSql = "SELECT * FROM document";
			ResultSet resultSet = statement.executeQuery(selectSql);
			List<IDocument> L=new ArrayList<IDocument>();
			while (resultSet.next()) {
				Abonne ab=selectAbonne(resultSet.getInt(4));
				IDocument a =new ConcurrentDocument(new DVD(resultSet.getInt(1), resultSet.getString(2), resultSet.getBoolean(3), ab));
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
	//Méthode qui retourne l'abonne avec l'id égale à i
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
			resultSet.close();
			statement.close();
			return a;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//Méthode qui retourne tous les abonnes de la médiathèque
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
			resultSet.close();
			statement.close();
			return L;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	//Méthode qui modifie l'emprunteur d'un document spécifié
	public static void setEmprunteur(int numDoc, Integer numAb) {
		try {
			Connection connexion = ConnexionBD.getConnexion();
			Statement statement = connexion.createStatement();
			String update = "UPDATE document SET emprunteur = " + numAb + " WHERE id = " + numDoc;
			statement.executeUpdate(update);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeConnexion() throws SQLException {
		ConnexionBD.close();
	}
	

}
