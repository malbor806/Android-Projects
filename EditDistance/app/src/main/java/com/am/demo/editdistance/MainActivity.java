package com.am.demo.editdistance;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText firstWordEditText;
    private EditText secondWordEditText;
    private EditText findWordEditText;
    private TextView showDistanceTextView;
    private Button countDistanceButton;
    private EditDistance editDistance;
    private String firstWord;
    private String secondWord;
    private ArrayList<String> stops;
    private ArrayList<String> suggestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        editDistance = new EditDistance();
        readStopsFromFile();
        setListeners();
    }

    private void readStopsFromFile() {
        InputStream file;
        stops = new ArrayList<>();
        try {
            file = getAssets().open("stops.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                stops.add(line);
            }
            br.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    private void findViews() {
        firstWordEditText = (EditText) findViewById(R.id.et_firstWord);
        secondWordEditText = (EditText) findViewById(R.id.et_secondWord);
        countDistanceButton = (Button) findViewById(R.id.b_countDistance);
        showDistanceTextView = (TextView) findViewById(R.id.tv_showDistance);
        findWordEditText = (EditText) findViewById(R.id.et_findWord);
    }

    private void setListeners() {
        countDistanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDistance();
            }
        });
        findWordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                suggestions = editDistance.showSuggestion(s.toString(), stops);
                showDistanceTextView.setText(suggestions.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    showDistanceTextView.setText(null);
                }
            }
        });
    }

    private void showEditDistance() {
        getWordsFromEditText();
        int distance = editDistance.getEditDistance(firstWord, secondWord);
        showDistanceTextView.setText(String.format(getString(R.string.distance_is), String.valueOf(distance)));
    }

    private void getWordsFromEditText() {
        firstWord = firstWordEditText.getText().toString();
        secondWord = secondWordEditText.getText().toString();
    }
}
