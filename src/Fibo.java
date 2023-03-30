import java.util.InputMismatchException;
import java.util.Scanner;

public class Fibo implements Command {
    @Override
    public String name() {
        return "fibo";
    }

    @Override
    public boolean run(Scanner sc) {
        // Demander à l'utilisateur l'index n du nombre de Fibonacci
        System.out.println("Please enter the index n of the Fibonacci number:");

        try {
            // Lire l'entrée de l'utilisateur sous forme d'un entier
            int n = sc.nextInt();

            // Vérifier si l'entrée est valide (n doit être supérieur à 2)
            if (n < 2) {
                System.out.println("n must be greater than 2!");
                return false; // Retourner false pour indiquer que la commande n'a pas été exécutée avec succès
            }

            // Calculer le nombre de Fibonacci à l'index n
            int fib = calculateFibonacci(n);

            // Afficher le nombre de Fibonacci à l'utilisateur
            System.out.println("The Fibonacci number at index " + n + " is " + fib);

        } catch (InputMismatchException e) {
            System.out.println("Invalid input! You need to input a valid integer.");
            return false; // Retourner false pour indiquer que la commande n'a pas été exécutée avec succès
        } finally {
            // Fermer le scanner
            sc.nextLine(); // Ajouter cette ligne pour consommer la fin de ligne qui suit l'entier lu avec nextInt()
            sc.close();
        }

        return false; // Retourner false pour indiquer que la commande n'a pas été exécutée avec succès
    }

    private int calculateFibonacci(int n) {
        // Initialiser les deux premiers nombres de Fibonacci
        int fib1 = 0;
        int fib2 = 1;

        // Calculer les nombres de Fibonacci suivants jusqu'à atteindre l'index n
        for (int i = 2; i <= n; i++) {
            int fib = fib1 + fib2;
            fib1 = fib2;
            fib2 = fib;
        }

        // Retourner le nombre de Fibonacci à l'index n
        return fib2;
    }
}