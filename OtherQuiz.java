import javax.swing.*;
import java.util.Random;
import java.util.Vector;

public class OtherQuiz {
    Vector<Term> termVector;
    int rounds;
    RomajiParser romajiParser;

    OtherQuiz(Vector<Term> tVector, int r, RomajiParser parser){
        this.termVector = tVector;
        this.rounds = r;
        this.romajiParser = parser;
    }

    /*
    this version is designed for a person that cannot read hiragana or katakana
    all resources are populated in Main, and this is primarily concerned with
    running the quiz itself, and the logic for that.  The resources populated
    are as follows:

    Vector of "Term" objects
    integer number of rounds
    a populated Romaji Parser

     */
    void runOtherQuiz(){
        //this holds the questions that have been tested on previously
        Vector<Integer> testedQuestions = new Vector<>();
        //RNG
        Random random = new Random();
        //main loop that drives the quiz
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

            //get a random int between 0 and the number of questions
            int question = random.nextInt(this.termVector.size());
            //if we've already tested on this question
            if (testedQuestions.contains(question)){
                //70% chance to repick the question.  there is value in some repeats, but
                //I'd prefer them to be relatively rare
                if (random.nextInt(10) > 3){
                    //if repick, deincrement the loop and re-run
                    i--;
                    continue;
                }
            } else{
                //if we haven't seen this question before, add it to the tested questions we've seen
                testedQuestions.add(question);
            }
            //if number of tested questions == number of terms, reset the questions we've seen
            if (testedQuestions.size() == this.termVector.size()){
                testedQuestions.clear();
            }
            //determine if the term in question has a kanji associated in the parameters provided
            boolean hasKanji = !termVector.elementAt(question).getKanji().equals("");

            /*
            if it has a kani, there are 6 cases we can test on, as there are three parameters:
            English Definition
            Reading
            Kanji

            Definition - Kanji
            Definition - Reading
            Reading - Kanji
            Reading - Definition
            Kanji - Definition
            Kanji - Reading

            This is run via a switch statement.  There's probably a more clean way to write this, but
            this is good enough for now.

            TODO: Make this better

             */
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
                        JOptionPane.showMessageDialog(null, this.romajiParser.parseTerm(this.termVector.elementAt(question).getKanaReading()));
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
                /*
                If there's no kanji, there are only two possible cases:
                English Definition - Reading
                Reading - English Definition
                 */
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
