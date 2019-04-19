package edu.und.seau.presentation.views;

public interface RegisterView {
    void navigateToParent();

    String getEmail();
    String getPassword();
    String getRepeatPassword();
    String getNotificationMessage();

    void setEmail(String email);
    void setPassword(String password);
    void setRepeatPassword(String password);
    void setNotificationMessage(String message);

    void setEmailInputError(String errorMessage);
    void setPasswordInputError(String errorMessage);
    void setRepeatPasswordInputError(String errorMessage);
}
