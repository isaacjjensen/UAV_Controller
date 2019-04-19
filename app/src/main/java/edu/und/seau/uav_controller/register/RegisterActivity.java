package edu.und.seau.uav_controller.register;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.databinding.DataBindingUtil;
import edu.und.seau.common.ValidationHelpersKt;
import edu.und.seau.di.components.DaggerPresentationComponent;
import edu.und.seau.di.components.PresentationComponent;
import edu.und.seau.firebase.models.user.UserResponse;
import edu.und.seau.presentation.presenters.RegisterPresenter;
import edu.und.seau.presentation.views.RegisterView;
import edu.und.seau.uav_controller.R;
import edu.und.seau.uav_controller.databinding.RegisterScreenBinding;

public class RegisterActivity extends AppCompatActivity implements RegisterView {
    PresentationComponent component;
    RegisterScreenBinding binding;
    RegisterPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.register_screen);
        component = DaggerPresentationComponent.create();
        presenter = component.getRegisterPrensenter();

        presenter.setContext(this);

        binding.registerButton.setOnClickListener(v -> onRegisterClicked());
        binding.emailEntry.setOnFocusChangeListener((v, hasFocus) -> onEmailChanged());
        binding.passwordEntry.setOnFocusChangeListener((v, hasFocus) -> onPasswordChanged());
        binding.passwordEntryRepeat.setOnFocusChangeListener((v,hasFocus)->onPasswordRepeatChanged());
    }

    private void onPasswordRepeatChanged(){
        if(presenter != null) {
            presenter.onRepeatPasswordChanged();
        }
    }

    private void onPasswordChanged() {
        if(presenter != null) {
            presenter.onPasswordChanged();
        }
    }

    private void onEmailChanged() {
        if(presenter != null) {
            presenter.onEmailChanged();
        }
    }

    private void onRegisterClicked()
    {
        if(presenter != null){
            presenter.onRegisterClicked();
        }
    }

    public void navigateToParent()
    {
        NavUtils.navigateUpFromSameTask(this);
    }

    public String getNotificationMessage(){
        String returnValue = null;
        if(binding != null){
            returnValue = binding.welcomeInformation.getText().toString();
        }
        return returnValue;
    }

    public void setNotificationMessage(String message){
        if(binding != null){
            binding.welcomeInformation.setText(message);
        }
    }

    @Override
    public String getEmail() {
        String returnValue = null;
        if(binding != null)
        {
            returnValue = binding.emailEntry.getText().toString();
        }
        return returnValue;
    }

    @Override
    public String getPassword() {
        String returnValue = null;
        if(binding != null)
        {
            returnValue = binding.passwordEntry.getText().toString();
        }
        return returnValue;
    }

    @Override
    public String getRepeatPassword() {
        String response = null;
        if(binding != null)
        {
            response = binding.passwordEntryRepeat.getText().toString();
        }
        return response;
    }

    @Override
    public void setEmail(String email) {
        if(binding != null)
        {
            binding.emailEntry.setText(email);
        }
    }

    @Override
    public void setPassword(String password) {
        if(binding != null)
        {
            binding.passwordEntry.setText(password);
        }
    }

    @Override
    public void setRepeatPassword(String password) {
        if(binding != null)
        {
            binding.passwordEntryRepeat.setText(password);
        }
    }

    @Override
    public void setEmailInputError(String errorMessage) {
        if(binding != null) {
            binding.emailEntry.setError(errorMessage);
        }
    }

    @Override
    public void setPasswordInputError(String errorMessage) {
        if(binding != null) {
            binding.passwordEntry.setError(errorMessage);
        }
    }

    @Override
    public void setRepeatPasswordInputError(String errorMessage) {
        if(binding != null) {
            binding.passwordEntryRepeat.setError(errorMessage);
        }
    }
}
