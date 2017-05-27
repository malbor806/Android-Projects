package com.am.demo.mapapp;

import com.am.demo.mapapp.model.City;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by malbor806 on 27.05.2017.
 */

public class JSONConventer {
    private JSONObject jsonObject;
    private JSONArray cities;
    private City city;
    private List<City> citiesList;
    private String cityName;
    private double latitude;
    private double longitude;


    public JSONConventer(InputStream inputStream) {
        String jsonAsString = readJSONfromFile(inputStream);
        try {
            readGsonFromString(jsonAsString);
            convertStringToJson(jsonAsString);
        } catch (ParseException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void readGsonFromString(String json) {
        // Cities cities = new Gson().fromJson(json, Cities.class);
        try {
            convertStringToJson(json);
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
    }

    private String readJSONfromFile(InputStream inputStream) {
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {

            Reader reader = new BufferedReader(new InputStreamReader(inputStream));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    private void convertStringToJson(String jsonAsString) throws JSONException, ParseException {
        JSONParser parser = new JSONParser();
        jsonObject = new JSONObject(jsonAsString); // (JSONObject) parser.parse(jsonAsString);
        citiesList = new ArrayList<>();
        convertJSONtoCityList();
    }


    private void convertJSONtoCityList() {
        try {
            cities = jsonObject.getJSONArray("cities");
            if (cities != null) {
                getCitiesListFromJSON();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getCitiesListFromJSON() throws JSONException {
        for (int i = 0; i < cities.length(); i++) {
            getCityFromJSON(i);
            city = new City(cityName, latitude, longitude);
            citiesList.add(city);
        }
    }

    private void getCityFromJSON(int i) throws JSONException {
        cityName = cities.getJSONObject(i).getString("name");
        latitude = cities.getJSONObject(i).getDouble("xcord");
        longitude = cities.getJSONObject(i).getDouble("ycord");
    }

    public List<City> getCitiesList() {
        return citiesList;
    }

}
