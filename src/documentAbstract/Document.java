package documentAbstract;

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
	private boolean sendMailWhenAvailable; //quand == true et que le doc devient dispo on envoit un mail
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
		if(ab.equals(emprunteur)) // si emprunteur == ab
			throw new RestrictionException("Vous avez déjà emprunté ce "+ this.getClass().getSimpleName());
		else if(emprunteur!=null) // si déja emprunté par un autre abonné
			throw new RestrictionException("Ce "+ this.getClass().getSimpleName() +" est déjà emprunté");
		else if(ab.equals(reserveur)) {
			throw new RestrictionException("Vous avez déjà réserver ce "+ this.getClass().getSimpleName());
		}
		else if(reserveur!=null) { // si réservé par un autre abonné
			int heure = reservationDate.getHours()+2;
			int minutes = reservationDate.getMinutes();
			throw new RestrictionException("Ce "+ this.getClass().getSimpleName() +" est déja réservé juqu'à "+heure+"h"+minutes+ " par un autre abonné.");
		}
		this.setReserveur(ab); // sinon on sauvegarde le reserveur
	}
	
	private void setReserveur(Abonne ab) {
		assert(ab!=null);
		this.reserveur = ab;
		this.reservationDate = new Date(); //on sauvegarde la date de réservation
		planifierAnnulationReservation(); // lancement du timer de 2h
	}

	private void planifierAnnulationReservation() {
		timerReservation = new Timer();
		timerReservation.schedule(new AnnulationReservation(),120*60 * 1000); // Annuler la réservation après 2 heure (120 min * 60 sec * 1000 ms)
	}
	//au bout de 2h
	private class AnnulationReservation extends TimerTask {
		public void run() {
			annulerReservation();
		}
	}
	
	private void annulerReservation() {
		this.sendMail();//méthode gérant l'envoi du mail ou non
		this.reserveur = null;
		this.reservationDate = null;
		timerReservation.cancel();
		timerReservation.purge();
	}
	@Override
	//permet de mettre l'attribut à true et vérifie si le doc est devenu disponbile entre temps
	public void setSendMailTrue() {
		this.sendMailWhenAvailable = true;
		if(reserveur==null && emprunteur==null) { //if permettant de vérifier si le document est devenu disponible entre le temps de réponse de l'utilisateur
		this.sendMail(); //doc disponible donc on appelle la méthode sendMail qui va obligatoirement envoyé le mail cette fois.
		}
	}
	
	//méthode appelé quand l'état du doc devient disponible et décide si on envoit un mail ou non
	private void sendMail() {
		if(this.sendMailWhenAvailable){// si l'utilisateur attend un mail {
			Mediatheque.sendEmail(this.numero); // on l'envoit
			this.sendMailWhenAvailable = false; 
		}
		//sinon on ne fait rien
	}	
	

	

	@Override
	public void empruntPar(Abonne ab) throws RestrictionException {
		if(ab.equals(emprunteur))// si ab l'a déjà emprunté
			throw new RestrictionException("Vous avez déjà emprunté ce "+ this.getClass().getSimpleName());
		else if(emprunteur !=null)// si il est déja emprunté
			throw new RestrictionException("Ce "+ this.getClass().getSimpleName() +" est déjà emprunté");
		else if(reserveur!=null && !ab.equals(reserveur)) { // si il est réservé par un autre ab
			int heure = reservationDate.getHours()+2;
			int minutes = reservationDate.getMinutes();
			throw new RestrictionException("Ce "+ this.getClass().getSimpleName() +" est réservé juqu'à "+heure+"h"+minutes+ " par un autre abonné.");
		}
		// sinon (si il l'a réservé ou qu'il n'est pas réservé par un autre abonné)
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
		cal.add(Calendar.WEEK_OF_YEAR,1); // nous lui ajoutons 1 semaines pour indiquer à l'abonne le délai restant
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
		cal.add(Calendar.WEEK_OF_YEAR,2); // nous lui ajoutons 2 semaines suplémentaires
		retourEmpruntDate = cal.getTime();
		Date today = new Date();
		return today.after(retourEmpruntDate); //nous retournons true si le document est rendu avec plus de deux semaine de retard
	}

	@Override
	public boolean getBonEtat() {
		return this.bonEtat;
	}

	
	


}