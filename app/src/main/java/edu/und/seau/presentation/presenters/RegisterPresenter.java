package edu.und.seau.presentation.presenters;

import javax.inject.Inject;

import edu.und.seau.firebase.authentication.FirebaseAuthenticationInterface;
import edu.und.seau.firebase.database.FirebaseDatabaseInterface;
import edu.und.seau.firebase.models.login.RegisterModel;
import edu.und.seau.presentation.views.RegisterView;


public class RegisterPresenter extends BasePresenter {
    RegisterView view;

    FirebaseDatabaseInterface databaseInterface;
    FirebaseAuthenticationInterface authenticationInterface;
    RegisterModel registerModel = new RegisterModel();

    @Inject
    public RegisterPresenter(FirebaseDatabaseInterface databaseInterface,
                             FirebaseAuthenticationInterface authenticationInterface) {
        this.databaseInterface = databaseInterface;
        this.authenticationInterface = authenticationInterface;
    }

    public void setContext(RegisterView view)
    {
        this.view = view;
    }

    public void onRegisterClicked()
    {
        if(view != null)
        {
            registerModel.email = view.getEmail();
            registerModel.password = view.getPassword();

            authenticationInterface.register(registerModel.email,registerModel.password,registerModel.email,isSuccessful -> onRegisterResult(isSuccessful,registerModel.email,registerModel.email));
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
