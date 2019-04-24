package edu.und.seau.uav_controller.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import edu.und.seau.di.components.DaggerPresentationComponent;
import edu.und.seau.di.components.PresentationComponent;
import edu.und.seau.presentation.presenters.MainPresenter;
import edu.und.seau.presentation.views.ControllerView;
import edu.und.seau.presentation.views.LogoutView;
import edu.und.seau.presentation.views.MainView;
import edu.und.seau.presentation.views.NewsView;
import edu.und.seau.presentation.views.SelectUAVView;
import edu.und.seau.uav_controller.R;
import edu.und.seau.uav_controller.control.ControlScreenFragment;
import edu.und.seau.uav_controller.control.SelectUAVFragment;
import edu.und.seau.uav_controller.control.SelectUAVNotificationInterface;
import edu.und.seau.uav_controller.databinding.ActivityMainBinding;
import edu.und.seau.uav_controller.joystick.JoystickListener;
import edu.und.seau.uav_controller.logout.LogoutFragment;
import edu.und.seau.uav_controller.logout.LogoutNotificationInterface;
import edu.und.seau.uav_controller.news.NewsFragment;
import edu.und.seau.uav_controller.welcome.WelcomeActivity;

public class MainActivity
        extends AppCompatActivity
        implements MainView, LogoutNotificationInterface, SelectUAVNotificationInterface {
    ActivityMainBinding binding;
    PresentationComponent component;
    MainPresenter presenter;

    ActionBar toolbar;
    BottomNavigationView bottomNavigationView;

    NewsView newsFragment;
    LogoutView logoutFragment;
    ControllerView controllerView;
    SelectUAVView selectUAVView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        component = DaggerPresentationComponent.create();
        presenter = component.getMainPresenter();
        presenter.setView(this);

        newsFragment = component.getNewsView();
        logoutFragment = component.getLogoutView();
        controllerView = component.getControllerView();
        selectUAVView = component.getSelectUAVView();

        initToolbar();
        bottomNavigationView = binding.bottomNavigation;
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        bottomNavigationView.setSelectedItemId(R.id.navigate_news_activity);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            int menuItemID = menuItem.getItemId();
            Fragment fragment;
            switch (menuItemID)
            {
                case  R.id.navigate_control_activity :
                    fragment = selectUAVView.getFragmentInstance();
                    break;
                case R.id.navigate_news_activity :
                    fragment = newsFragment.getFragmentInstance();
                    break;
                case R.id.navigate_profile_activity:
                    return true;
                case R.id.navigate_logout:
                    fragment = logoutFragment.getFragmentInstance();
                    break;
                default:
                    return false;
            }
            openFragment(fragment);
            return true;
        }
    };



    private void initToolbar()
    {
        if(toolbar == null)
        {
            toolbar = getSupportActionBar();
        }
    }

    private void openFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void logout()
    {
        presenter.onLogoutClicked();
    }

    public void NavigateToWelcomeScreen(){
        startActivity(new Intent(this, WelcomeActivity.class));
    }

    public void uavSelected(String uavID) {
        openFragment(controllerView.getFragmentInstance());
    }
}
