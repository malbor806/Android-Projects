package com.am.demo.mapapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.am.demo.mapapp.model.City;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap googleMap;
    private List<City> cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        JSONConventer jsonConventer = new JSONConventer(getResources().openRawResource(R.raw.miasta));
        cities = jsonConventer.getCitiesList();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        findStartedLocation();
    }

    private void findStartedLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkPermission()) return;
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if (location != null) {
            setUserLocationAsStartedLocation(location);
        } else {
            setDefaultLocation();
        }
    }

    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            setDefaultLocation();
            return true;
        }
        return false;
    }


    private void setUserLocationAsStartedLocation(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        setUserLocation(latitude, longitude);
    }

    private void setDefaultLocation() {
        setLocation(51.110, 17.030);
    }

    private void setUserLocation(double latitude, double longitude) {
        setLocation(latitude, longitude);
    }

    private void setLocation(double latitude, double longitude) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 9.0f));
    }

}
