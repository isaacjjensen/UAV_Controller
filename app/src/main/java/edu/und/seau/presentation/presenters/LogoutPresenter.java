package edu.und.seau.presentation.presenters;

import javax.inject.Inject;

import edu.und.seau.firebase.authentication.FirebaseAuthenticationInterface;
import edu.und.seau.presentation.views.LogoutView;

public class LogoutPresenter extends BasePresenter {
    FirebaseAuthenticationInterface authenticationInterface;
    LogoutView view;

    @Inject
    public LogoutPresenter(FirebaseAuthenticationInterface authenticationInterface)
    {
        this.authenticationInterface = authenticationInterface;
    }

    public void setView(LogoutView view)
    {
        this.view = view;
    }

    public void onLogoutClicked()
    {
        if(view != null)
        {
            authenticationInterface.logOut(onResult ->{

            });
            view.logout();
        }
    }
}
