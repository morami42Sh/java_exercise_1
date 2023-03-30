import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Predict implements Command {
    @Override
    public String name() {
        return "predict";
    }

    @Override
    public boolean run(Scanner scanner) {
        System.out.println("Choose a file !");
        String chosen = "";
        try {
            chosen = scanner.nextLine();

            // Lire le contenu du fichier choisi et le découper en mots
            String text = Files.readString(Paths.get(chosen));
            String[] words = text.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");

            // Construire une carte de probabilités pour chaque mot
            Map<String, Map<String, Integer>> probabilities = new HashMap<>();
            for (int i = 0; i < words.length - 1; i++) {
                String word = words[i],
                        nextWord = words[i+1];
                Map<String, Integer> nextWordsCount = probabilities.get(word);
                if (nextWordsCount == null) {
                    nextWordsCount = new HashMap<>();
                    probabilities.put(word, nextWordsCount);
                    nextWordsCount.put(nextWord, 1);
                    continue;
                }
                Integer count = nextWordsCount.get(nextWord);
                if (count == null)
                    nextWordsCount.put(nextWord, 1);
                else
                    nextWordsCount.replace(nextWord, count+1);
            }

            // Demander un mot à l'utilisateur et prédire la suite la plus probable
            System.out.println("Enter a word : ");
            chosen = scanner.nextLine();
            if (!probabilities.containsKey(chosen)) {
                System.out.println("This word is not in the text !");
                return false;
            }
            String nextWord = chosen;
            StringBuilder builder = new StringBuilder();
            int length = 1;
            while (probabilities.containsKey(nextWord) && length++ < 20) {
                Map<String, Integer> nextWordsCount = probabilities.get(nextWord);
                Integer maxValue = 0;
                probabilities.remove(nextWord); // Éviter les boucles infinies
                builder.append(" " + nextWord);
                for (Entry<String, Integer> entry : nextWordsCount.entrySet()) {
                    if (entry.getValue() > maxValue) {
                        nextWord = entry.getKey();
                        maxValue = entry.getValue();
                    }
                }
            }
            builder.append(' ' + nextWord);
            System.out.println("Most common sentence :" + builder.toString());
        } catch (IOException e) {
            System.out.println("Unreadable file : " + e.getClass() + " " + e.getMessage());
        }
        return false;
    }
}

