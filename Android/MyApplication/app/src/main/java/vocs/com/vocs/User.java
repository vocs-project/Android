package vocs.com.vocs;

import java.util.List;

/**
 * Created by ASUS on 05/10/2017.
 */

public class User {

    private int id;
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstandSur(){return firstname.concat(" ").concat(surname);}

    private String firstname;
    private String surname;
    private String password;
    private List<Classes> classes;
    private String roles[];

    public List<Liste> getListes() {
        return lists;
    }

    public void setListes(List<Liste> listes) {
        this.lists = listes;
    }

    private List<Liste> lists;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String[] getRoles() {
        return roles;
    }

    public List<Classes> getClasses() {
        return classes;
    }

    public void setClasses(List<Classes> classes) {
        this.classes = classes;
    }

    public void setRoles(String[] role) {
        this.roles = role;
    }
}
