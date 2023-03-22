package db;
import bttp.bttp;
import document.Abonne;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
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
	
	@SuppressWarnings("deprecation")
	@Test
	void test2() {
		//List<document.Document> s = requetes.getAllDocuments();
		
        LocalTime heureCourante = LocalTime.now();
        System.out.println(new Date().getHours());
        int heure = heureCourante.getHour();
        heure+=2;
        int minutes = heureCourante.getMinute();
        System.out.println("Heure courante : " + heure + ":" + minutes);

	}
	
	@Test
	void test3(){
		requetes.setEmprunteur(2, 2);
	}
	
	
	

}
