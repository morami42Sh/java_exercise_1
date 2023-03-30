import java.util.Scanner;

public class Launcher {
	public static final String WELCOME = "Bienvenue !";
	public static final Command[] COMMANDS = {new Fibo(), new Freq(), new Predict(), new Quit()};

	public static void main(String[] args) {
		System.out.println(WELCOME);
		Scanner sc = new Scanner(System.in);
		String var = "";
		Command cmd = null;
		do {
			var = sc.nextLine(); // Lire la commande de l'utilisateur
			cmd = null;
			for(Command c : COMMANDS) if(c.name().contentEquals(var)) { // Vérifier si la commande est valide
				cmd = c;
				break;
			}
			if(cmd == null) {
				System.out.println("Unknown command"); // Si la commande est invalide, afficher un message d'erreur
			}
		} while(cmd == null || !cmd.run(sc)); // Tant que la commande est invalide ou n'est pas terminée, continuer la boucle
		sc.close(); // Fermer le scanner
	}
}

