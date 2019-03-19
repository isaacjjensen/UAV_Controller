package edu.und.seau.firebase.models.login;

import static edu.und.seau.common.ValidationHelpersKt.isEmailValid;
import static edu.und.seau.common.ValidationHelpersKt.isPasswordValid;

public class RegisterModel {
    public String email = "";
    public String password = "";

    public RegisterModel()
    {

    }

    public  Boolean isValid()
    {
        return isEmailValid(email) && isPasswordValid(password);
    }

}
