package mediatheque;

import java.util.Date;

import document.IDocument;
import document.RestrictionException;

public class ConcurrentDocument implements IDocument {
	private IDocument monDoc;
	
	public ConcurrentDocument (IDocument doc) {
		this.monDoc=doc;
	}

	@Override
	public int numero() {
		return monDoc.numero();
	}

	@Override
	public Abonne emprunteur() {
		return monDoc.emprunteur();
	}

	@Override
	public Abonne reserveur() {
		return monDoc.reserveur();
	}

	@Override
	public void reservationPour(Abonne ab) throws RestrictionException {
		synchronized (monDoc) {
			monDoc.reservationPour(ab);
		}
		
	}

	@Override
	public void empruntPar(Abonne ab) throws RestrictionException {
		synchronized (monDoc) {
			monDoc.empruntPar(ab);
		}
		
	}

	@Override
	public void retour() {
		synchronized (monDoc) {
			monDoc.retour();
		}
		
	}
	@Override 
	public String toString() {
		return monDoc.toString();
	}

	@Override
	public void setSendMailTrue() {
		synchronized(monDoc) {
			monDoc.setSendMailTrue();
		}
		
	}
	public Date dateRetour() {
		synchronized (monDoc) {
			return monDoc.dateRetour();
		}
	}

	
	public void mauvaisEtat() {
		synchronized (monDoc) {
			monDoc.mauvaisEtat();
		}
		
	}


	public void setRetour() {
		synchronized(monDoc) {
			monDoc.setRetour();
		}
	}
}
