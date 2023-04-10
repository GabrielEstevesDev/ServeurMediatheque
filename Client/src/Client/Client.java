package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Proxy;
import java.net.Socket;

import bttp.bttp;

public class Client {
	public static void main(String[] args) {
		BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));//permet la saisie clavier            
	    Socket socket = null;        
	    try {
	        String rep; //réponse de l'utilisateur
	        System.out.println("Entrez l'url de connexion au serveur de cette façon <<BTTP:host:port>>");
	        System.out.print("->");
		    rep = clavier.readLine(); //saisie clavier de l'host et du port
	        String[] parts = rep.split(":");//on prend l'host et le port
	        String host = parts[1];//host
	        String stringPort = parts[2]; // le string du port
	        int port = Integer.parseInt(stringPort); // conversion en int
	        socket = new Socket(host, port);
	        BufferedReader sin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        PrintWriter sout = new PrintWriter (socket.getOutputStream ( ), true);
	        // Informe l'utilisateur de la connection
	        System.out.println("Connecté au serveur " + socket.getInetAddress() + ":"+ socket.getPort());
	        while(true) {
	        	String service = bttp.decoder(sin.readLine());//échange ping 
	        	System.out.println(service);
	        	if(service.contains("?")){//si la phrase contient ? ceci implique une réponse de l'utilisateur
	        		 System.out.print("->");
	  		        rep = clavier.readLine();
	  		        sout.println(bttp.encoder(rep)); //échange pong
	        	}else {// sinon 
	        		socket.close(); //fin du service
	        	}
	        }
	    }
	    catch (IOException e) { 
	        System.err.println("Fin du service"); 
	    }
	    finally {
	        // Refermer dans tous les cas la socket
	        try { 
	            if (socket != null) socket.close(); 
	        } 
	        catch (IOException e2) { 
	            System.err.println("Erreur lors de la fermeture de la socket du client"); 
	        }
	    }
	}
}
