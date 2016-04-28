package models;

/**
 * Created by thaodang on 26/4/16.
 */
public class User {

    protected String userame;
    protected String password;

    public void setUsername(String userame) {
        this.userame = userame;
    }

    public String getUsername() {
        return userame;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

}