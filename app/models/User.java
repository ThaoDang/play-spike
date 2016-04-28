package models;

/**
 * Created by thaodang on 26/4/16.
 */
public class User {

    protected String username;
    protected String password;

    public void setUsername(String userame) {
        this.username = userame;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

}