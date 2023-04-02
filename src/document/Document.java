package document;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import bd.RequetesBD;
import mediatheque.Abonne;
import mediatheque.IDocument;
import mediatheque.Mediatheque;
import mediatheque.RestrictionException;

public class Document implements IDocument {
	private int numero; 
	private String titre;
	private Abonne emprunteur;
	private Abonne reserveur;
	private Date reservationDate; //date de réservation
	private Date retourEmpruntDate; // date de retour de  l'emprunt
	private boolean bonEtat; //bon ou mauvais état du document
	private Timer timerReservation; // timer lancé lors d'une réservation
	private boolean sendMailWhenAvailable; //quand document disponible envoi d'un mail
	public Document(int num,String titre, Abonne ab) {
		this.numero=num;
		this.titre = titre;
		this.bonEtat=true;
		emprunteur=ab;
		reserveur=null;
		reservationDate = null;
		timerReservation=null;
		sendMailWhenAvailable = false;
	}

	@Override
	public int numero() {
		return this.numero;
	}

	@Override
	public Abonne emprunteur() {
		return this.emprunteur;
	}
	@Override
	public Abonne reserveur() {
		return this.reserveur;
	}

	@Override
	public void reservationPour(Abonne ab) throws RestrictionException {
		if(emprunteur!=null && ab.equals(emprunteur)) // si emprunteur == emprunteur
			throw new RestrictionException("Vous avez déjà emprunté ce "+ this.getClass().getSimpleName());
		else if(emprunteur!=null) // si déja emprunté
			throw new RestrictionException("Ce "+ this.getClass().getSimpleName() +" est déjà emprunté");
		else if(reserveur!=null && ab.equals(reserveur)) {
			throw new RestrictionException("Vous avez déjà réserver ce "+ this.getClass().getSimpleName());
		}
		else if(reserveur!=null && reserveur!=ab) { // si réservé par un autre abonné
			int heure = reservationDate.getHours()+2;
			int minutes = reservationDate.getMinutes();
			throw new RestrictionException("Ce "+ this.getClass().getSimpleName() +" est déja réservé juqu'à "+heure+"h"+minutes+ " par un autre abonné.");
		}
		this.setReserveur(ab); // sinon on sauvegarde le reserveur
	}
	
	private void setReserveur(Abonne ab) {
		assert(ab!=null);
		this.reserveur = ab;
		reservationDate = new Date(); //on sauvegarde la date de réservation
		planifierAnnulationReservation(); // lancement du timer de 2h
	}

	private void planifierAnnulationReservation() {
		timerReservation = new Timer();
		timerReservation.schedule(new AnnulationReservation(),30 * 1000); // Annuler la réservation après 1 heure (120 min * 60 sec * 1000 ms)
	}
	
	private class AnnulationReservation extends TimerTask {
		public void run() {
			annulerReservation();
		}
	}
	
	private void annulerReservation() {
		this.sendMail();
		this.reserveur = null;
		reservationDate = null;
		timerReservation.cancel();
		timerReservation.purge();
	}
	@Override
	public void setSendMailTrue() {
		if(reserveur==null && emprunteur==null) { 
			this.sendMailWhenAvailable = true;
			this.sendMail(); // car ça veut dire qu'entre le temps de réponse de l'utilisateur, le doc est devenu disponible
		}
		else this.sendMailWhenAvailable=true; // sinon on met la variable à true et on attend que le doc devienne disponible
	}
	
	private void sendMail() {
		if(this.sendMailWhenAvailable){// si l'utilisateur attend un mail {
			Mediatheque.sendEmail(this.numero); // on l'envoit
			this.sendMailWhenAvailable = false; 
		}
	}	
	

	

	@Override
	public void empruntPar(Abonne ab) throws RestrictionException {
		if(emprunteur!=null && ab.equals(emprunteur))// si ab l'a déjà emprunté
			throw new RestrictionException("Vous avez déjà emprunté ce "+ this.getClass().getSimpleName());
		else if(emprunteur!=null && !ab.equals(emprunteur))// si il est déja emprunté
			throw new RestrictionException("Ce "+ this.getClass().getSimpleName() +" est déjà emprunté");
		else if(reserveur!=null && !ab.equals(reserveur)) { // si il est réservé par un autre ab
			int heure = reservationDate.getHours()+2;
			int minutes = reservationDate.getMinutes();
			throw new RestrictionException("Ce "+ this.getClass().getSimpleName() +" est déja réservé juqu'à "+heure+"h"+minutes+ " par un autre abonné.");
		}
		// sinon (si il l'a réservé ou qu'il n'est pas réservé)
		this.setEmprunteur(ab); 
	}

	private void setEmprunteur(Abonne ab) {
		assert(ab!=null);
		if(timerReservation!=null)// si le timer est lancé
			timerReservation.cancel(); //on l'arrête car il est devenu indisponible jusqu'au retour
		this.emprunteur=ab;
		this.reserveur = null; 
		retourEmpruntDate = new Date(); //nous utilisons la date d'aujourd'hui
		Calendar cal = Calendar.getInstance();
		cal.setTime(retourEmpruntDate);
		cal.add(Calendar.MINUTE,1); // nous lui ajoutons 1 semaines pour indiquer à l'abonne le délai restant
		retourEmpruntDate = cal.getTime();
		RequetesBD.setEmprunteur(this.numero, ab.getId()); // on met à jour la base de données
	}
	

	@Override
	public void retour() {
		if(timerReservation!=null) //si le timer est lancé
			timerReservation.cancel(); // on l'arrête
		this.sendMail(); // on envoit le mail si il est devenu disponible
		RequetesBD.setEmprunteur(this.numero, null); // on met à jour la base de données
		emprunteur=null;
		reserveur=null;		
	}
	@Override 
	public Date dateRetouremprunt() {
		return retourEmpruntDate;
	}
	@Override
	public void mauvaisEtat() {
		if(this.bonEtat) { //si le document était encore en bon etat
			this.bonEtat = false;  //on indique que le document est en mauvais état
			this.emprunteur.banniMois();// on bannit l'abonné pendant 1 mois
		}
	}
	@Override 
	public String toString() {
		return "numero : "+numero+" titre : "+titre;
	}

	@Override
	public boolean renduEnretard() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(retourEmpruntDate); // nous recuperons la date de rendu initiale
		cal.add(Calendar.MINUTE,1); // nous lui ajoutons 2 semaines suplémentaires
		retourEmpruntDate = cal.getTime();
		Date today = new Date();
		return today.after(retourEmpruntDate); //nous retournons true si le document est rendu avec plus de deux semaine de retard
	}

	@Override
	public boolean getBonEtat() {
		return this.bonEtat;
	}

	
	


}