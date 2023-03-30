import java.util.Scanner;

public class Launcher {
    public static final String WELCOME = "Bienvenue !";
    public static final Command[] COMMANDS = {new Fibo(), new Freq(), new Predict(), new Quit()};

    public static void main(String[] args) {
        System.out.println(WELCOME);
        Scanner scanner = new Scanner(System.in);
        Command command = null;
        do {
            String input = scanner.nextLine(); // Lire la commande de l'utilisateur
            command = null;
            for (Command c : COMMANDS) {
                if (c.name().equalsIgnoreCase(input)) { // Vérifier si la commande est valide (ignorer la casse)
                    command = c;
                    break;
                }
            }
            if (command == null) {
                System.out.println("Unknown command"); // Si la commande est invalide, afficher un message d'erreur
            } else {
                boolean shouldQuit = command.run(scanner); // Exécuter la commande
                if (shouldQuit) {
                    System.out.println("Exiting program...");
                    System.exit(0); // Quitter le programme si la commande le demande
                }
            }
        } while (command == null || !command.name().equalsIgnoreCase("quit")); // Continuer tant que la commande n'est pas "quit"
        scanner.close(); // Fermer le scanner
    }
}

