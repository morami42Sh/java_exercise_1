import java.util.Scanner;

public class Quit implements Command {

    // Implémentation de la méthode "name()" de l'interface "Command"
    @Override
    public String name() {
        return "quit"; // Retourne le nom de la commande
    }

    // Implémentation de la méthode "run()" de l'interface "Command"
    @Override
    public boolean run(Scanner sc) {
        return true; // Retourne "true" pour indiquer que la commande a réussi et doit quitter l'application
    }
}

