package edu.und.seau.firebase.authentication;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import java.util.Objects;

import javax.inject.Inject;

import androidx.core.util.Consumer;

public class FirebaseAuthenticationManager implements FirebaseAuthenticationInterface {
    
    private FirebaseAuth firebaseAuth;

    @Inject
    public FirebaseAuthenticationManager(FirebaseAuth firebaseAuth)
    {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void login(String email, String password, Consumer<Boolean> onResult) {
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> onResult.accept(task.isComplete() && task.isSuccessful()));
    }

    @Override
    public void register(String email, String password, String userName, Consumer<Boolean> onResult) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if(task.isComplete() && task.isSuccessful())
                    {
                        Objects.requireNonNull(firebaseAuth.getCurrentUser()).updateProfile(
                                new UserProfileChangeRequest.
                                        Builder()
                                        .setDisplayName(userName)
                                        .build());
                        onResult.accept(true);
                    }
                    else{
                        onResult.accept(false);
                    }
                });
    }

    @Override
    public String getUserId() {
        String returnValue = "";
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null)
        {
            returnValue = user.getUid();
        }
        return returnValue;
    }

    @Override
    public String getUserName() {
        String returnValue = "";
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            returnValue = user.getDisplayName();
        }
        return returnValue;
    }

    @Override
    public void logOut(Consumer<Boolean> onResult) {
        firebaseAuth.signOut();
        onResult.accept(true);
    }
}
