package edu.und.seau.presentation.presenters;

import javax.inject.Inject;

import edu.und.seau.firebase.authentication.FirebaseAuthenticationInterface;
import edu.und.seau.presentation.views.MainView;

public class MainPresenter extends BasePresenter {
    MainView view;
    FirebaseAuthenticationInterface firebaseAuth;

    @Inject
    public MainPresenter(FirebaseAuthenticationInterface authenticationInterface) {
        firebaseAuth = authenticationInterface;
    }

    public void onLogoutClicked(){
        firebaseAuth.logOut(v->onLogoutComplete());
    }

    private void onLogoutComplete(){
        if(view != null){
            view.NavigateToWelcomeScreen();
        }
    }

    public void setView(MainView view)
    {
        this.view = view;
    }
}
