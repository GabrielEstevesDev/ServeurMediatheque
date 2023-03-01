package main;

import java.sql.*;

import Mediatheque.Mediatheque;
import document.Abonne;


public class main {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Mediatheque m=new Mediatheque();
		Class.forName("com.mysql.cj.jdbv.Driver");
		Connection connect= DriverManager.getConnection("jdbc:mysql://localhost:3306:java1","root","");
		String sql="SELECT * FROM Abonne";
		Statement req1 = connect.createStatement ( ) ;
		PreparedStatement req2 = connect.prepareStatement ( sql) ;
		CallableStatement req3 = connect. prepareCall ( sql) ;
		ResultSet res = req1.executeQuery(sql);
		while(res.next()) {
			int num = res.getInt("id");
			String nom = res.getString("nom");
			Date naissance = res.getDate("naissance");
			Abonne a=new Abonne(num,nom,naissance);
			m.AddAbo(a);
		}
		connect.close ( );
		res.close ( );

	}
}
