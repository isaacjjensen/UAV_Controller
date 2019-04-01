package edu.und.seau.presentation.presenters;

import javax.inject.Inject;

import edu.und.seau.firebase.authentication.FirebaseAuthenticationInterface;
import edu.und.seau.firebase.database.FirebaseDatabaseInterface;
import edu.und.seau.firebase.models.login.RegisterModel;
import edu.und.seau.firebase.models.user.UserResponse;
import edu.und.seau.presentation.views.RegisterView;


public class RegisterPresenter extends BasePresenter {
    private RegisterView view;

    private FirebaseDatabaseInterface databaseInterface;
    private FirebaseAuthenticationInterface authenticationInterface;
    private RegisterModel registerModel = new RegisterModel();
    private UserResponse _userEntry;

    @Inject
    public RegisterPresenter(FirebaseDatabaseInterface databaseInterface,
                             FirebaseAuthenticationInterface authenticationInterface,
                             UserResponse userEntry) {
        this.databaseInterface = databaseInterface;
        this.authenticationInterface = authenticationInterface;
        _userEntry = userEntry;
    }

    public void setContext(RegisterView view)
    {
        this.view = view;
    }

    public void onRegisterClicked()
    {
        if(view != null)
        {
            onEmailChanged();
            onPasswordChanged();
            onRepeatPasswordChanged();
            if(_userEntry.isValid()) {
                authenticationInterface.register(_userEntry.email, _userEntry.password, _userEntry.email, isSuccessful -> onRegisterResult(isSuccessful, _userEntry.email, _userEntry.email));
            }
        }
    }

    public void onEmailChanged(){
        if(view != null){
            String email = view.getEmail();
            if(!email.equals(_userEntry.email)){
                _userEntry.email = email;

                if(_userEntry.isEmailValid())
                {
                    view.setEmailInputError(null);
                }
                else{
                    view.setEmailInputError("Please Enter a Valid Email...");
                }
            }
        }
    }

    public void onPasswordChanged(){
        if(view != null){
            String password = view.getPassword();
            if(!password.equals(_userEntry.password)){
                _userEntry.password = password;
                if(_userEntry.isPasswordValid())
                {
                    view.setPasswordInputError(null);
                }
                else{
                    view.setPasswordInputError("Please Enter a Valid Password...");
                }
            }
        }
    }

    public void onRepeatPasswordChanged(){
        if(view != null){
            String password = view.getRepeatPassword();
            if(!password.equals(_userEntry.repeatedPassword)){
                _userEntry.repeatedPassword = password;
                if(_userEntry.arePasswordsSame())
                {
                    view.setRepeatPasswordInputError(null);
                }
                else{
                    view.setRepeatPasswordInputError("Your Passwords Do Not Match.");
                }
            }
        }
    }

    private void onRegisterResult(Boolean isSuccessful, String name, String email)
    {
        if(isSuccessful)
        {
            createUser(name, email);
            if(view != null)
            {
                view.navigateToParent();
            }
        }
    }

    private void createUser(String name, String email)
    {
        String id = authenticationInterface.getUserId();
        databaseInterface.createUser(id,name,email);
    }
}
