package edu.und.seau.firebase.models.user;
import javax.inject.Inject;

import edu.und.seau.common.ValidationHelpersKt;


public class User {
    public String id;
    public String username;
    public String email;

    @Inject
    public User(){

    }

    public User(String id, String username, String email){
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public Boolean isValid() {
        return (!id.isEmpty()) && (!username.isEmpty()) && (!email.isEmpty());
    }

    public Boolean isEmailValid() {
        return  ValidationHelpersKt.isEmailValid(email);
    }
}
