package vocs.com.vocs;

/**
 * Created by ASUS on 26/10/2017.
 */

public class Userpost {

    private String firstname;

    private String surname;

    private String email;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Integer[] getClasses() {
        return classes;
    }

    public void setClasses(Integer[] classes) {
        this.classes = classes;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    private String password;

    private String roles[];

    private Integer classes[];

    public void setAll(String firstname, String surname, String password, String email, String roles[], Integer classes[]){
        this.firstname = firstname;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.roles = roles;
        this.classes = classes;
    }
}
