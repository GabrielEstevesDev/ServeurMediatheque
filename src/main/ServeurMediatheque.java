package main;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import abonne.Abonne;
import bd.RequetesBD;
import bserveur.Serveur;
import mediatheque.IDocument;
import mediatheque.Mediatheque;
import services.ServiceEmp;
import services.ServiceRes;
import services.ServiceRet;

public class ServeurMediatheque {
	private static int PORTRes = 3000;
	private static int PORTEmp = 4000;
	private static int PORTRet = 5000;
	private static Mediatheque Med;
		public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
			List<Abonne> ListAbo=RequetesBD.getAllAbonne();
			List<IDocument> ListDoc=RequetesBD.getAllDocuments();
			Mediatheque.setMediatheque(ListAbo, ListDoc);
			new Thread(new Serveur(ServiceRes.class,PORTRes)).start();
			new Thread(new Serveur(ServiceEmp.class,PORTEmp)).start();
			new Thread(new Serveur(ServiceRet.class,PORTRet)).start();
		}
		
	}
	

