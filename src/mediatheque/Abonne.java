package mediatheque;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;


public class Abonne {
	private int num;
	private String nom;
	private Date naissance;
	private Date dateBannissement;
	
	public Abonne(int numero, String nom, Date naissance) {
		this.num = numero;
		this.nom=nom;
		this.naissance=naissance;
		this.dateBannissement = null;
	}

	@SuppressWarnings("deprecation")
	public int getAge() {
		Date now = new Date();
        int age = now.getYear() - naissance.getYear();
        if (now.getMonth() < naissance.getMonth() ||
            (now.getMonth() == naissance.getMonth() && now.getDate() < naissance.getDate())) {
            age--;
        }
		return age;
	}
	
	public int getId() {
		return num;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(num);
	}

	@Override
	public boolean equals(Object obj) { // return true si le numéro est le même
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Abonne other = (Abonne) obj;
		return num == other.num;
	}

	public void banniMois() { //bannis l'abonne 1 mois
		if(dateBannissement==null) { //si l'abonne n'est pas déjà bannis
			dateBannissement=new Date();//on calcule la date d'aujourd'hui
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateBannissement);
			cal.add(Calendar.MONTH, 1); //nous lui ajoutons 1 mois
			dateBannissement=cal.getTime(); //nous l'attribuons à l'abonne 
		}
		
		
	}
	
	public Date dateBannissement() {
		return dateBannissement;
	}
	
	
	@Override 
	public String toString() {
		return "num : "+this.num + " nom :"+ this.nom + " naissance : " + this.naissance;
	}

	public void estBannis() throws AbonneBanisException {
		Date today = new Date(); 
		if(dateBannissement!=null && dateBannissement.after(today)) { //si la date de bannissement est toujours d'acualité
			Date date = dateBannissement;
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date); //nous renvoyons une erreur et indiquons que l'abonne est toujours bannis
			throw new AbonneBanisException("Vous êtes toujours bannis jusqu'au "+calendar.get(GregorianCalendar.DAY_OF_MONTH)+"/"+(calendar.get(GregorianCalendar.MONTH)+1)+"/"+calendar.get(GregorianCalendar.YEAR)+".");
		}
		else // sinon nous lui annullons son bannissement
			dateBannissement=null;
		
	}

}
