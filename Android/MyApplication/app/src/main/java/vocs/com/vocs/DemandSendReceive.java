package vocs.com.vocs;

/**
 * Created by ASUS on 07/11/2017.
 */

public class DemandSendReceive {

    private int id;
    private UserSend userSend;
    private UserSend userReceive;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserSend getUserSend() {
        return userSend;
    }

    public void setUserSend(UserSend userSend) {
        this.userSend = userSend;
    }

    public UserSend getUserReceive() {
        return userReceive;
    }

    public void setUserReceive(UserSend userReceive) {
        this.userReceive = userReceive;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    private Classe classe;
}
