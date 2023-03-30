import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Predict implements Command{

    // Cette méthode prend une chaîne de caractères en entrée et renvoie une carte de fréquence de mots
    public static Map<String, String> predict(String str) {
        // Remplacer tous les caractères autres que les lettres et les chiffres par un espace, puis convertir en minuscules
        str = str.replaceAll("[^A-Za-z0-9]+", " ").toLowerCase(Locale.ROOT);
        // Diviser la chaîne en une liste de mots
        List<String> arr = Arrays.asList(str.split(" "));
        // Extraire la liste de mots uniques
        List<String> uniq = arr.stream().distinct().collect(Collectors.toList());
        // Initialiser une table de hachage pour stocker les résultats
        Map<String, String> freqWord = new Hashtable<>();
        // Boucler sur chaque mot unique
        for (int index = 0; index < uniq.size(); index++) {
            // Créer une liste de tous les mots qui suivent ce mot unique
            List<String> list = new ArrayList<>();
            for (int w = 0; w < arr.size() - 1; w++) {
                if (arr.get(w).equals(uniq.get(index))) {
                    list.add(arr.get(w + 1));
                }
            }
            // Extraire la liste de mots uniques qui suivent ce mot unique
            List<String> li = list.stream().distinct().collect(Collectors.toList());
            // Créer une carte de fréquence des mots qui suivent ce mot unique
            var freqMap = list.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            // Trouver le mot qui apparaît le plus souvent parmi ceux qui suivent ce mot unique
            long max = 0;
            String st = "";
            for (String s: li) {
                if (max < freqMap.get(s)){
                    st = s;
                    max= freqMap.get(s);
                }
            }
            // Ajouter le mot le plus fréquent dans la carte de fréquence de mots
            freqWord.put(uniq.get(index), st);
        }
        // Renvoyer la carte de fréquence de mots
        return freqWord;
    }

    // Cette méthode permet à l'utilisateur d'interagir avec le programme
    public static void interactUser(Scanner console, Map<String, String> freq){
        // Demander à l'utilisateur d'entrer un mot
        System.out.printf("Enter a word: ");
        String word = console.nextLine().toLowerCase();
        // Afficher le mot entré
        System.out.printf(word + " ");
        // Si le mot est dans la carte de fréquence de mots, afficher les 19 mots suivants les plus probables
        if (freq.containsKey(word)){
            for (int i = 0; i < 19; i++){
                System.out.printf(freq.get(word) + " ");
                word = freq.get(word).toString();
            }
            System.out.printf("\n");
        }
        // Sinon, afficher un message d'erreur
        else {
            System.out.printf("Word does not exist");
        }
    }

    @Override
    public String name() {
        return "predict";
    }

    @Override
    public boolean run(Scanner console) {
    System.out.printf("Enter a path: ");
    String path = console.nextLine();
    Path filepath = Paths.get(path);

    try {
        // Lire le contenu du fichier
        String content = Files.readString(filepath);
        // Prédire les mots suivants et stocker les résultats dans une carte de fréquence de mots
        Map<String, String> freq = predict(content);
        // Interagir avec l'utilisateur en utilisant la carte de fréquence de mots
        interactUser(console, freq);
    }
    catch (IOException e) {
        // Afficher un message d'erreur si le fichier est illisible
        System.out.printf("Unreadable file: ");
        e.printStackTrace();
    }
    // Indiquer que la commande s'est exécutée avec succès
    return true;
}
}

