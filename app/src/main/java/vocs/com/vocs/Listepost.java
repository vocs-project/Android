package vocs.com.vocs;

/**
 * Created by ASUS on 05/11/2017.
 */

public class Listepost {

    private String name;
    private User user[];

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User[] getUser() {
        return user;
    }

    public void setUser(User[] user) {
        this.user = user;
    }

    public WordTraduction[] getWordTrads() {
        return wordTrads;
    }

    public void setWordTrads(WordTraduction[] wordTrads) {
        this.wordTrads = wordTrads;
    }

    private WordTraduction wordTrads[];
}
