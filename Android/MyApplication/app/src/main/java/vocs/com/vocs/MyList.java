package vocs.com.vocs;

import static android.R.attr.id;

/**
 * Created by ASUS on 12/06/2017.
 */

public class MyList {

    private String idList;
    private String name;

    public MyList(String idList, String name) {
        this.name = name;
        this.idList = idList;
    }
    public String getIdList() {
        return this.idList;
    }

    public String getName (){
        return this.name;
    }

}
