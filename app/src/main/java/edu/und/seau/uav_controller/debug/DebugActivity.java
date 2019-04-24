package edu.und.seau.uav_controller.debug;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import edu.und.seau.di.components.DaggerPresentationComponent;
import edu.und.seau.di.components.PresentationComponent;
import edu.und.seau.firebase.commands.CommandManager;
import edu.und.seau.presentation.presenters.DebugPresenter;
import edu.und.seau.uav_controller.R;

public class DebugActivity extends AppCompatActivity {

    private PresentationComponent component;
    private DebugPresenter presenter;

    Boolean isLoggedin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        component = DaggerPresentationComponent.create();
        presenter = component.getDebugPresenter();

        logIn();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());



    }

    private void logIn(){
        presenter.authenticationInterface.login("debug@debug.com", "debug123", aBoolean -> {
            sendCommand();
        });
    }

    private void sendCommand(){
        String userKey = presenter.authenticationInterface.getUserId();
        //presenter.manager.SendCommand2(userKey,"eryneycdzdjqkdh",null);
    }

}
