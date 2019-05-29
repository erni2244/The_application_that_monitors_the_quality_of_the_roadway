package com.example.gsensor.Zbieranieinformacji;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineListener;
import com.mapbox.android.core.location.LocationEnginePriority;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.util.List;

public class GPSlistener implements LocationEngineListener, PermissionsListener {

    private int PERMISION_GPS = 1;
    private MapboxMap map;
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private Location originLocation;

    private String latitude;
    private String longitude;
Context co;
Activity ac;

public GPSlistener(Context context, Activity activity){
    co=context;
    ac=activity;
    enableLocation();
}





    private void enableLocation() {
        if (PermissionsManager.areLocationPermissionsGranted(co)) {
            initializeLocationEngine();
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(ac);
        }
    }


    private void initializeLocationEngine(){
        locationEngine = new LocationEngineProvider(co).obtainBestLocationEngineAvailable();
        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
        locationEngine.activate();

        if (ActivityCompat.checkSelfPermission(co, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(co, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ac, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISION_GPS);
        }
        Location lastLocation = locationEngine.getLastLocation();
        if (lastLocation != null) {
            originLocation = lastLocation;
        } else {
            locationEngine.addLocationEngineListener(this);
        }
    }

/*
    public String podajszer() { return "" + originLocation.getLatitude();}

    public String podajdlug() {
        return "" + originLocation.getLongitude();
    }

*/

public String podajszer() {
    return latitude;
}

    public String podajdlug() {
        return longitude;
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location!=null){
            originLocation=location;
            double lon = (double) (location.getLongitude());
            double lat = (double) (location.getLatitude());
            latitude = lat + "";
            longitude = lon + "";
        }

    }








    @Override
    public void onConnected() {
        if (ActivityCompat.checkSelfPermission(co, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(co, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ac, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISION_GPS);
        }
        locationEngine.requestLocationUpdates();
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {
        if(granted){
            enableLocation();
        }
    }
}
