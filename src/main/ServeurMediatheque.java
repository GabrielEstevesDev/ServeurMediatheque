package main;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import Mediatheque.Mediatheque;
import Services.ServiceEmp;
import Services.ServiceRes;
import Services.ServiceRet;
import bserveur.Serveur;
import db.requetes;
import document.Abonne;
import document.IDocument;

public class ServeurMediatheque {
	private static int PORTRes = 3000;
	private static int PORTEmp = 4000;
	private static int PORTRet = 5000;
	private static Mediatheque Med;
		public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
			List<Abonne> ListAbo=requetes.getAllAbonne();
			List<IDocument> ListDoc=requetes.getAllDocuments();
			Mediatheque.setMediatheque(ListAbo, ListDoc);
			new Thread(new Serveur(ServiceRes.class,PORTRes)).start();
			new Thread(new Serveur(ServiceEmp.class,PORTEmp)).start();
			new Thread(new Serveur(ServiceRet.class,PORTRet)).start();
		}
		
	}
	

