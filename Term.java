public class Term {
    String englishDefinition;
    String kanaReading;
    String kanji;

    Term(String def, String kana, String kan){
        this.englishDefinition = def;
        this.kanaReading = kana;
        this.kanji = kan;
    }

    public String getEnglishDefinition() {
        return englishDefinition;
    }

    public String getKanaReading() {
        return kanaReading;
    }

    public String getKanji() {
        return kanji;
    }
}
