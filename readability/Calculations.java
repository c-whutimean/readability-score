package readability;

import java.util.ArrayList;
import java.util.List;

public class Calculations {
    static List<Integer> ages = new ArrayList<>();

    static String getAgeGroup(int score) {
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

    static double getAvgAge() {
        double avg = 0;
        double size = ages.size();

        for (int a : ages) {
            avg += a;
        }

        avg = avg / size;

        return avg;
    }

    static void getARI() {
        double score = 4.71 * ((double) Counter.numChars / Counter.numWords)
                + 0.5 * ((double) Counter.numWords / Counter.numSentences) - 21.43;
        String output1 = "Automated Readability Index: %.2f".formatted(score);
        score = Math.ceil(score);
        String ageGroup = getAgeGroup((int) score);
        String output2 = " (about %s-year-olds).".formatted(ageGroup);
        System.out.println(output1 + output2);
    }

    static void getFK() {
        double score = 0.39 * ((double) Counter.numWords / Counter.numSentences)
                + 11.8 * ((double) Counter.numSyllables / Counter.numWords) - 15.59;
        String output1 = "Flesch" + (char) 8211 + "Kincaid";
        String output2 = " readability tests: %.2f".formatted(score);
        score = Math.ceil(score);
        String ageGroup = getAgeGroup((int) score);
        String output3 = " (about %s-year-olds).".formatted(ageGroup);
        System.out.println(output1 + output2 + output3);
    }

    static void getSMOG() {
        double score = 1.043 * Math.sqrt(Counter.numPoly
                * ((double) 30 / Counter.numSentences)) + 3.1291;
        String output1 = "Simple Measure of Gobbledygook: %.2f".formatted(score);
        score = Math.ceil(score);
        String ageGroup = getAgeGroup((int) score);
        String output2 = " (about %s-year-olds).".formatted(ageGroup);
        System.out.println(output1 + output2);
    }

    static void getCL() {
        double _L = (double) Counter.numChars / ((double) Counter.numWords / 100);
        double _S = (double) Counter.numSentences / ((double) Counter.numWords / 100);
        double score = 0.0588 * _L - 0.296 * _S - 15.8;
        String output1 = "Coleman" + (char) 8211 + "Liau";
        String output2 = " index: %.2f".formatted(score);
        score = Math.ceil(score);
        String ageGroup = getAgeGroup((int) score);
        String output3 = " (about %s-year-olds).".formatted(ageGroup);
        System.out.println(output1 + output2 + output3);
    }

}
