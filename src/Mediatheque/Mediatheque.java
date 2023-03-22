package Mediatheque;

import java.util.ArrayList;
import java.util.List;
import document.Abonne;
import document.DVD;
import document.IDocument;

public class Mediatheque {
	private static List<Abonne> abos;
	private static List<IDocument> docs;
	
	
	public static void setMediatheque(List<Abonne> abos1,List<IDocument> docs1) {
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

	public static IDocument getDoc(int numDoc) {
		for(IDocument doc :docs) {
			if(doc.numero()==numDoc) {
				return doc;
			}
		}
		return null;
	}
	
	public static String afficherDocs() {
		String s = "";
		for(IDocument doc: docs) {
			s+=doc.toString()+"\n";
		}
		return s;
	}

}
