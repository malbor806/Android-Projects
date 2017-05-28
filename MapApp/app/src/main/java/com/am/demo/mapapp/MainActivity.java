package com.am.demo.mapapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.am.demo.mapapp.model.City;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.actv_searchCity)
    AutoCompleteTextView searchCityAutoCompleteTextView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private static final double DEFAULT_LATITUDE = 51.110;
    private static final double DEFAULT_LONGITUDE = 17.030;
    private static final float ZOOM = 10.0f;
    private GoogleMap googleMap;
    private List<City> cities;
    private String[] citiesName;
    private boolean userWantToDrawPath;
    private PolylineOptions pathToDraw;
    private Polyline polyline;
    private MenuItem menuItem;
    private double distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userWantToDrawPath = false;
        ButterKnife.bind(this);
        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                addOrRemovePath(item);
                return true;
            }
        });
        setMapFragment();
        convertJsonFileToCitiesList();
        addSuggestionListToAutoCompleteTextView();
        setListeners();
    }

    private void addOrRemovePath(MenuItem item) {
        if (item.getItemId() == R.id.drawLines) {
            addNewPath(item);
        } else {
            removePath(item);
        }
    }

    private void addNewPath(MenuItem item) {
        setOtherMenuItemEnable(item, R.id.removeLines);
        pathToDraw = new PolylineOptions();
        distance = 0;
    }

    private void removePath(MenuItem item) {
        setOtherMenuItemEnable(item, R.id.drawLines);
        removePathIfExist();
    }

    private void setOtherMenuItemEnable(MenuItem item, int drawLines) {
        item.setEnabled(false);
        menuItem = toolbar.getMenu().findItem(drawLines);
        menuItem.setEnabled(true);
    }

    private void setMapFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(googleMap1 -> {
            MainActivity.this.googleMap = googleMap1;
            setOnMapClickListener();
            findStartedLocation();
        });
    }

    private void setOnMapClickListener() {
        googleMap.setOnMapClickListener(latLng -> {
            if (toolbar.getMenu().findItem(R.id.removeLines).isEnabled()) {
                drawPath(latLng);
            }
        });
    }

    private void drawPath(LatLng latLng) {
        pathToDraw.add(latLng);
        removePathIfExist();
        polyline = googleMap.addPolyline(pathToDraw);
    }

    private void setLocation(double latitude, double longitude) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), ZOOM));
    }

    private void removePathIfExist() {
        if (polyline != null) {
            polyline.remove();
        }
    }

    private void convertJsonFileToCitiesList() {
        InputStream inputStream = getResources().openRawResource(R.raw.miasta);
        cities = new Gson().fromJson(StringReaderUtil.readStream(inputStream),
                new TypeToken<List<City>>() {
                }.getType());

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
    }

    private void findStartedLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkPermission()) {
            setDefaultLocation();
            return;
        }
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
        return (checkAccessPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                && checkAccessPermission(Manifest.permission.ACCESS_COARSE_LOCATION));
    }

    private boolean checkAccessPermission(String accessFineLocation) {
        return ActivityCompat.checkSelfPermission(this, accessFineLocation) != PackageManager.PERMISSION_GRANTED;
    }

    private void setUserLocationAsStartedLocation(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        setUserLocation(latitude, longitude);
    }

    private void setDefaultLocation() {
        setLocation(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);
    }

    private void setUserLocation(double latitude, double longitude) {
        setLocation(latitude, longitude);
    }

}
