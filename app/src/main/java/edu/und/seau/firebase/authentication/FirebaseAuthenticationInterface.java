package edu.und.seau.firebase.authentication;

import java.util.function.Predicate;

import androidx.core.util.Consumer;

public interface FirebaseAuthenticationInterface {

    void login(String email, String password, Consumer<Boolean> onResult);

    void register(String email, String password, String userName, Consumer<Boolean> onResult);

    String getUserId();
    String getUserName();
    void logOut(Consumer<Boolean> onResult);
}
