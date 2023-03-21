package Mediatheque;

import java.util.ArrayList;
import java.util.List;
import document.Abonne;
import document.DVD;
import document.Document;

public class Mediatheque {
	private static List<Abonne> abos;
	private static List<Document> docs;
	
	
	public static void setMediatheque(List<Abonne> abos1,List<Document> docs1) {
		abos = abos1;
		docs = docs1;
	}
	
	public static Abonne getAbo(int i) {
		for(Abonne ab :abos) {
			if(ab.getId()==i) {
				return ab;
			}
		}
		return null;
	}

	public static Document getDoc(int numDoc) {
		for(Document doc :docs) {
			if(doc.numero()==numDoc) {
				return doc;
			}
		}
		return null;
	}
	

}
