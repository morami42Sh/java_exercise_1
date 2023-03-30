import java.util.Scanner;

public interface Command {
    // Méthode pour retourner le nom de la commande
    public String name();

    // Méthode pour exécuter la commande
    public boolean run(Scanner sc);
}