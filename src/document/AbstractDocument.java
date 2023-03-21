package document;




public class AbstractDocument implements Document {
	private int numero;
	private String titre;
	private Abonne emprunteur;
	private Abonne reserveur;
	private boolean  reserver;
	private boolean  emprunter;
	
	
	public AbstractDocument(int num,String titre) {
		this.numero=num;
		this.titre = titre;
		emprunteur=null;
		reserveur=null;
		reserver=false;
		emprunter=false;
	}

	@Override
	public int numero() {
		// TODO Auto-generated method stub
		return this.numero;
	}

	@Override
	public Abonne emprunteur() {
		// TODO Auto-generated method stub
		return this.emprunteur;
		
	}
	public boolean estReserver() {
		return reserver;
	}
	public boolean estEmprunter() {
		return emprunter;
	}
	public void setReserveur(Abonne ab) {
		this.reserveur = ab;
		reserver=true;
	}
	@Override
	public Abonne reserveur() {
		// TODO Auto-generated method stub
		return this.reserveur;
	}

	@Override
	public void reservationPour(Abonne ab) throws RestrictionException {
		if(emprunter==true )
			throw new RestrictionException("Ce DVD est déjà emprunté");
		if(reserver==true)
			throw new RestrictionException("Ce DVD est déja réservé");
		reserver=true;
		this.reserveur=ab;
		
	}

	@Override
	public void empruntPar(Abonne ab) throws RestrictionException {
		if(reserveur!=ab)
			throw new RestrictionException("Ce DVD est déja réservé");
		if(emprunter==true)
			throw new RestrictionException("Ce DVD est déjà emprunté");
		emprunter=true;
		emprunteur=ab;
	}

	@Override
	public void retour() {
		emprunteur=null;
		reserveur=null;
		reserver=false;
		emprunter=false;
		
	}
	
	public String toString() {
		return "numero : "+numero+" titre : "+titre;
		
	}

}