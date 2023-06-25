package readability;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Counter {
    static int numChars;
    static int numPoly;
    static int numSentences;
    static int numWords;
    static long numSyllables;
    String text;
    String[] words;
    String[] sentences;
    List<Long> syllableList = new ArrayList<>();

    Counter(String text, String[] words) {
        this.text = text;
        this.words = words;
        this.sentences = text.split("[.?!]");
    }

    void setAll() {
        setNumSentences();
        setNumWords();
        setNumOfChars();
        setNumSyllables();
        setPolySyllables();
    }

    void setNumSentences() {
        numSentences = sentences.length;
    }

    void setNumWords() {
        numWords = words.length;
    }
    void setNumOfChars() {
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isWhitespace(text.charAt(i))) {
                numChars++;
            }
        }
    }
    void setNumSyllables() {
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
    }
    void setPolySyllables() {
        Long[] syllableArr = syllableList.toArray(new Long[0]);
        numPoly = 0;
        for (Long aLong : syllableArr) {
            if (aLong > 2) {
                numPoly++;
            }
        }
    }
}
