import java.util.Scanner;

public class Launcher {
    public static final String WELCOME = "Bienvenue !";
    public static final Command[] COMMANDS = {new Fibo(), new Freq(), new Predict(), new Quit()};

    public static void main(String[] args) {
        System.out.println(WELCOME);
        Scanner sc = new Scanner(System.in);
        String var = "";
        Command cmd = null;
        boolean shouldContinue;
        do {
            var = sc.nextLine(); // Lire la commande de l'utilisateur
            cmd = null;
            shouldContinue = false;
            for (Command c : COMMANDS) {
                if (c.name().contentEquals(var)) { // Vérifier si la commande est valide
                    cmd = c;
                    break;
                }
            }
            if (cmd == null) {
                System.out.println("Unknown command"); // Si la commande est invalide, afficher un message d'erreur
            } else {
                shouldContinue = !cmd.run(sc);
            }
        } while (shouldContinue);

        sc.close(); // Fermer le scanner
    }
}

