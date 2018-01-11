package vocs.com.vocs;

import java.util.List;

/**
 * Created by ASUS on 10/01/2018.
 */

public class DemandeSynonyme {
    private int userSend;
    private int userReceive;
    private WordTraductionSynonymes wordTrad;

    public int getUserSend() {
        return userSend;
    }

    public void setUserSend(int userSend) {
        this.userSend = userSend;
    }

    public int getUserReceive() {
        return userReceive;
    }

    public void setUserReceive(int userReceive) {
        this.userReceive = userReceive;
    }

    public WordTraductionSynonymes getWordTrad() {
        return wordTrad;
    }

    public void setWordTrad(WordTraductionSynonymes wordTrad) {
        this.wordTrad = wordTrad;
    }

    public void getListe(){return;}
    public void getClasse(){return;}
}
