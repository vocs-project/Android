package vocs.com.vocs;

/**
 * Created by ASUS on 11/02/2018.
 */

public class Stat {

    private int id;
    private int level;
    private int goodRepetition;
    private int badRepetition;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getGoodRepetition() {
        return goodRepetition;
    }

    public void setGoodRepetition(int goodRepetition) {
        this.goodRepetition = goodRepetition;
    }

    public int getBadRepetition() {
        return badRepetition;
    }

    public void setBadRepetition(int badRepetition) {
        this.badRepetition = badRepetition;
    }
}
