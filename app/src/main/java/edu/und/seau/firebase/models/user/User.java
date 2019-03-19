package edu.und.seau.firebase.models.user;

public class User {
    public String id;
    public String username;
    public String email;

    public User(String id, String username, String email)
    {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public Boolean isValid()
    {
        return (!id.isEmpty()) && (!username.isEmpty()) && (!email.isEmpty());
    }
}
