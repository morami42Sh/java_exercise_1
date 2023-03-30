import java.util.InputMismatchException;
import java.util.Scanner;

public class Fibo implements Command {

    // Implémentation de la méthode "name()" de l'interface "Command"
    @Override
    public String name() {
        return "fibo"; // Retourne le nom de la commande
    }

    // Implémentation de la méthode "run()" de l'interface "Command"
    @Override
    public boolean run(Scanner sc) {
        System.out.println("Index fibonacci n = ?");
        try {
            int i = sc.nextInt(); // Lit l'entrée utilisateur pour obtenir l'indice de Fibonacci à calculer
            if(i < 2) {
                System.out.println("n must be greater than 2 !"); // Vérifie que l'indice est supérieur à 2
                sc.close(); // Ferme le scanner
                return false; // Retourne "false" pour indiquer que la commande a échoué
            }
            int u = 1, v = 1;
            for(int j = 2; j < i; j++) { // Calcul de l'indice de Fibonacci en utilisant une boucle "for"
                int temp = u+v;
                u = v;
                v = temp;
            }
            System.out.println(v); // Affiche le résultat de l'indice de Fibonacci calculé
        } catch (InputMismatchException e) {
            System.out.println("You need to input a valid number"); // Gère l'exception si l'utilisateur entre autre chose qu'un nombre entier
        }
        sc.close(); // Ferme le scanner
        return false; // Retourne "false" pour indiquer que la commande a échoué
    }

}

