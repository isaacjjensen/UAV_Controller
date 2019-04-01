package edu.und.seau.uav_controller.logout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import edu.und.seau.di.components.DaggerPresentationComponent;
import edu.und.seau.di.components.PresentationComponent;
import edu.und.seau.presentation.presenters.LogoutPresenter;
import edu.und.seau.presentation.views.LogoutView;
import edu.und.seau.uav_controller.R;
import edu.und.seau.uav_controller.databinding.LogoutScreenBinding;


public class LogoutFragment extends Fragment implements LogoutView {
    LogoutScreenBinding binding;
    PresentationComponent component;
    LogoutPresenter presenter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.logout_screen,container,false);
        component = DaggerPresentationComponent.create();
        presenter = component.getLogoutPresenter();
        presenter.setView(this);

        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogoutClicked();
            }
        });

        return binding.getRoot();
    }

    public Fragment getFragmentInstance()
    {
        return this;
    }

    public void onLogoutClicked()
    {
        presenter.onLogoutClicked();
    }

    public void logout()
    {
         ((LogoutNotificationInterface) getActivity()).logout();
    }

    public static LogoutFragment newInstance(){
        return new LogoutFragment();
    }
}
