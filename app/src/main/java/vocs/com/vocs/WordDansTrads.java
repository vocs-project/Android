package vocs.com.vocs;

/**
 * Created by ASUS on 10/01/2018.
 */

public class WordDansTrads {
    private int id;
    private String content;
    private Langage langage;
    private WordDansTrads trads[];

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Langage getLangage() {
        return langage;
    }

    public void setLangage(Langage langage) {
        this.langage = langage;
    }

    public WordDansTrads[] getTrads() {
        return trads;
    }

    public void setTrads(WordDansTrads[] trads) {
        this.trads = trads;
    }
}
