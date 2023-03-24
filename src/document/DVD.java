package document;

import abonne.Abonne;

public class DVD extends Document {
	private boolean adulte;


	public DVD(int numero, String titre, boolean adulte, Abonne ab) {
		super(numero,titre,ab);
		this.adulte = adulte;
	}


	@Override
	public void reservationPour(Abonne ab) throws RestrictionException {
		if(adulte==true && ab.getAge()<=16) {
			throw new RestrictionException("Vous n'avez pas l'âge recquis pour réserver ce DVD");
		}
		super.reservationPour(ab);

	}
	
	@Override
	public void empruntPar(Abonne ab) throws RestrictionException {
		if(adulte==true && ab.getAge()<=16) 
			throw new RestrictionException("Vous n'avez pas l'âge recquis pour emprunter ce DVD");
		super.empruntPar(ab);
	}

	@Override
	public String toString() {
		return super.toString()+" Adulte : "+adulte;
	}

}