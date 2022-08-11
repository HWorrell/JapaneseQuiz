import javax.swing.*;
import java.util.Random;
import java.util.Vector;

public class OtherQuiz {
    Vector<Term> termVector;
    int rounds;
    RomajiParser romajiParser;

    OtherQuiz(Vector<Term> tvector, int r, RomajiParser parser){
        this.termVector = tvector;
        this.rounds = r;
        this.romajiParser = parser;
    }

    void runOtherQuiz(){
        Vector<Integer> testedQuestions = new Vector<>();
        Random random = new Random();
        int checkPeriod;
        if (rounds/5 <20){
            checkPeriod = rounds/5;
        }
        else{
            checkPeriod = 20;
        }
        int checkCounter = 0;
        for (int i = 0; i < this.rounds; i++){

            checkCounter++;

            if (this.rounds > 20) {
                if (checkCounter == checkPeriod) {
                    String cont = JOptionPane.showInputDialog("Continue? Enter \"no\" to quit, or yes to continue");
                    if (cont.equals("no")) {
                        System.exit(0);
                    } else {
                        checkCounter = 0;
                    }
                }
            }
            int question = random.nextInt(this.termVector.size());
            if (testedQuestions.contains(question)){
                if (random.nextInt(10) > 3){
                    i--;
                    continue;
                }
            } else{
                testedQuestions.add(question);
            }
            if (testedQuestions.size() == this.termVector.size()){
                testedQuestions.clear();
            }
            boolean hasKanji = !termVector.elementAt(question).getKanji().equals("");

            if (hasKanji){
                switch (random.nextInt(6)){
                    //see kanji, produce reading
                    case 0:
                        JOptionPane.showMessageDialog(null, "What is the reading for the following Kanji: " + this.termVector.elementAt(question).getKanji() + "?");
                        JOptionPane.showMessageDialog(null, this.romajiParser.parseTerm(this.termVector.elementAt(question).getKanaReading()));
                        break;
                    //see kanji, produce english definition
                    case 1:
                        JOptionPane.showMessageDialog(null, "What is the definition for the following Kanji: " + this.termVector.elementAt(question).getKanji() + "?");
                        JOptionPane.showMessageDialog(null, this.termVector.elementAt(question).getEnglishDefinition());
                        break;
                    //see english definition, produce kanji
                    case 2:
                        JOptionPane.showMessageDialog(null, "What is the Kanji for this definition: " + this.termVector.elementAt(question).getEnglishDefinition() + "?");
                        JOptionPane.showMessageDialog(null, this.termVector.elementAt(question).getKanji());
                        break;
                    //see english definition, produce reading
                    case 3:
                        JOptionPane.showMessageDialog(null, "What is the reading for this definition: " + this.termVector.elementAt(question).getEnglishDefinition() + "?");
                        JOptionPane.showMessageDialog(null, this.romajiParser.parseTerm(this.romajiParser.parseTerm(this.termVector.elementAt(question).getKanaReading())));
                        break;
                    //see reading, produce kanji
                    case 4:
                        JOptionPane.showMessageDialog(null, "What is the kanji for this term: " + this.romajiParser.parseTerm(this.termVector.elementAt(question).getKanaReading()) + "?");
                        JOptionPane.showMessageDialog(null, this.termVector.elementAt(question).getKanji());
                        break;
                    //see reading, produce english definition
                    case 5:
                        JOptionPane.showMessageDialog(null, "What is the english definition for this term: " + this.romajiParser.parseTerm(this.termVector.elementAt(question).getKanaReading()) + "?");
                        JOptionPane.showMessageDialog(null, this.termVector.elementAt(question).getEnglishDefinition());
                        break;
                }
            } else{
                switch (random.nextInt(2)){

                    //see english definition, produce reading
                    case 0:
                        JOptionPane.showMessageDialog(null, "What is the reading for this definition: " + this.termVector.elementAt(question).getEnglishDefinition() + "?");
                        JOptionPane.showMessageDialog(null, this.romajiParser.parseTerm(this.termVector.elementAt(question).getKanaReading()));
                        break;
                    //see reading, produce english definition
                    case 1:
                        JOptionPane.showMessageDialog(null, "What is the english definition for this term: " + this.romajiParser.parseTerm(this.termVector.elementAt(question).getKanaReading()) + "?");
                        JOptionPane.showMessageDialog(null, this.termVector.elementAt(question).getEnglishDefinition());
                        break;
                }
            }
        }
    }
}
