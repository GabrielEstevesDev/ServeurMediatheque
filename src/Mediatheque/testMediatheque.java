package Mediatheque;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import document.Abonne;
import document.DVD;
import document.Document;

class testMediatheque {

	@Test
	void test() {
		List<Abonne> abos = List.of(new Abonne(1, "Gabriel", null));
		List<Document> docs = List.of(new DVD(1, "FAF", false));
		Mediatheque.setMediatheque(abos, docs);
		System.out.println(Mediatheque.getDoc(1));
	}

}
