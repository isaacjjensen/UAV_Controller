package edu.und.seau.uav_controller;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;


public class control_screen  extends FragmentActivity
        implements JoystickView.JoystickListener,
            WarningDialog.WarningDialogListener,
            GoogleMap.OnMyLocationButtonClickListener,
            GoogleMap.OnMyLocationClickListener,
            OnMapReadyCallback {

    // Button and Text Field Vars
//    private Button btn_start;
//    private Button btn_land;

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int MY_LOCATION_REQUEST_CODE = 1;
    private boolean mLocationPermissionGranted;

    private ToggleButton toggleCmdPoll;

    //private EditText input_msg;
    private TextView chat_conversation;

    private String user_name, UAV_name;
    private DatabaseReference root;
    private CommandPoller poller;
    private Thread pollThread;
    private String temp_key;

    // GPS Vars
    public String[] gps_array = {"", "", "", ""};
    public static long time = 0;
    public static double latitude = 0;
    public static double longitude = 0;
    public static double altitude = 0;

    // Time in ms the application waits before polling for change in command
    // *** This is a precaution for (avoiding) flooding the firebase database with commands
    private long pollTime = 200;

    public static float commandX;
    public static float commandY;
    public static float commandZ;

    // Map UI Button declarations
    private FloatingActionButton fab_uav;
    private FloatingActionButton fab_ctrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLocationPermission();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.control_screen);

        fab_uav = findViewById(R.id.fab_uav);
        fab_ctrl = findViewById(R.id.fab_ctrl);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Initialize Button and Text Vars
        chat_conversation = findViewById(R.id.textView);
//        btn_start = findViewById(R.id.btn_start);
//        btn_land = findViewById(R.id.btn_stop);

        toggleCmdPoll = findViewById(R.id.toggleCmdPoll);

        user_name = getIntent().getExtras().get("user_name").toString();
        UAV_name = getIntent().getExtras().get("UAV_name").toString();
        setTitle(" UAV Control: " + user_name);

        root = FirebaseDatabase.getInstance().getReference().child(UAV_name);

        poller = new CommandPoller(pollTime, root, user_name);
        pollThread = new Thread(poller);

        commandX = 0.0f;
        commandY = 0.0f;
        commandZ = 0.0f;

//        btn_start.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Map<String, Object> map = new HashMap<String, Object>();
//                temp_key = root.push().getKey();
//                root.updateChildren(map);
//                DatabaseReference message_root = root.child(temp_key);
//                Map<String, Object> map2 = new HashMap<String, Object>();
//                map2.put("name", user_name);
//                map2.put("msg", "OO"); //Start
//                message_root.updateChildren(map2);
//            }
//        });
//        btn_land.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Map<String, Object> map = new HashMap<String, Object>();
//                temp_key = root.push().getKey();
//                root.updateChildren(map);
//                DatabaseReference message_root = root.child(temp_key);
//                Map<String, Object> map2 = new HashMap<String, Object>();
//                map2.put("name", user_name);
//                map2.put("msg", "PP"); //STOP
//                message_root.updateChildren(map2);
//            }
//        });

        toggleCmdPoll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Toggle is ON
                    pollThread.start();
                } else {
                    // Toggle is OFF
                    poller.stop();
                    try {
                        pollThread.join();
                    } catch (Exception e) {
                        Log.d("Control Poll", "Exception joining command polling thread: " + e);
                    }
                }

            }
        });

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                append_chat_conversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                append_chat_conversation(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        final UiSettings mUiSettings = mMap.getUiSettings();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // This button moves the map to the current UAV position
        fab_uav.setOnClickListener(new View.OnClickListener() {
            // TODO: currently points to an arbitrary location until integrating with base app
            // TODO: Make it zoom into the location because right now it is way zoomed out
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    // For now we go to this random coordinate, later to move to the last known
                    // UAV location
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-30, 120)));
                }
            }
        });

        // This button moves the map to the current Controller position
        fab_ctrl.setOnClickListener(new View.OnClickListener() {
            // TODO: Make it zoom into the location because right now it is way zoomed out
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    mFusedLocationClient.getLastLocation()
                            .addOnSuccessListener(new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    // On rare occasions this can be null
                                    if (location != null) {
                                        // Move map to current controller location
                                        mMap.moveCamera(CameraUpdateFactory.newLatLng(
                                                new LatLng(location.getLatitude(), location.getLongitude())));
                                    }
                                }
                            });
                }
            }
        });
    }

    public void getLocationPermission() {
        // Checking for location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            // If user has previously denied access, provides a brief explanation
            // as to why the location services are needed for this application.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                showWarningDialog();
                // No explanation is needed, so request permission
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_LOCATION_REQUEST_CODE);
            }
        }
    }

    //@Override
    public void onRequestionPermissionResult(int requestCode,
                                             String[] permissions, int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case MY_LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                } else {
                    // Disable any features that utilize the user's locations and notify user of
                    // app's limited functionality without the location permissions
                    Toast toastNoLoc = Toast.makeText(getApplicationContext(),
                            R.string.toast_no_loc, Toast.LENGTH_LONG);
                    toastNoLoc.show();
                }
                return;
            }
        }
    }

    public void showWarningDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new WarningDialog();
        dialog.show(getSupportFragmentManager(), "WarningDialog");
    }

    // Dialog fragment receives reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the WarningDialog.WarningDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched positive button
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_LOCATION_REQUEST_CODE);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched negative button
        // Continue with limited operation
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int id) {
        //Log.d("Main Method", "id: " + id + "\t\tX Percent: " + xPercent + "\tY Percent: " + yPercent);
        switch(id) {
            case R.id.leftStick:
                commandZ = yPercent;
                break;
            case R.id.rightStick:
                commandX = xPercent;
                commandY = yPercent;
                break;
        }
    }

    private String GPS_Coordinates,UAV_Pilot_name;

    private void append_chat_conversation(DataSnapshot dataSnapshot) {

//        Iterator i = dataSnapshot.getChildren().iterator();
//
//        while (i.hasNext()){
//
//            GPS_Coordinates = (String) ((DataSnapshot)i.next()).getValue();
//            UAV_Pilot_name = (String) ((DataSnapshot)i.next()).getValue();
//        }
//        chat_conversation.setText("");
//        chat_conversation.append(UAV_Pilot_name +" : "+GPS_Coordinates +" \n");
//        if(UAV_Pilot_name.equals("REMOTE")){
//            gps_array = GPS_Coordinates.split(" ", 4);
//            time = Long.parseLong(gps_array[0]);
//            latitude = Double.parseDouble(gps_array[1]);
//            longitude = Double.parseDouble(gps_array[2]);
//            altitude = Double.parseDouble(gps_array[3]);
//            Log.d("D:", "Lat: "+latitude+"Long: "+longitude+"Alt: "+altitude+"Time: "+time);
//        }
    }
}
