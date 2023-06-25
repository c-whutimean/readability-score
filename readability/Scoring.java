package readability;

import java.util.Scanner;

public class Scoring {
    Scanner sc = new Scanner(System.in);
    Counter count;

    void showStatus() {
        String output = """
                Words: %d
                Sentences: %d
                Characters: %d
                Syllables: %d
                Polysyllables: %d
                """.formatted(Counter.numWords, Counter.numSentences,
                Counter.numChars, Counter.numSyllables, Counter.numPoly);
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
                Calculations.getARI();
                break;
            case "FK":
                Calculations.getFK();
                break;
            case "SMOG":
                Calculations.getSMOG();
                break;
            case "CL":
                Calculations.getCL();
                break;
            case "all":
                Calculations.getARI();
                Calculations.getFK();
                Calculations.getSMOG();
                Calculations.getCL();
                String output1 = "This text should be understood in average by";
                String output2 = " %.2f-year-olds.".formatted(Calculations.getAvgAge());
                System.out.println();
                System.out.println(output1 + output2);
                break;
            default:
                System.out.println("Not an option");
                break;
        }
    }
}
