package mediatheque;

import java.util.Date;

import abonne.Abonne;
import document.RestrictionException;

public interface IDocument {
	int numero();
	//return null si pas emprunté ou pas réservé
	Abonne emprunteur() ; // Abonné qui a emprunté ce document
	Abonne reserveur() ; // Abonné qui a réservé ce document
	//pre ni réservé ni emprunté
	void reservationPour(Abonne ab) throws RestrictionException;
	//pre libre ou réservé par l’abonné qui vient emprunter
	void empruntPar(Abonne ab) throws RestrictionException;
	//brief retour d’un document ou annulation d‘une réservation
	void retour();
<<<<<<< Updated upstream
	
	void setSendMailTrue();
=======
	Date dateRetour();
	void mauvaisEtat();
	 void setRetour();
>>>>>>> Stashed changes
}
