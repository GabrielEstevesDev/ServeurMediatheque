package main;

import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;


import Services.ServiceRes;
import bserveur.Serveur;
import bserveur.ServiceAbstract;

public class ServeurMediatheque {
	
		public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
			new Thread(new Serveur(ServiceRes.class,3000)).start();
		}
	}
	

