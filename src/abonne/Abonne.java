package abonne;

import java.util.Calendar;
import java.util.Date;
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
	public String toString() {
		return "num : "+this.num + " nom :"+ this.nom + " naissance : " + this.naissance;
	}

	@Override
	public int hashCode() {
		return Objects.hash(num);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Abonne other = (Abonne) obj;
		return num == other.num;
	}

	public void banniMois() {
		dateBannissement=new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateBannissement);
		cal.add(Calendar.MONTH, 2);
		dateBannissement=cal.getTime();
		
	}
	
	public Date getDateBan() {
		return dateBannissement;
	}
}
