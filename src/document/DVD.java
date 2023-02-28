package document;

public class DVD extends AbstractDocument {
	private boolean adulte;

	
	public DVD(int numero, String titre, boolean adulte) {
		super(numero,titre);
		this.adulte = adulte;
	}


	@Override
	public void reservationPour(Abonne ab) {
		assert(super.estReserver()==false &&super.estEmprunter()==false );
		if(adulte==true) {
			assert(ab.getAge()>=16);
		}
		super.setReserveur(ab);
		
	}
	
	@Override
	public void empruntPar(Abonne ab) {
		assert(super.reserveur()==ab || super.estEmprunter()==false );
//		emprunter=true; setEmprunteur( à rajouter)
//		emprunteur=ab;
	}

}