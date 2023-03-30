import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Freq implements Command {
	// Méthode pour afficher les trois mots les plus fréquents dans un fichier choisi à l'aide d'une interface graphique
	public static void freq2() throws IOException {
		// Changer le look and feel pour une apparence plus agréable (Windows, Mac, etc.)
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			System.out.println("Couldnt change l&f :(");
		}
		// Ouvrir une fenêtre de sélection de fichier
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(null);
		// Lire le contenu du fichier sélectionné
		FileInputStream in = new FileInputStream(chooser.getSelectedFile());
		String a = new String(in.readAllBytes());
		// Supprimer les caractères non alphabétiques et mettre tous les mots en minuscules
		String[] words = a.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");
		// Regrouper les mots en fonction de leur fréquence d'apparition et afficher les trois plus fréquents
		Arrays.stream(words).filter((str) -> !str.isBlank())
		.collect(Collectors.groupingBy((str) -> str)).entrySet().stream()
		.sorted(Comparator.comparingInt((e) -> -e.getValue().size())).limit(3)
		.forEach((str) -> System.out.print(str.getKey() + " "));
		in.close();
	}

	@Override
	public String name() {
		return "freq";
	}

	@Override
	public boolean run(Scanner sc) {
		System.out.println("Choose a file !");
		String chosen = "";
		try {
			chosen = sc.next();
			// Lire le contenu du fichier choisi
			String a = Files.readString(Paths.get(chosen));
			// Supprimer les caractères non alphabétiques et mettre tous les mots en minuscules
			String[] words = a.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");
			// Regrouper les mots en fonction de leur fréquence d'apparition et afficher les trois plus fréquents
			Arrays.stream(words).filter((str) -> !str.isBlank())
			.collect(Collectors.groupingBy((str) -> str)).entrySet().stream()
			.sorted(Comparator.comparingInt((e) -> -e.getValue().size())).limit(3)
			.forEach((str) -> System.out.print(str.getKey() + " "));
		} catch (IOException e) {
			System.out.println("Unreadable file : " + e.getClass() + " " + e.getMessage());
		}
		return false;
	}

}

