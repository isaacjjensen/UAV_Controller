package edu.und.seau.uav_controller.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.databinding.DataBindingUtil;
import edu.und.seau.di.components.DaggerRegisterActivityComponent;
import edu.und.seau.di.components.RegisterActivityComponent;
import edu.und.seau.presentation.presenters.RegisterPresenter;
import edu.und.seau.presentation.views.RegisterView;
import edu.und.seau.uav_controller.R;
import edu.und.seau.uav_controller.databinding.RegisterScreenBinding;

public class RegisterActivity extends AppCompatActivity implements RegisterView {
    RegisterActivityComponent component;
    RegisterScreenBinding binding;
    RegisterPresenter presenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.register_screen);
        component = DaggerRegisterActivityComponent.create();
        presenter = component.getRegisterPrensenter();
        presenter.setContext(this);

        binding.registerButton.setOnClickListener(v -> onRegisterClicked());
    }

    private void onRegisterClicked()
    {
        presenter.onRegisterClicked();
    }

    public void navigateToParent()
    {
        NavUtils.navigateUpFromSameTask(this);
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
    public String getBirthday() {
        String returnValue = null;
        if(binding != null)
        {
            returnValue = binding.birthdayEntry.getText().toString();
        }
        return returnValue;
    }

    @Override
    public String getPhoneNumber() {
        String returnValue = null;
        if(binding != null)
        {
            returnValue = binding.phoneEntry.getText().toString();
        }
        return returnValue;
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
    public void setBirthday(String birthday) {
        if(binding != null)
        {
            binding.birthdayEntry.setText(birthday);
        }
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        if(binding != null)
        {
            binding.phoneEntry.setText(phoneNumber);
        }
    }
}
