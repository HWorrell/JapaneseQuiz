import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        //String test = "ゆうびんきょうく";

        //pull in config file
        File config = new File("Config.ini");
        //default if it doesn't exist
        if(!config.exists()) {
            UIManager.put("OptionPane.messageFont", new Font("System", Font.PLAIN, 30));
        } else{
            try {
                Scanner scanner = new Scanner(config);
                String in = scanner.nextLine();
                in = in.replace("FontSize=[", "").replace("]", "");
                int fontSize = Integer.parseInt(in);
                UIManager.put("OptionPane.messageFont", new Font("System", Font.PLAIN, fontSize));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        File sourceFilesCheck = new File("Source Files");
        File activeFilesCheck = new File("Source Files\\Active");

        if (!sourceFilesCheck.exists() || !activeFilesCheck.exists()) {
            if (sourceFilesCheck.mkdir() && activeFilesCheck.mkdir()) {
                JOptionPane.showMessageDialog(null, "The folders for the content files have been created, please put files into them.");
            }
            System.exit(0);
        }
        //kana translation files for people that do not speak/read japanese
        //format for both is 0 == english letter, 1 == japanese character
        //hiragana translation file
        File hiraganaInFile = new File("hiragana.csv");
        //katakana translation file
        File katakanaInFile = new File("katakana.csv");
        //doesn't actually matter which is which, but keeping them separate would be preferable
        //to aid in future expansions


        //create a parser, feed in the source files
        RomajiParser romajiParser = new RomajiParser(hiraganaInFile, katakanaInFile);

        //master term vector
        Vector<Term> termVector = new Vector<>();

        //System.out.println(romajiParser.parseTerm(test));

        //file format: 0 == kana, 1 == kanji, 2 == english
        File active = new File("Source Files\\Active");

        //get the files in the active folder
        File[] activeFiles = active.listFiles();

        //check that we actually got something
        if (activeFiles == null || activeFiles.length == 0){
            JOptionPane.showMessageDialog(null, "Issue with the files");
            System.exit(0);
        } else {

            for (File f : activeFiles
            ) {
                //if it's not a .txt file, skip it.  This can't parse anything else at this time.
                if (!f.getName().contains(".txt")){
                    continue;
                }
                try {
                    //read the file in
                    BufferedReader inReader = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_16));

                    //init
                    String str = "";
                    //while we're still getting something from the reader
                    /*
                    basic logic for this loop is as follows:
                    kana is everything before first tab
                        throw away first tab
                        set string equal to remainder
                    kanji is everything before first tab
                        throw away first tab
                        set string equal to remainder
                    english term is everything remaining
                     */
                    while((str = inReader.readLine()) != null) {
                        String kana = str.substring(0, str.indexOf("\t"));
                        str = str.substring(str.indexOf("\t") + 1);
                        String kanji = str.substring(0, str.indexOf("\t"));
                        str = str.substring(str.indexOf("\t") + 1);
                        String english = str.replace("\t", "");
                        Term tempTerm = new Term(english, kana, kanji);
                        termVector.add(tempTerm);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } //end file for
        } // end else

        //get user selection.  This can probably be done better, but it's not a high priority now.
        //TODO improve this

        //initialize as null
        String selection = null;
        //keep looping until we get something valid
        while (selection == null || (!selection.equalsIgnoreCase("Quit") && !selection.equals("1") && !selection.equals("2")))
        selection = JOptionPane.showInputDialog("Enter 1 for self quiz or to have someone that can read Hiragana quiz you\r\n" +
                "Enter 2 to have someone that cannot read hiragana quiz you\r\n" +
                "Type \"Quit\" to cancel");
        //if quit, exit gracefully
        if (selection.equalsIgnoreCase("Quit")){
            System.exit(0);
            //if 1, get number of rounds, create quiz with parameters, and exit gracefully.
            //this case doesn't need the Romaji parser.
        } else if (selection.equals("1")){
            String rounds = JOptionPane.showInputDialog("Enter the number of rounds\r\nIf you enter a value more than 20, you will be given periodic chances to quit before you finish");
            int roundsForQuiz = Integer.parseInt(rounds);
            SelfQuiz selfQuiz = new SelfQuiz(termVector,roundsForQuiz);
            selfQuiz.runSelfQuiz();
            System.exit(0);
            //if 1, get number of rounds, create quiz with parameters, and exit gracefully.
            //Romaji parser passed in as a parameter.
        } else if (selection.equals("2")){
            String rounds = JOptionPane.showInputDialog("Enter the number of rounds\r\nIf you enter a value more than 20, you will be given periodic chances to quit before you finish");
            int roundsForQuiz = Integer.parseInt(rounds);
            OtherQuiz otherQuiz = new OtherQuiz(termVector, roundsForQuiz,romajiParser);
            otherQuiz.runOtherQuiz();
            System.exit(0);
        }
    } // end main
}
