package main;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import Mediatheque.Mediatheque;
import Services.ServiceRes;
import bserveur.Serveur;
import db.requetes;
import document.Abonne;
import document.Document;

public class ServeurMediatheque {
	private static int PORTRes = 3000;
	private static int PORTEmp = 4000;
	private static int PORTRet = 5000;
	private static Mediatheque Med;
		public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
			List<Abonne> ListAbo=requetes.getAllAbonne();
			List<Document> ListDoc=requetes.getAllDocuments();
			Mediatheque.setMediatheque(ListAbo, ListDoc);
			Serveur serv = new Serveur(ServiceRes.class,PORTRes);
			new Thread(serv).start();
			//new Thread(new Serveur(ServiceRes.class,PORTEmp)).start();
			//new Thread(new Serveur(ServiceRes.class,PORTRet)).start();
		}
		
	}
	

