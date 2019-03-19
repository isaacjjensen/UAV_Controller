package edu.und.seau.presentation.presenters;

import javax.inject.Inject;

import edu.und.seau.firebase.authentication.FirebaseAuthenticationInterface;
import edu.und.seau.firebase.authentication.FirebaseAuthenticationManager;
import edu.und.seau.firebase.models.login.LoginRequest;
import edu.und.seau.presentation.views.WelcomeView;

public class WelcomePresenter extends BasePresenter {
    FirebaseAuthenticationInterface authenticationInterface;
    WelcomeView view;

    @Inject
    public WelcomePresenter(FirebaseAuthenticationInterface authenticationInterface) {
        this.authenticationInterface = authenticationInterface;
    }

    public void setContext(WelcomeView view)
    {
        this.view = view;
    }

    public void OnRegisterClicked()
    {
        if(view != null)
        {
            view.startRegisterActivity();
        }
    }

    public void OnLoginClicked()
    {

        if(view != null)
        {
            LoginRequest request = new LoginRequest();
            request.email = view.getEmail();
            request.password = view.getPassword();
            if(request.isValid())
            {
                authenticationInterface.login(request.email,request.password, isSuccess -> {
                    if(isSuccess)
                    {
                        view.startMainActivity();
                    }
                    else
                    {
                        view.setInformaition("Username or Password is Incorrect");
                    }
                });
            }
            else
            {
                view.setInformaition("Username or Password is Not Valid");
            }


        }
    }

}
