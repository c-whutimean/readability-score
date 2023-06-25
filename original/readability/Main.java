package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    Scanner sc = new Scanner(System.in);
    final String endMark = "[.?!]";
    int numChars;
    int numPoly;
    int numSentences;
    int numWords;
    long numSyllables;
    String text;
    String word;
    String[] words;
    String[] sentences;
    List<Long> syllableList = new ArrayList<>();
    List<Integer> ages = new ArrayList<>();

    Main(String args) {
        this.text = setText(args);
        this.numChars = getNumOfChars();
        this.words = text.split("\\s+");
        this.sentences = text.split(endMark);
        this.numSentences = sentences.length;
        this.numWords = words.length;
        this.numSyllables = getNumSyllables();
        this.numPoly = getPolySyllables();
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

    String getAgeGroup(int score) {
        int start = 5;
        int end = 6;
        int incr = score - 1;
        start += incr;
        end += incr;
        String ageStart = Integer.toString(start);
        String ageEnd = Integer.toString(end);
        int age = Integer.parseInt(ageEnd);
        ages.add(age);

        return ageEnd;
    }

    double getAvgAge() {
        double avg = 0;
        double size = ages.size();

        for (int a : ages) {
            avg += a;
        }

        avg = avg / size;

        return avg;
    }

    int getNumOfChars() {
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isWhitespace(text.charAt(i))) {
                numChars++;
            }
        }
        return numChars;
    }

    void removeEndMarks() {
        List<String> modList = new ArrayList<>();

        for (int i = 0; i < words.length; i++) {
            if (words[i].endsWith(".") || words[i].endsWith("?") || words[i].endsWith("!") || words[i].endsWith(",")) {
                word = words[i].substring(0, words[i].length() - 1);
            } else {
                word = words[i];
            }
            modList.add(word);
        }
        String[] modWords = modList.toArray(new String[0]);
        words = Arrays.copyOf(modWords, modWords.length);
    }

    long getNumSyllables() {
        removeEndMarks();
        String vowels = "[aeiouyAEIOUY]";
        String consecVowels = "[aeiouyAEIOUY]{2}";

        Pattern pattern = Pattern.compile(vowels);
        Pattern pattern2 = Pattern.compile(consecVowels);
        numSyllables = 0;

        for (String word : words) {
            int lastIndex = word.length() - 1;
            Matcher matcher = pattern.matcher(word);
            Matcher matcher2 = pattern2.matcher(word);
            long syllableCount = 0;
            long consecutive = 0;

            while (matcher2.find()) {
                consecutive++;
            }
            while (matcher.find()) {
                syllableCount++;
            }
            if (syllableCount > 0 && word.charAt(lastIndex) == 'e') {
                syllableCount--;
            }
            if (word.charAt(0) == 'y' || word.charAt(0) == 'Y') {
                syllableCount--;
            }
            if (syllableCount == 0) {
                syllableCount = 1;
            }

            syllableCount -= consecutive;
            syllableList.add(syllableCount);

            numSyllables += syllableCount;
        }

        return numSyllables;
    }

    int getPolySyllables() {
        Long[] syllableArr = syllableList.toArray(new Long[0]);
        numPoly = 0;
        for (int i = 0; i < syllableArr.length; i++) {
            if (syllableArr[i] > 2) {
                numPoly++;
            }
        }
        return numPoly;
    }

    void showStatus() {
        String output = """
                Words: %d
                Sentences: %d
                Characters: %d
                Syllables: %d
                Polysyllables: %d
                """.formatted(numWords, numSentences, numChars,
                numSyllables, numPoly);
        String options1 = "Enter the score you want to calculate ";
        String options2 = "(ARI, FK, SMOG, CL, all): ";

        System.out.println(output);
        System.out.print(options1 + options2);
        setOption();
    }

    void setOption() {
        String option = sc.nextLine();
        System.out.println();

        switch (option) {
            case "ARI":
                show_ARI();
                break;
            case "FK":
                showFK();
                break;
            case "SMOG":
                showSMOG();
                break;
            case "CL":
                showCL();
                break;
            case "all":
                show_ARI();
                showFK();
                showSMOG();
                showCL();
                String output1 = "This text should be understood in average by";
                String output2 = " %.2f-year-olds.".formatted(getAvgAge());
                System.out.println();
                System.out.println(output1 + output2);
                break;
            default:
                System.out.println("Not an option");
                break;
        }
    }


    void show_ARI() {
        double score = 4.71 * ((double) numChars / numWords) + 0.5 * ((double) numWords / numSentences) - 21.43;
        String output1 = "Automated Readability Index: %.2f".formatted(score);
        score = Math.ceil(score);
        String ageGroup = getAgeGroup((int) score);
        String output2 = " (about %s-year-olds).".formatted(ageGroup);
        System.out.println(output1 + output2);
    }

    void showFK() {
        double score = 0.39 * ((double) numWords / numSentences) + 11.8 * ((double) numSyllables / numWords) - 15.59;
        String output1 = "Flesch" + (char) 8211 + "Kincaid";
        String output2 = " readability tests: %.2f".formatted(score);
        score = Math.ceil(score);
        String ageGroup = getAgeGroup((int) score);
        String output3 = " (about %s-year-olds).".formatted(ageGroup);
        System.out.println(output1 + output2 + output3);
    }

    void showSMOG() {
        double score = 1.043 * Math.sqrt(numPoly * ((double) 30 / numSentences)) + 3.1291;
        String output1 = "Simple Measure of Gobbledygook: %.2f".formatted(score);
        score = Math.ceil(score);
        String ageGroup = getAgeGroup((int) score);
        String output2 = " (about %s-year-olds).".formatted(ageGroup);
        System.out.println(output1 + output2);
    }

    void showCL() {
        double _L = (double) numChars / ((double) numWords / 100);
        double _S = (double) numSentences / ((double) numWords / 100);
        double score = 0.0588 * _L - 0.296 * _S - 15.8;
        String output1 = "Coleman" + (char) 8211 + "Liau";
        String output2 = " index: %.2f".formatted(score);
        score = Math.ceil(score);
        String ageGroup = getAgeGroup((int) score);
        String output3 = " (about %s-year-olds).".formatted(ageGroup);
        System.out.println(output1 + output2 + output3);
    }

    public static void main(String[] args) {
        Main main = new Main(args[0]);
        main.showStatus();
    }
}
