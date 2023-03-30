import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Freq implements Command {
    // Implémentation de la méthode "name()" de l'interface "Command"
    @Override
    public String name() {
        return "freq"; // Retourne le nom de la commande
    }

    // Implémentation de la méthode "run()" de l'interface "Command"
    @Override
    public boolean run(Scanner console) {
        System.out.printf("Enter a path");
        String path = console.nextLine(); // Lit l'entrée utilisateur pour obtenir le chemin d'accès au fichier
        Path filepath = Paths.get(path);

        try {
            String content = Files.readString(filepath); // Lit le contenu du fichier
            freq(content); // Calcule la fréquence des mots dans le contenu du fichier
        } catch (IOException e) {
            System.out.printf("Unreadable file: ");
            e.printStackTrace(); // Gère l'exception si le fichier ne peut pas être lu
        }
        return true; // Retourne "true" pour indiquer que la commande a réussi
    }

    // Méthode pour calculer la fréquence des mots dans une chaîne de caractères
    public static void freq(String str){
        str = str.replaceAll("[^A-Za-z0-9]+", " "); // Remplace tous les caractères qui ne sont pas des lettres ou des chiffres par un espace
        str = str.toLowerCase(Locale.ROOT); // Convertit toutes les lettres en minuscules
        List<String> arr = Arrays.asList(str.split(" ")); // Convertit la chaîne de caractères en une liste de mots

        if (str.isBlank()) { // Vérifie si la chaîne de caractères est vide ou ne contient que des espaces
            return;
        }
        var freqMap = arr.stream()
                .collect(Collectors.groupingBy(s->s, Collectors.counting())); // Calcule la fréquence des mots en utilisant les fonctions de flux

        List<String> li = null;
        var max3 = freqMap.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()).limit(3); // Récupère les 3 mots les plus fréquents
        String l = max3.map(Map.Entry::getKey).collect(Collectors.joining(" ")); // Concatène les mots avec un espace entre eux
        System.out.printf(l + "\n"); // Affiche les 3 mots les plus fréquents
    }
}

