package vocs.com.vocs;

/**
 * Created by ASUS on 10/01/2018.
 */

public class WordPatch {

    private String content;
    private String language;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int[] getTrads() {
        return trads;
    }

    public void setTrads(int[] trads) {
        this.trads = trads;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    private int trads[];
}
