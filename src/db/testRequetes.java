package db;
import bttp.bttp;
import document.Abonne;

import java.util.List;

import org.junit.jupiter.api.Test;

class testRequetes {

	@Test
	void test() {
		//List<document.Document> s = requetes.getAllDocuments();
		String s="Gabriel sans stage \naaaaa";
		System.out.println(bttp.decoder(bttp.encoder(s)));
		List<Abonne> a = requetes.getAllAbonne();
		for(Abonne b:a) {
			System.out.println(b);
		}
	}

}
