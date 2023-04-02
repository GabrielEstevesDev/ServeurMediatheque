package mediatheque;

import java.util.Date;

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
	//attribut à true pour envoyer mail à un utilisateur quand le document sera disponbile
	void setSendMailTrue();
	
	//retourne la date de retour attendu pour le document
	Date dateRetouremprunt();
	
	//attribut à false le boolean bonetat du document
	void mauvaisEtat();
	
	//return true si le document est rendu avec un retard de plus de 2 semaines
	boolean renduEnretard();
	//return true si le document est en bon etat
	boolean getBonEtat();
}
