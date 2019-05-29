package com.example.gsensor;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

public class GPSlocalization implements LocationListener {

    private LocationManager locationManager;
    private String latitude;
    private String longitude;

    Context co;

    public GPSlocalization(Context context) {
        co=context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context,"Brak zgody na GPS!!!!",Toast.LENGTH_SHORT).show();
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 250, 0, (LocationListener) this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 250, 0, (LocationListener) this);


    }



    public String podajszerokosc() {
        return latitude;
    }

    public String podajdlugosc() {
        return longitude;
    }


    @Override
    public void onLocationChanged(Location location) {
        double lon = (double) (location.getLongitude());
        double lat = (double) (location.getLatitude());
        latitude = lat + "";
        longitude = lon + "";
/*
        if(location.getProvider().equals("gps")){
            Toast.makeText(co,"jest sygnal z "+location.getProvider(),Toast.LENGTH_LONG).show();

        }
*/
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}




