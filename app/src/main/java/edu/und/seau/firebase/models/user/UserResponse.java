package edu.und.seau.firebase.models.user;

import javax.inject.Inject;

import edu.und.seau.common.ValidationHelpersKt;

public class UserResponse {
    public String email = "";
    public String password = "";
    public String repeatedPassword = "";

    @Inject
    public UserResponse()
    {

    }

    public User mapToUser()
    {
        return new User();
    }

    public Boolean isEmailValid() {
        return  ValidationHelpersKt.isEmailValid(email);
    }

    public Boolean isPasswordValid(){ return ValidationHelpersKt.isPasswordValid(password); }

    public Boolean arePasswordsSame() { return ValidationHelpersKt.arePasswordsSame(password,repeatedPassword); }

    public Boolean isValid()
    {
        return  (isEmailValid()
                && isPasswordValid()
                && arePasswordsSame());
    }
}
