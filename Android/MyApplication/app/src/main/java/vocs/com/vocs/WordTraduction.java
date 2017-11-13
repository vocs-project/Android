package vocs.com.vocs;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ASUS on 26/10/2017.
 */

public class WordTraduction {

    private int id;
    private Word word;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Word getTrad() {
        return trad;
    }

    public void setTrad(Word trad) {
        this.trad = trad;
    }

    private Word trad;
}
