package vocs.com.vocs;

import java.util.ArrayList;

/**
 * Created by ASUS on 24/10/2017.
 */

public class LoginInfo {

    private String email;
    private String password;

    public LoginInfo(String email, String password){
        this.email=email;
        this.password=password;
    }
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}