package document;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import db.requetes;

public class Document implements IDocument {
	private int numero;
	private String titre;
	private Abonne emprunteur;
	private Abonne reserveur;
	private Date reservationDate;
	private Timer timer;
	
	public Document(int num,String titre) {
		this.numero=num;
		this.titre = titre;
		emprunteur=null;
		reserveur=null;
		reservationDate = null;
		timer=null;
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
		if(emprunteur!=null)
			throw new RestrictionException("Ce "+ this.getClass().getSimpleName() +" est déjà emprunté");
		if(reserveur!=null && reserveur!=ab) {
			int heure = reservationDate.getHours()+2;
			int minutes = reservationDate.getMinutes();
			throw new RestrictionException("Ce "+ this.getClass().getSimpleName() +" est déja réservé juqu'à "+heure+"h"+minutes+ " par un autre abonné.");
		}
		if(reserveur==ab) {
			throw new RestrictionException("Vous avez déjà réservé ce "+ this.getClass().getSimpleName() +"");
		}
		this.setReserveur(ab);
		
	}
	private void setReserveur(Abonne ab) {
		this.reserveur = ab;
		reservationDate = new Date();
		planifierAnnulationReservation();
    }

    private void planifierAnnulationReservation() {
        timer = new Timer();
        timer.schedule(new AnnulationReservation(),120* 60 * 1000); // Annuler la réservation après 1 heure (120 min * 60 sec * 1000 ms)
    }

    private void annulerReservation() {
        this.reserveur = null;
        reservationDate = null;
        timer.cancel();
        timer.purge();
        System.out.println("La réservation a été annulée.");
    }

    private class AnnulationReservation extends TimerTask {
        public void run() {
            annulerReservation();
        }
    }

	@Override
	public void empruntPar(Abonne ab) throws RestrictionException {
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
		this.emprunteur=ab;
		requetes.setEmprunteur(this.numero, ab.getId());
	}
	
	@Override
	public void retour() {
		requetes.setEmprunteur(this.numero, null);
		emprunteur=null;
		reserveur=null;		
	}
	
	public String toString() {
		return "numero : "+numero+" titre : "+titre;
		
	}


}