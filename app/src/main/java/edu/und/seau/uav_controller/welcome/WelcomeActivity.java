package edu.und.seau.uav_controller.welcome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import edu.und.seau.di.components.DaggerPresentationComponent;
import edu.und.seau.di.components.PresentationComponent;
import edu.und.seau.presentation.presenters.WelcomePresenter;
import edu.und.seau.presentation.views.WelcomeView;
import edu.und.seau.uav_controller.R;
import edu.und.seau.uav_controller.databinding.WelcomeScreenBinding;
import edu.und.seau.uav_controller.main.MainActivity;
import edu.und.seau.uav_controller.register.RegisterActivity;

public class WelcomeActivity extends AppCompatActivity implements WelcomeView {
    WelcomePresenter presenter;
    PresentationComponent component;
    WelcomeScreenBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.welcome_screen);

        component = DaggerPresentationComponent.create();
        presenter = component.getWelcomePresenter();
        presenter.setContext(this);

        binding.loginButton.setOnClickListener(v -> onLoginClicked());
        binding.registerButton.setOnClickListener(v -> onRegisterClicked());
    }

    public String getEmail()
    {
        String returnValue = "";
        if(binding != null)
        {
            returnValue = binding.emailEntry.getText().toString();
        }
        return returnValue;
    }

    public String getPassword()
    {
        String returnValue = "";
        if(binding != null)
        {
            returnValue = binding.passwordEntry.getText().toString();
        }
        return returnValue;
    }

    public Boolean getRememberLogin(){
        Boolean returnValue = false;
        if(binding != null){
            returnValue = binding.rememberMe.isChecked();
        }
        return returnValue;
    }

    public SharedPreferences getStoredSettings(){
        return getPreferences(Context.MODE_PRIVATE);
    }

    public void setEmail(String email)
    {
        if(binding != null)
        {
            binding.emailEntry.setText(email);
        }
    }

    public void setPassword(String password)
    {
        if(binding != null)
        {
            binding.passwordEntry.setText(password);
        }
    }

    @Override
    public void setRememberLogin(Boolean rememberLogin) {
        if(binding != null)
        {
            binding.rememberMe.setChecked(rememberLogin);
        }
    }

    public void setInformaition(String informaition)
    {
        if((informaition != null) && binding != null)
        {
            binding.welcomeInformation.setText(informaition);
        }
    }

    private void onLoginClicked()
    {
        presenter.OnLoginClicked();
    }

    private void onRegisterClicked()
    {
        presenter.OnRegisterClicked();
    }

    @Override
    public void startMainActivity()
    {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void startRegisterActivity()
    {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    public  void onBackPressed(){ }
}
