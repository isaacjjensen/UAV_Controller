package edu.und.seau.uav_controller.googlemaps;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import javax.inject.Inject;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class GoogleMapsManager implements GoogleMapsInterface {

    @Inject
    public GoogleMapsManager() {

    }

    @Override
    public MapFragment getMapFragment(Fragment fragment,
                                      OnMapReadyCallback callback) {
        MapFragment mapFragment = (MapFragment) fragment;
        mapFragment.getMapAsync(callback);
        return mapFragment;
    }

    @Override
    public void getLocationPermission(Context context, Activity activity) {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String [] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    @Override
    public FusedLocationProviderClient getFusedLocationProvider(Activity activity) {
        return LocationServices.getFusedLocationProviderClient(activity);
    }

    @Override
    public void moveToLocation(GoogleMap map, Location location) {
        map.moveCamera(CameraUpdateFactory.newLatLng(
                new LatLng(location.getLatitude(), location.getLongitude())));
    }

    @Override
    public void moveToMyLocation(GoogleMap map, FusedLocationProviderClient fusedLocationProvider) {
        try {
            fusedLocationProvider.getLastLocation().addOnSuccessListener(
                    new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        map.moveCamera(CameraUpdateFactory.newLatLng(
                                new LatLng(location.getLatitude(), location.getLongitude())));
                    }
                }
            });
        } catch (SecurityException e) {
            Log.d("GoogleMapsManager", "Do not have permissions for retrieving location");
            return;
        }
    }

    public void setZoom(GoogleMap map, float zoom) {
        map.moveCamera(CameraUpdateFactory.zoomTo(zoom));
    }
}
