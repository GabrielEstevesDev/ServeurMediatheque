package db;
import bttp.bttp;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testRequetes {

	@Test
	void test() {
		String s = requetes.getAllDocuments();
		System.out.println(bttp.decoder(bttp.encoder(s)));
	
	}

}
