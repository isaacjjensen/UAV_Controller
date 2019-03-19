package edu.und.seau.uav_controller;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;
import edu.und.seau.di.AppComponent;
import edu.und.seau.di.DaggerAppComponent;


public class App extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        if(component == null)
        {
            component = DaggerAppComponent.builder().build();
        }
    }
}
