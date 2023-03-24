package mediatheque;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import abonne.Abonne;
import document.DVD;

class testMediatheque {

	@Test
	void test() {
		List<Abonne> abos = List.of(new Abonne(1, "Gabriel", null));
		List<IDocument> docs = List.of(new DVD(1, "FAF", false,null));
		Mediatheque.setMediatheque(abos, docs);
		System.out.println(Mediatheque.getDoc(1));
	}

}
