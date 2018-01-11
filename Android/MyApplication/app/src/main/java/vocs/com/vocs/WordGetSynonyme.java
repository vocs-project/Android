package vocs.com.vocs;

/**
 * Created by ASUS on 10/01/2018.
 */

    public class WordGetSynonyme {
    private WordDansTrads trads[];
    private int id;
    private Langage language;
    private String content;

    public WordDansTrads[] getTrads() {
        return trads;
    }

    public void setTrads(WordDansTrads[] trads) {
        this.trads = trads;
    }

    public Langage getLanguage() {
        return language;
    }

    public void setLanguage(Langage language) {
        this.language = language;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
