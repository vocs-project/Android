package vocs.com.vocs;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ASUS on 26/10/2017.
 */

public class Trad {

    @SerializedName("content")
    String content;

    @SerializedName("lang")
    String lang;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

}
