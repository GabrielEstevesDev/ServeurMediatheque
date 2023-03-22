package document;

public class DVD extends AbstractDocument {
	private boolean adulte;


	public DVD(int numero, String titre, boolean adulte) {
		super(numero,titre);
		this.adulte = adulte;
	}


	@Override
	public void reservationPour(Abonne ab) throws RestrictionException {
		if(super.estEmprunter()==true)
			throw new RestrictionException("Ce DVD est déjà emprunté");
		if(super.estReserver()==true)
			throw new RestrictionException("Ce DVD est déjà réservé");
		if(adulte==true && ab.getAge()<=16) {
			throw new RestrictionException("Vous n'avez pas l'âge recquis pour ce DVD");
		}
		super.setReserveur(ab);

	}

	@Override
	public String toString() {
		return super.toString()+" Adulte : "+adulte;
	}

}