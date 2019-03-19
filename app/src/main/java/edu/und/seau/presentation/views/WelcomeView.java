package edu.und.seau.presentation.views;

import edu.und.seau.uav_controller.main.MainActivity;

public interface WelcomeView {
    String getEmail();
    String getPassword();
    void setEmail(String email);
    void setPassword(String password);
    void setInformaition(String informaition);
    void startMainActivity();
    void startRegisterActivity();

}
