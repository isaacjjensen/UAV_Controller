package edu.und.seau.firebase.models.user;

public class UserResponse {
    public String username;
    public String id;
    public String email;

    public UserResponse(String id, String username, String email)
    {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public User mapToUser()
    {
        return new User(id,username,email);
    }

    public Boolean isValid()
    {
        return (!id.isEmpty()) && (!username.isEmpty()) && (!email.isEmpty());
    }
}
