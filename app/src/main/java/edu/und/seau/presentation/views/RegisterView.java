package edu.und.seau.presentation.views;

public interface RegisterView {
    void navigateToParent();

    String getEmail();
    String getPassword();
    String getBirthday();
    String getPhoneNumber();

    void setEmail(String email);
    void setPassword(String password);
    void setBirthday(String birthday);
    void setPhoneNumber(String phoneNumber);
}
