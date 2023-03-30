import java.util.Scanner;

public class Quit implements Command {
    @Override
    public String name() {
        return "quit";
    }

    @Override
    public boolean run(Scanner sc) {
        // Retourner true pour indiquer que la commande a été exécutée avec succès
        // et que le programme doit quitter
        return true;
    }

}