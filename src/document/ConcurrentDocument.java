package document;

import java.util.Date;

import mediatheque.Abonne;
import mediatheque.IDocument;

public class ConcurrentDocument implements IDocument {
	private IDocument monDoc;
	//classe nous permettant de synchronised tous les points critiques
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
	public void setSendMailTrue() {
		synchronized(monDoc) {
			monDoc.setSendMailTrue();
		}
		
	}
	@Override 
	public Date dateRetouremprunt() {
		synchronized (monDoc) {
			return monDoc.dateRetouremprunt();
		}
	}

	@Override 
	public void mauvaisEtat() {
		synchronized (monDoc) {
			monDoc.mauvaisEtat();
		}
		
	}

	
	@Override 
	public String toString() {
		return monDoc.toString();
	}

	@Override
	public boolean renduEnretard() {
		synchronized(monDoc) {
			return monDoc.renduEnretard();
		}
	}

	@Override
	public boolean getBonEtat() {
		synchronized (monDoc) {
			return monDoc.getBonEtat();
		}
	}
}
