package vocs.com.vocs;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ASUS on 26/10/2017.
 */

public class Trads {

    @SerializedName("content")
    String content;

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

    @SerializedName("lang")
    String lang;
}
