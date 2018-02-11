package vocs.com.vocs;

       import com.google.gson.annotations.SerializedName;

        import java.util.ArrayList;
        import java.util.List;

       import static android.R.attr.name;

/**
 * Created by ASUS on 23/10/2017.
 */

public class MotsListe {

    private int id;
    private String name;
    private String creationDate;
    private List<WordTraductionStat> wordTrads;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }


    public void setWordTrads(List<WordTraductionStat> wordTrads) {
        this.wordTrads = wordTrads;
    }

    public List<WordTraductionStat> getWordTrads() {
        return wordTrads;
    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }
}
