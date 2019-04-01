package edu.und.seau.presentation.views;

import android.content.SharedPreferences;

import edu.und.seau.uav_controller.main.MainActivity;

public interface WelcomeView {
    String getEmail();
    String getPassword();
    Boolean getRememberLogin();
    SharedPreferences getStoredSettings();

    void setEmail(String email);
    void setPassword(String password);
    void setRememberLogin(Boolean rememberLogin);

    void setInformaition(String informaition);
    void startMainActivity();
    void startRegisterActivity();

}
