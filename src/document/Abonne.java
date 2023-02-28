package document;

import java.util.Date;

public class Abonne {
	private int num;
	private String nom;
	private Date naissance;
	
	public Abonne(int numero, String nom, Date naissance) {
		this.num = numero;
		this.nom=nom;
		this.naissance=naissance;
	}

	@SuppressWarnings("deprecation")
	public int getAge() {
		Date now = new Date();
        int age = now.getYear() - naissance.getYear();
        if (now.getMonth() < naissance.getMonth() ||
            (now.getMonth() == naissance.getMonth() && now.getDate() < naissance.getDate())) {
            age--;
        }
		return age;
	}
}
