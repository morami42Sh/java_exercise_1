import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Freq implements Command {

    @Override
    public String name() {
        return "freq";
    }

    @Override
    public boolean run(Scanner sc) {
        // Demander à l'utilisateur de choisir un fichier
        System.out.println("Please choose a file:");

        try {
            // Lire le nom du fichier à partir de l'entrée de l'utilisateur
            String filename = sc.nextLine();

            // Lire le contenu du fichier dans une chaîne de caractères
            String content = readFileAsString(filename);

            // Extraire les mots de la chaîne de caractères, en supprimant la ponctuation et en convertissant en minuscules
            String[] words = extractWords(content);

            // Calculer la fréquence des mots
            printTopWords(words);

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return false; // Retourner false pour indiquer que la commande n'a pas été exécutée avec succès
    }

    private String readFileAsString(String filename) throws IOException {
        // Utiliser BufferedReader pour lire le fichier ligne par ligne
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        StringBuilder sb = new StringBuilder();

        // Lire chaque ligne et ajouter à la chaîne de caractères
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }

        // Fermer le BufferedReader et retourner la chaîne de caractères résultante
        reader.close();
        return sb.toString();
    }

    private String[] extractWords(String content) {
        // Remplacer tous les caractères qui ne sont pas des lettres ou des espaces par une chaîne vide
        String cleaned = content.replaceAll("[^a-zA-Z\\s]", "");

        // Diviser la chaîne nettoyée en mots individuels, en ignorant les espaces en blanc
        String[] words = cleaned.toLowerCase().split("\\s+");

        return words;
    }

    private void printTopWords(String[] words) {
        // Calculer la fréquence des mots et trier par ordre décroissant de fréquence
        Arrays.stream(words).collect(Collectors.groupingBy(w -> w, Collectors.counting()))
                .entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                // Afficher les trois mots les plus fréquents
                .forEach((entry) -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }
}