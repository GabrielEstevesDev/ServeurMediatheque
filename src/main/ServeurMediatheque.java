package main;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import bd.RequetesBD;
import bserveur.Serveur;
import mediatheque.Abonne;
import mediatheque.IDocument;
import mediatheque.Mediatheque;
import services.ServiceEmp;
import services.ServiceRes;
import services.ServiceRet;

public class ServeurMediatheque {
	private static int PORTRes = 3000;
	private static int PORTEmp = 4000;
	private static int PORTRet = 5000;
		public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
			List<Abonne> ListAbo=RequetesBD.getAllAbonne(); //on charge tous les abonnées
			List<IDocument> ListDoc=RequetesBD.getAllDocuments(); //on charge tous les documents
			Mediatheque.setMediatheque(ListAbo, ListDoc);
			new Thread(new Serveur(ServiceRes.class,PORTRes)).start(); //lancement du service réservation
			new Thread(new Serveur(ServiceEmp.class,PORTEmp)).start(); //lancement du service emprunt
			new Thread(new Serveur(ServiceRet.class,PORTRet)).start(); //lancement du service retour
		}
		
	}
	

