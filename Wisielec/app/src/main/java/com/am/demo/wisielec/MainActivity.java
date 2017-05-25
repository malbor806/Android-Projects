package com.am.demo.wisielec;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private Button playAgainButton;
    private ImageView stepsOfFailuresImageView;
    private TextView wordToGuessTextView;
    private TextView failsCounterTextView;
    private TableLayout tableLayout;
    private GameLogic gameLogic;
    private ArrayList<Letter> letters;
    private Alphabet alphabet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        alphabet = new Alphabet();
        String[] words = getResources().getStringArray(R.array.words);
        if (savedInstanceState == null) {
            gameLogic = new GameLogic(words);
            failsCounterTextView.setText(String.format(getString(R.string.fails_counter), "10"));
            createLetters();
            wordToGuessTextView.setText(gameLogic.getWordToShow());
        } else {
            setPreviousChanges(savedInstanceState);
        }
        addAlphabetButtons();
        checkIsEndOfGame();
        setListeners();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putCharSequence("failsCounter", failsCounterTextView.getText());
        savedInstanceState.putString("imageName", "f" + gameLogic.getAttemptsCounter());
        savedInstanceState.putParcelable("gameLogic", gameLogic);
        savedInstanceState.putParcelableArrayList("letters", letters);
        savedInstanceState.putCharSequence("wordToGuess", wordToGuessTextView.getText());
    }

    private void findViews() {
        playAgainButton = (Button) findViewById(R.id.b_randomNewWord);
        stepsOfFailuresImageView = (ImageView) findViewById(R.id.iv_failPictures);
        wordToGuessTextView = (TextView) findViewById(R.id.tv_wordToGuess);
        tableLayout = (TableLayout) findViewById(R.id.ll_alfabetLinearLayout);
        failsCounterTextView = (TextView) findViewById(R.id.tv_failsCounter);
    }

    private void setPreviousChanges(Bundle savedInstanceState) {
        failsCounterTextView.setText(savedInstanceState.getCharSequence("failsCounter"));
        gameLogic = savedInstanceState.getParcelable("gameLogic");
        String image = savedInstanceState.getString("imageName");
        stepsOfFailuresImageView.setImageResource(getResources().getIdentifier(image, "drawable", this.getPackageName()));
        letters = savedInstanceState.getParcelableArrayList("letters");
        wordToGuessTextView.setText(savedInstanceState.getCharSequence("wordToGuess"));
    }

    private void createLetters() {
        letters = new ArrayList<>();
        String[] getAlphabet = alphabet.getAlphabet();
        for (String anAlphabet : getAlphabet) letters.add(new Letter(anAlphabet, 0));
    }

    private void addAlphabetButtons() {
        TableRow tableRow;
        Button button;
        int c = 0;
        for (int j = 0; j < 4; j++) {
            createTableRow();
            tableRow = (TableRow) tableLayout.getChildAt(j);
            for (int i = 0; i < 8; i++) {
                button = createNewButton(c);
                c++;
                tableRow.addView(button);
            }
        }
    }

    private void createTableRow() {
        TableRow tableRow = new TableRow(this);
        TableRow.LayoutParams lp = createLayoutParams();
        tableRow.setLayoutParams(lp);
        tableLayout.addView(tableRow);
    }

    private TableRow.LayoutParams createLayoutParams() {
        return new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, (float) 1.0);
    }

    private Button createNewButton(int c) {
        Button button = new Button(this);
        button.setText(letters.get(c).getName());
        button.setLayoutParams(createLayoutParams());
        if (letters.get(c).getState() == 1) {
            setButtonsState(button, ContextCompat.getColor(this, R.color.limegreen));
        }
        if (letters.get(c).getState() == (-1))
            setButtonsState(button, ContextCompat.getColor(this, R.color.red));
        return button;
    }

    private void setListeners() {
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewGame();
            }
        });
        for (int j = 0; j < 4; j++) {
            final TableRow tr = (TableRow) tableLayout.getChildAt(j);
            for (int i = 0; i < 8; i++) {
                tr.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int index = Arrays.asList(alphabet.getAlphabet()).indexOf(((Button) v).getText());
                        checkLetter(((Button) v).getText(), index);
                        int i = letters.get(index).getState();
                        if (i > 0) {
                            setButtonsState(v, ContextCompat.getColor(getBaseContext(), R.color.limegreen));
                        } else if (i < 0) {
                            setButtonsState(v, ContextCompat.getColor(getBaseContext(), R.color.red));
                        }
                    }
                });
            }
        }
    }

    private void startNewGame() {
        clearLetterState();
        setClickableAllButtons(true);
        setButtonsBackgroundColor();
        gameLogic.startNewGame();
        failsCounterTextView.setText(String.format(getString(R.string.fails_counter), "10"));
        wordToGuessTextView.setText(gameLogic.getWordToShow());
        stepsOfFailuresImageView.setImageResource(R.drawable.f0);
    }

    private void clearLetterState() {
        for (int i = 0; i < letters.size(); i++)
            letters.get(i).setState(0);
    }

    private void setClickableAllButtons(boolean state) {
        for (int j = 0; j < 4; j++) {
            TableRow tr = (TableRow) tableLayout.getChildAt(j);
            for (int i = 0; i < 8; i++) {
                tr.getChildAt(i).setEnabled(state);
            }
        }
    }

    private void setButtonsBackgroundColor() {
        for (int j = 0; j < 4; j++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(j);
            for (int i = 0; i < 8; i++)
                tableRow.getChildAt(i).setBackgroundResource(android.R.drawable.btn_default);
        }
    }

    private void checkLetter(CharSequence index, int i) {
        boolean isWordContainsLetter = gameLogic.checkIsWordContainsLetter(index);
        if (isWordContainsLetter) {
            wordToGuessTextView.setText(gameLogic.getWordToShow());
            letters.get(i).setState(1);
        } else {
            letters.get(i).setState(-1);
            gameLogic.increaseAttemptsCount();
            String pictureName = "f" + gameLogic.getAttemptsCounter();
            stepsOfFailuresImageView.setImageResource(getResources().getIdentifier(pictureName, "drawable", this.getPackageName()));
            failsCounterTextView.setText(String.format(getString(R.string.fails_counter), Integer.toString(10 - gameLogic.getAttemptsCounter())));
        }
        checkIsEndOfGame();
    }

    private void checkIsEndOfGame() {
        if (gameLogic.getAttemptsCounter() >= 10) {
            setEndGameState(R.string.you_lose);
        } else if (gameLogic.isGameEnd()) {
            setEndGameState(R.string.you_won);
        }
    }

    private void setEndGameState(int textId) {
        setClickableAllButtons(false);
        failsCounterTextView.setText(textId);
        wordToGuessTextView.setText(gameLogic.getWordToGuess());

    }
    private void setButtonsState(View v, int color) {
        v.setBackgroundColor(color);
        v.setEnabled(false);
    }

}
