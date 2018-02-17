package vocs.com.vocs;

import java.util.List;

/**
 * Created by ASUS on 11/02/2018.
 */

public class ListeTout {

    private int id;
    private String name;
    private String creationDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public List<WordTraductionStat> getWordTrads() {
        return wordTrads;
    }

    public void setWordTrads(List<WordTraductionStat> wordTrads) {
        this.wordTrads = wordTrads;
    }

    private List<WordTraductionStat> wordTrads;

}
