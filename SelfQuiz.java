import javax.swing.*;
import java.util.Random;
import java.util.Vector;

public class SelfQuiz {
    int rounds;
    Vector<Term> termVector;
    SelfQuiz(Vector<Term> termVectorIn, int rounds){
        this.termVector = termVectorIn;
        this.rounds = rounds;
    }

    void runSelfQuiz(){
        Vector<Integer> testedQuestions = new Vector<>();
        Random random = new Random();
        for (int i = 0; i < this.rounds; i++){
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
                        JOptionPane.showMessageDialog(null, this.termVector.elementAt(question).getKanaReading());
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
                        JOptionPane.showMessageDialog(null, this.termVector.elementAt(question).getKanaReading());
                        break;
                    //see reading, produce kanji
                    case 4:
                        JOptionPane.showMessageDialog(null, "What is the kanji for this term: " + this.termVector.elementAt(question).getKanaReading() + "?");
                        JOptionPane.showMessageDialog(null, this.termVector.elementAt(question).getKanji());
                        break;
                    //see reading, produce english definition
                    case 5:
                        JOptionPane.showMessageDialog(null, "What is the english definition for this term: " + this.termVector.elementAt(question).getKanaReading() + "?");
                        JOptionPane.showMessageDialog(null, this.termVector.elementAt(question).getEnglishDefinition());
                        break;
                }
            } else{
                switch (random.nextInt(2)){

                    //see english definition, produce reading
                    case 0:
                        JOptionPane.showMessageDialog(null, "What is the reading for this definition: " + this.termVector.elementAt(question).getEnglishDefinition() + "?");
                        JOptionPane.showMessageDialog(null, this.termVector.elementAt(question).getKanaReading());
                        break;
                    //see reading, produce english definition
                    case 1:
                        JOptionPane.showMessageDialog(null, "What is the english definition for this term: " + this.termVector.elementAt(question).getKanaReading() + "?");
                        JOptionPane.showMessageDialog(null, this.termVector.elementAt(question).getEnglishDefinition());
                        break;
                }
            }

        }
    }

}
