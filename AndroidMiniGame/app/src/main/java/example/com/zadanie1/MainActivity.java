package example.com.zadanie1;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int attempts;
    private int number;
    private TextView attemptsTextView;
    private TextView trialsTextView;
    private TextView infoTextView;
    private EditText numberEditText;
    private Button randomButton;
    private Button checkButton;
    private Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setListeners();
        rand = new Random();
        randomNumber();
        attempts = 0;
    }

    private void findViews() {
        attemptsTextView = (TextView) findViewById(R.id.tv_attempts);
        trialsTextView = (TextView) findViewById(R.id.tv_attempts);
        numberEditText = (EditText) findViewById(R.id.et_userNumberType);
        infoTextView = (TextView) findViewById(R.id.tv_isGreaterOrLower);
        randomButton = (Button) findViewById(R.id.b_randomNumber);
        checkButton = (Button) findViewById(R.id.b_check);
    }

    private void setListeners() {
        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preapareNumber();
            }
        });
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNumber();
            }
        });
        numberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkButton.setEnabled(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void preapareNumber() {
        attempts = 0;
        randomNumber();
        setTextViewPropertiesBeforeStartingGame();
    }

    public void randomNumber() {
        attempts = 0;
        number = rand.nextInt(10000);
    }

    private void setTextViewPropertiesBeforeStartingGame() {
        numberEditText.setEnabled(true);
        setInfoTextViewSizeAndColor(20, Color.RED);
        trialsTextView.setText(getString(R.string.attempts, 0));
        infoTextView.setText(null);
    }

    public void checkNumber() {
        int userNumberType = Integer.parseInt(numberEditText.getText().toString());
        if (userNumberType < number) {
            showComparisonInfo(getString(R.string.too_small));
            addAttempts();
        } else if (userNumberType > number) {
            showComparisonInfo(getString(R.string.too_big));
            addAttempts();
        } else {
            setInfoTextViewSizeAndColor(30, Color.BLUE);
            showComparisonInfo(getString(R.string.correct_number));
            numberEditText.setEnabled(false);
        }
    }

    private void showComparisonInfo(String text) {
        infoTextView.setText(text);
        numberEditText.setText(null);
    }

    private void addAttempts() {
        attempts++;
        attemptsTextView.setText(getString(R.string.attempts, attempts));
    }

    private void setInfoTextViewSizeAndColor(float size, int color) {
        infoTextView.setTextSize(size);
        infoTextView.setTextColor(color);
    }
}
