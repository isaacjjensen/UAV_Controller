package edu.und.seau.presentation.presenters;

import android.content.SharedPreferences;

import javax.inject.Inject;

import edu.und.seau.firebase.authentication.FirebaseAuthenticationInterface;
import edu.und.seau.firebase.models.login.LoginRequest;
import edu.und.seau.presentation.views.WelcomeView;

public class WelcomePresenter extends BasePresenter {
    private FirebaseAuthenticationInterface authenticationInterface;
    private WelcomeView view;
    private SharedPreferences _preferences;

    private static final String KEY_REMEMBERLOGIN = "REMEMBER_ME";
    private static final String KEY_EMAIL = "EMAIL";

    @Inject
    public WelcomePresenter(FirebaseAuthenticationInterface authenticationInterface) {
        this.authenticationInterface = authenticationInterface;
    }

    public void setContext(WelcomeView view) {
        this.view = view;
        initializeViewData();
    }

    private void initializeViewData()
    {
        if(view != null)
        {
            _preferences = view.getStoredSettings();
            if(_preferences != null)
            {
                Boolean rememberLogin = _preferences.getBoolean(KEY_REMEMBERLOGIN,false);
                String email = _preferences.getString(KEY_EMAIL,"");
                view.setRememberLogin(rememberLogin);
                if(rememberLogin){
                    view.setEmail(email);
                }
            }
        }
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
                        saveRememberLogin();
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

    private void saveRememberLogin(){
        if(_preferences != null) {
           if(view != null) {
               Boolean rememberLogin = view.getRememberLogin();
               String email = view.getEmail();
               SharedPreferences.Editor editor = _preferences.edit();
               editor.putBoolean(KEY_REMEMBERLOGIN,rememberLogin);
               if(rememberLogin)
               {
                   editor.putString(KEY_EMAIL,email);
               }
               editor.apply();
           }
        }
    }
}
