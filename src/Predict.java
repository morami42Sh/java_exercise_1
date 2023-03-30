import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Predict implements Command {

    @Override
    public String name() {
        return "predict";
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

            // Construire une table de transition de mots
            Map<String, Map<String, Integer>> transitionTable = buildTransitionTable(words);

            // Demander à l'utilisateur un mot de départ
            System.out.println("Enter a starting word:");
            String startingWord = sc.nextLine().toLowerCase();

            // Générer une phrase à partir du mot de départ et de la table de transition
            String sentence = generateSentence(transitionTable, startingWord);
            System.out.println(sentence);

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return false; // Retourner false pour indiquer que la commande n'a pas été exécutée avec succès
    }

    private String readFileAsString(String filename) throws IOException {
        // Lire le contenu du fichier en utilisant Files.readString()
        return Files.readString(Paths.get(filename));
    }

    private String[] extractWords(String content) {
        // Extraire les mots de la chaîne de caractères en utilisant la méthode split()
        String[] words = content.replaceAll("[^a-zA-Z\\s]", "").toLowerCase().split("\\s+");
        return words;
    }

    private Map<String, Map<String, Integer>> buildTransitionTable(String[] words) {
        // Construire une table de transition de mots en utilisant une Map de Maps
        Map<String, Map<String, Integer>> transitionTable = new HashMap<>();

        for (int i = 0; i < words.length - 1; i++) {
            String currentWord = words[i];
            String nextWord = words[i + 1];

            // Vérifier si la clé existe déjà dans la table de transition
            if (!transitionTable.containsKey(currentWord)) {
                transitionTable.put(currentWord, new HashMap<>());
            }

            Map<String, Integer> transitionCounts = transitionTable.get(currentWord);

            // Incrémenter le compteur de transition pour le mot suivant
            transitionCounts.put(nextWord, transitionCounts.getOrDefault(nextWord, 0) + 1);
        }

        return transitionTable;
    }

    private String generateSentence(Map<String, Map<String, Integer>> transitionTable, String startingWord) {
        // Générer une phrase à partir d'un mot de départ et d'une table de transition de mots
        StringBuilder sb = new StringBuilder();
        String currentWord = startingWord;

        // Ajouter le mot de départ à la phrase
        sb.append(currentWord);

        // Générer des mots suivants jusqu'à atte
        int wordCount = 1; // Compteur de mots

        while (transitionTable.containsKey(currentWord) && wordCount < 20) {
            // Trouver la transition la plus probable pour le mot courant
            String nextWord = null;
            int maxCount = 0;

            Map<String, Integer> transitionCounts = transitionTable.get(currentWord);

            for (String word : transitionCounts.keySet()) {
                int count = transitionCounts.get(word);
                if (count > maxCount) {
                    nextWord = word;
                    maxCount = count;
                }
            }

            if (nextWord == null) {
                break;
            }

            // Ajouter le mot suivant à la phrase
            sb.append(" ");
            sb.append(nextWord);

            // Passer au mot suivant
            currentWord = nextWord;
            wordCount++;
        }

        // Retourner la phrase générée
        return sb.toString();
    }
}
