package test;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

import document.Abonne;
import document.DVD;
import document.Document;

class abonnetest {

	@Test
	void test() {
		String date_string = "29-02-2003";
	       //Instantiating the SimpleDateFormat class
	       SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");      
	       //Parsing the given String to Date object
	       Date date = null;
		try {
			date = formatter.parse(date_string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Abonne a =new Abonne(1,"Yvann",date);
		System.out.println(a.getAge());
	}
	
	public Date stringToDate(String d) {
	       //Instantiating the SimpleDateFormat class
	       SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");      
	       //Parsing the given String to Date object
	       Date date = null;
		try {
			date = formatter.parse(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	@Test
	void testReserver() {
		Date g = this.stringToDate("20/02/2003");
		Abonne gabriel = new Abonne(1,"Gabriel",g);
		Date l = this.stringToDate("20/02/20010");
		Abonne louis = new Abonne(1,"Gabriel",l);
		Document DVD1 = new DVD(1,"spiderman",false);
		assertTrue(DVD1.emprunteur()==null);
		assertTrue(DVD1.reserveur()==null);
		DVD1.reservationPour(gabriel);
		assertTrue(DVD1.reserveur()==gabriel);
		DVD1.empruntPar(gabriel);
		assertTrue(DVD1.emprunteur()==gabriel);
		
		
	}

}
