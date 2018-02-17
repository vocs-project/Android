package vocs.com.vocs;

/**
 * Created by ASUS on 11/02/2018.
 */

public class WordTraductionStat {

    private int id;
    private Word word;
    private Word trad;

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

    public Stat getStat() {
        return stat;
    }

    public void setStat(Stat stat) {
        this.stat = stat;
    }

    private Stat stat;
}
