package document;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import abonne.Abonne;
import bd.RequetesBD;
import mediatheque.IDocument;
import mediatheque.Mediatheque;

public class Document implements IDocument {
	private final static String email = "testjava053@gmail.com";
	private int numero;
	private String titre;
	private Abonne emprunteur;
	private Abonne reserveur;
	private Date reservationDate;
	private Date empruntDate;
	private boolean etat;
	private Timer timer;
	private boolean sendMailWhenAvailable;
	public Document(int num,String titre, Abonne ab) {
		this.numero=num;
		this.titre = titre;
		this.etat=true;
		emprunteur=ab;
		reserveur=null;
		reservationDate = null;
		timer=null;
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
<<<<<<< Updated upstream
		if(emprunteur!=null && emprunteur.equals(ab))
=======
		if(emprunteur!=null && ab.equals(emprunteur))
>>>>>>> Stashed changes
			throw new RestrictionException("Vous avez déjà emprunté ce "+ this.getClass().getSimpleName());
		if(emprunteur!=null)
			throw new RestrictionException("Ce "+ this.getClass().getSimpleName() +" est déjà emprunté");
		if(reserveur!=null && reserveur!=ab) {
			int heure = reservationDate.getHours()+2;
			int minutes = reservationDate.getMinutes();
			throw new RestrictionException("Ce "+ this.getClass().getSimpleName() +" est déja réservé juqu'à "+heure+"h"+minutes+ " par un autre abonné.");
		}
		if(reserveur!=null && reserveur==ab) {
			throw new RestrictionException("Vous avez déjà réserver ce "+ this.getClass().getSimpleName());
		}
		this.setReserveur(ab);

	}
	
	private void setReserveur(Abonne ab) {
		assert(ab!=null);
		this.reserveur = ab;
		reservationDate = new Date();
		planifierAnnulationReservation();
	}

	private void planifierAnnulationReservation() {
		timer = new Timer();
		timer.schedule(new AnnulationReservation(),30 * 1000); // Annuler la réservation après 1 heure (120 min * 60 sec * 1000 ms)
	}
	
	@Override
	public void setSendMailTrue() {
		if(reserveur==null && emprunteur==null) {
			this.sendMailWhenAvailable = true;
			this.sendMail();
		}
		this.sendMailWhenAvailable=true;
	}
	private void annulerReservation() {
		this.sendMail();
		this.reserveur = null;
		reservationDate = null;
		timer.cancel();
		timer.purge();
		
	}

	private class AnnulationReservation extends TimerTask {
		public void run() {
			annulerReservation();
		}
	}

	@Override
	public void empruntPar(Abonne ab) throws RestrictionException {
<<<<<<< Updated upstream
		if(emprunteur!=null && emprunteur.equals(ab))
=======
		if(emprunteur!=null && ab.equals(emprunteur))
>>>>>>> Stashed changes
			throw new RestrictionException("Vous avez déjà emprunté ce "+ this.getClass().getSimpleName());
		if(reserveur!=null && reserveur!=ab) {
			int heure = reservationDate.getHours()+2;
			int minutes = reservationDate.getMinutes();
			throw new RestrictionException("Ce "+ this.getClass().getSimpleName() +" est déja réservé juqu'à "+heure+"h"+minutes+ " par un autre abonné.");
		}
		if(emprunteur!=null)
			throw new RestrictionException("Ce "+ this.getClass().getSimpleName() +" est déjà emprunté");
		this.setEmprunteur(ab);
	}

	private void setEmprunteur(Abonne ab) {
		assert(ab!=null);
		if(timer!=null)
			timer.cancel();
		this.emprunteur=ab;
<<<<<<< Updated upstream
		this.reserveur = null;
=======
		empruntDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(empruntDate);
		cal.add(Calendar.MINUTE,1);
		empruntDate = cal.getTime();
>>>>>>> Stashed changes
		RequetesBD.setEmprunteur(this.numero, ab.getId());
	}

	@Override
	public void retour() {
		if(timer!=null)
			timer.cancel();
		this.sendMail();
		RequetesBD.setEmprunteur(this.numero, null);
		emprunteur=null;
		reserveur=null;		
	}

<<<<<<< Updated upstream
	private void sendMail() {
		if(this.sendMailWhenAvailable) {
			Mediatheque.sendEmail(email,this.numero);
			this.sendMailWhenAvailable = false;
		}
	}

=======
	public void mauvaisEtat() {
		this.etat=false;
	}
	
	public void setRetour() {
		Date today=new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(empruntDate);
		cal.add(Calendar.MINUTE,1);
		empruntDate = cal.getTime();
	}
	public Date dateRetour() {
		return empruntDate;
	}
>>>>>>> Stashed changes
	public String toString() {
		return "numero : "+numero+" titre : "+titre;

	}

	


}