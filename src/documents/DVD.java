package documents;

import documentAbstract.Document;
import mediatheque.Abonne;
import mediatheque.RestrictionException;

public class DVD extends Document {
	private boolean pourAdulte;


	public DVD(int numero, String titre, boolean adulte, Abonne ab) {
		super(numero,titre,ab);
		this.pourAdulte = adulte;
	}

	
	@Override
	public void reservationPour(Abonne ab) throws RestrictionException {
		if(pourAdulte==true && ab.getAge()<=16) {
			throw new RestrictionException("Vous n'avez pas l'âge recquis pour réserver ce DVD");
		}
		super.reservationPour(ab); // appel de la méthode du document car seul différence c'est la vérif de l'âge

	}
	
	@Override
	public void empruntPar(Abonne ab) throws RestrictionException {
		if(pourAdulte==true && ab.getAge()<=16) 
			throw new RestrictionException("Vous n'avez pas l'âge recquis pour emprunter ce DVD");
		super.empruntPar(ab);  // appel de la méthode du document car seul différence c'est la vérif de l'âge
	}

	@Override
	public String toString() {
		String s = "";
		if(pourAdulte) {
			s = "interdit au moins de 16 ans.";
 		}
		else s = "pas de restriction pour l'âge";
		return super.toString()+", " +s;
	}

}