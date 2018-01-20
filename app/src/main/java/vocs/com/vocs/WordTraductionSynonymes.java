package vocs.com.vocs;

import static android.R.attr.id;

/**
 * Created by ASUS on 10/01/2018.
 */

public class WordTraductionSynonymes {

    private WordSynonymes word;

    public WordSynonymes getWord() {
        return word;
    }

    public void setWord(WordSynonymes word) {
        this.word = word;
    }

    public WordSynonymes getTrad() {
        return trad;
    }

    public void setTrad(WordSynonymes trad) {
        this.trad = trad;
    }

    private WordSynonymes trad;
}
