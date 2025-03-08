package model;

public class User {
    private int id;
    private String username;

    private String password;
    public User(String username, String password, int id){
        this.username = username;
        this.password = password;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(int id) {
        this.id = id;
    }

}
