package Mediatheque;

import java.util.ArrayList;
import java.util.List;
import document.Abonne;
import document.DVD;
import document.Document;

public class Mediatheque {
	private List<Abonne> abos;
	private List<Document> docs;
	
	
	public Mediatheque() {
		abos = new ArrayList<>();
		docs = new ArrayList<>();
	}

}
