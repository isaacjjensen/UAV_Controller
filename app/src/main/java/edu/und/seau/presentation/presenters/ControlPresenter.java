package edu.und.seau.presentation.presenters;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import edu.und.seau.presentation.views.ControlFragmentView;
import edu.und.seau.uav_controller.googlemaps.GoogleMapsInterface;

public class ControlPresenter implements OnMapReadyCallback {

    ControlFragmentView view;
    GoogleMapsInterface googleMapsInterface;

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;

    private Timer cTimer;
    private Timer lTimer;

    private Location lastLocation;

    private float xPosition;
    private float yPosition;
    private float zPosition;

    private float xPoll;
    private float yPoll;
    private float zPoll;

    private long cmdDelay;
    private long locDelay;

    @Inject
    public ControlPresenter(GoogleMapsInterface googleMapsInterface)
    {
        cmdDelay = 250; // TODO: Decide on a way to dynamically set this (set through UI settings?)
        locDelay = 5000; // TODO: Also dynamically set this delay
        this.googleMapsInterface = googleMapsInterface;
    }

    public void readyGoogleMap(Fragment fragment, Context context, Activity activity) {
        googleMapsInterface.getLocationPermission(context, activity);
        googleMapsInterface.getMapFragment(fragment, this);
        mFusedLocationClient =  googleMapsInterface.getFusedLocationProvider(activity);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        googleMapsInterface.moveToMyLocation(mMap, mFusedLocationClient);
        googleMapsInterface.setZoom(mMap, 15.0f);
    }

    public void onStartClicked() {
        // TODO: Decide on functionality of start button (starts "Serial Connection?")
        // For now Log to make sure it works
        Log.d("ControlPresenter", "<< START >> Clicked");

    }

    public void onStopClicked() {
        // TODO: Decide on functionality of stop button (stops all power to servos and stops serial writing?)
        // For now Log to make sure it works
        Log.d("ControlPresenter", "<< STOP >> Clicked");
    }

    public void onCmdToggle(boolean isChecked) {
        if (isChecked) {
            // Toggle is ON
            Log.d("ControlPresenter", "<< CMD TOGGLE ON >>");
            cTimer = new Timer();
            cTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    pollForMovementCommand();
                }
            },0, cmdDelay);
        } else {
            // Toggle is OFF
            Log.d("ControlPresenter", "<< CMD TOGGLE OFF >>");
            cTimer.cancel();
            cTimer.purge();
        }
    }

    public void onLocToggle(boolean isChecked, Activity activity) {
        if (isChecked) {
            // Toggle is ON
            Log.d("ControlPresenter", "<< LOC TOGGLE ON >>");
            lTimer = new Timer();
            lTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            lastLocation = pollForLocation();
                            googleMapsInterface.moveToLocation(mMap, lastLocation);
                        }
                    });
                }
            },0, locDelay);
        } else {
            // Toggle is OFF
            Log.d("ControlPresenter", "<< LOC TOGGLE OFF >>");
            lTimer.cancel();
            lTimer.purge();
        }
    }

    public void onVerticalMove(float z) {
        zPosition = z;
    }

    public void onHorizontalMove(float x, float y) {
        xPosition = x;
        yPosition = y;
    }

    public void onCtrlFabClicked() {
        googleMapsInterface.moveToMyLocation(mMap, mFusedLocationClient);
    }

    private void pollForMovementCommand() {
        if ((xPoll != xPosition) || (yPoll != yPosition) ||
                (zPoll != zPosition) || (zPoll != 0.0f)) {
            xPoll = xPosition;
            yPoll = yPosition;
            zPoll = zPosition;
            Log.d("ControlPresenter", "<< NEW MOVEMENT COMMAND >> -> " +
                    xPoll + " " + yPoll + " " + zPoll);
            // TODO: Send out a movement command
        }
    }

    private Location pollForLocation() {
        Log.d("ControlPresenter", "Polling for UAV's Location");
        // TODO: Send out a command to request the UAV's location and wait for a response
        Location location = new Location("FireBase");
        location.setLongitude(0.0);
        location.setLatitude(0.0);
        return location;
    }

    public void setView(ControlFragmentView view)
    {
        this.view = view;
    }
}
