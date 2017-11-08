package vocs.com.vocs;

import java.util.List;

/**
 * Created by ASUS on 31/10/2017.
 */

public class Classes {

    private int id;

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

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public List<SimpleUser> getUsers() {
        return users;
    }

    public void setUsers(List<SimpleUser> users) {
        this.users = users;
    }

    public List<Liste> getLists() {
        return lists;
    }

    public void setLists(List<Liste> lists) {
        this.lists = lists;
    }

    private String name;
    private String school;
    private List<SimpleUser> users;
    private List<Liste> lists;


}
