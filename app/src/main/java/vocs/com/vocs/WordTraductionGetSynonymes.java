package vocs.com.vocs;

/**
 * Created by ASUS on 10/01/2018.
 */

public class WordTraductionGetSynonymes {

    private int id;
    private WordGetSynonyme word;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public WordGetSynonyme getTrad() {
        return trad;
    }

    public void setTrad(WordGetSynonyme trad) {
        this.trad = trad;
    }

    public WordGetSynonyme getWord() {
        return word;
    }

    public void setWord(WordGetSynonyme word) {
        this.word = word;
    }

    private WordGetSynonyme trad;
}
