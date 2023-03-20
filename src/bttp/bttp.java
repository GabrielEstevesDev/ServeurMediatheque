package bttp;

public class bttp {
	
	public static String encoder(String s) {
		return s.replaceAll("\n", "##");
	}
	public static String decoder(String s) {
		return s.replaceAll("##", "\n");
	}
}
