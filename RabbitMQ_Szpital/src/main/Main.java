package main;

public class Main {

	public static void main(String[] args) throws Exception {
		System.out.println("Main start with " + args[0]);
		if(args[0].contains("A")) {
			Administrator.main(null);
		}
		else if(args[0].contains("T")) {
			Technik.main(null);
		}
		else if(args[0].contains("L")) {
			Lekarz.main(null);
		}
	}

}
