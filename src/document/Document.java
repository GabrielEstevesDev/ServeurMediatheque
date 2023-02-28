package document;

public interface Document {
	int numero();
	//return null si pas emprunté ou pas réservé
	Abonne emprunteur() ; // Abonné qui a emprunté ce document
	Abonne reserveur() ; // Abonné qui a réservé ce document
	//pre ni réservé ni emprunté
	void reservationPour(Abonne ab) ;
	//pre libre ou réservé par l’abonné qui vient emprunter
	void empruntPar(Abonne ab);
	//brief retour d’un document ou annulation d‘une réservation
	void retour();
}
