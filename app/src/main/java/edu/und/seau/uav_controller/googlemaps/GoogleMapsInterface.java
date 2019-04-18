package edu.und.seau.uav_controller.googlemaps;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;


public interface GoogleMapsInterface {
    MapFragment getMapFragment(Fragment fragmentManager, OnMapReadyCallback callback);

    void getLocationPermission(Context context, Activity activity);


    FusedLocationProviderClient getFusedLocationProvider(Activity activity);

    void moveToMyLocation(GoogleMap map, FusedLocationProviderClient fusedLocationProviderClient);
    void moveToLocation(GoogleMap map, Location location);
    void setZoom(GoogleMap map, float zoom);
}
