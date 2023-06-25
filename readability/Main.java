package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    String text;
    String[] words;
    static String word;

    Main(String args) {
        this.text = setText(args);
        this.words = text.split("\\s+");
        removeEndMarks();
    }

    String setText(String args) {
        File file = new File(args);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                text = scanner.nextLine();
            }
        } catch (FileNotFoundException e) {
            text = "No file found: " + args;
        }

        return text;
    }

    void removeEndMarks() {
        List<String> modList = new ArrayList<>();

        for (String s : words) {
            if (s.endsWith(".") || s.endsWith("?") || s.endsWith("!") || s.endsWith(",")) {
                word = s.substring(0, s.length() - 1);
            } else {
                word = s;
            }
            modList.add(word);
        }
        String[] modWords = modList.toArray(new String[0]);
        words = Arrays.copyOf(modWords, modWords.length);
    }

    public static void main(String[] args) {
        Main main = new Main(args[0]);
        Counter count = new Counter(main.text, main.words);
        count.setAll();
        Scoring score = new Scoring();
        score.showStatus();
    }
}
