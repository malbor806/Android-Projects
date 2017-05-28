package com.am.demo.mapapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;

import com.am.demo.mapapp.model.City;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    @BindView(R.id.actv_searchCity)
    AutoCompleteTextView searchCityAutoCompleteTextView;
    @BindView(R.id.ib_drawLines)
    ImageButton drawTourImageButton;
    private GoogleMap googleMap;
    private List<City> cities;
    private String[] citiesName;
    private boolean userWantToDrawPath;
    private PolylineOptions pathToDraw;
    private Polyline polyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userWantToDrawPath = false;
        ButterKnife.bind(this);
        setMapFragment();
        convertJsonFileToCitiesList();
        addSuggestionListToAutoCompleteTextView();
        setListeners();
    }

    private void convertJsonFileToCitiesList() {
        JSONConventer jsonConventer = new JSONConventer(getResources().openRawResource(R.raw.miasta));
        cities = jsonConventer.getCitiesList();
    }

    private void setMapFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void addSuggestionListToAutoCompleteTextView() {
        if (cities != null) {
            createCitiesNameArray();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, citiesName);
            searchCityAutoCompleteTextView.setAdapter(adapter);
            searchCityAutoCompleteTextView.setThreshold(1);
        }
    }

    private void createCitiesNameArray() {
        citiesName = new String[cities.size()];
        for (int i = 0; i < cities.size(); i++) {
            citiesName[i] = cities.get(i).getName();
        }
    }

    private void setListeners() {
        searchCityAutoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String name = (String) parent.getItemAtPosition(position);
            int index = Arrays.asList(citiesName).indexOf(name);
            City city = cities.get(index);
            MainActivity.this.setLocation(city.getLatitude(), city.getLongitude());
        });
        drawTourImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userWantToDrawPath) {
                    userWantToDrawPath = false;
                    if (polyline != null) {
                        polyline.remove();
                    }
                } else {
                    pathToDraw = new PolylineOptions();
                    userWantToDrawPath = true;
                }

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setOnMapClickListener(this);
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
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 11.0f));
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (userWantToDrawPath) {
            pathToDraw.add(latLng);
            setLocation(latLng.latitude, latLng.longitude);
            if (polyline != null) {
                polyline.remove();
            }
            polyline = googleMap.addPolyline(pathToDraw);
        }
    }
}
