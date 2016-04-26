package model;

/**
 * Created by thaodang on 26/4/16.
 */
public class User {

    protected String userName;
    protected String password;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

}