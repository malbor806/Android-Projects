package com.am.demo.tictactoe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {
    private TextView playerTokenTextView;
    private TextView whoseTurnTextView;
    private TextView whoWins;
    private TableLayout tableLayout;
    private Button playAgainButton;
    private List<List<Button>> buttons;
    private ArrayList<Move> moves;
    private GameLogic gameLogic;
    private String playerToken;
    private boolean isGameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        if (savedInstanceState == null) {
            playerToken = getString(R.string.o_token);
            createMoves();
            isGameOver = false;
            playerTokenTextView.setText(String.format(getString(R.string.you_play_as_token), playerToken));
            gameLogic = new GameLogic(playerToken);
            createButtons();
        } else {
            setPreviousState(savedInstanceState);
            createButtons();
            if (isGameOver)
                endOfGameState((String) savedInstanceState.getCharSequence("whoWins"));
        }
        setListeners();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("playerToken", playerToken);
        savedInstanceState.putBoolean("isGameOver", isGameOver);
        savedInstanceState.putCharSequence("youPlayAs", playerTokenTextView.getText());
        savedInstanceState.putParcelable("gameLogic", gameLogic);
        savedInstanceState.putParcelableArrayList("moves", moves);
        savedInstanceState.putCharSequence("whoWins", whoWins.getText());
    }

    private void findViews() {
        playerTokenTextView = (TextView) findViewById(R.id.tv_playerToken);
        whoseTurnTextView = (TextView) findViewById(R.id.tv_whoseTurn);
        whoWins = (TextView) findViewById(R.id.tv_whoWin);
        playAgainButton = (Button) findViewById(R.id.b_playAgain);
        tableLayout = (TableLayout) findViewById(R.id.tl_tableLayout);
    }

    private void createMoves() {
        moves = new ArrayList<>();
        createListOfMoves();
    }

    private void createListOfMoves() {
        for (int i = 0; i < 16; i++) {
            moves.add(new Move("", 1));
        }
    }

    private void createButtons() {
        buttons = new ArrayList<>();
        createListOfButtons();
    }

    private void createListOfButtons() {
        for (int i = 0; i < 4; i++) {
            ArrayList<Button> addButtons = new ArrayList<>();
            TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
            for (int j = 0; j < 4; j++) {
                View childView = tableRow.getChildAt(j);
                ((Button) childView).setText(moves.get(i * 4 + j).getToken());
                childView.setEnabled(moves.get(i * 4 + j).getIsEnabled() != 0);
                addButtons.add((Button) childView);
            }
            buttons.add(addButtons);
        }
    }

    private void setListeners() {
        playAgainButton.setOnClickListener(v -> startNewGame());
        List<Button> tmp;
        for (int i = 0; i < 4; i++) {
            tmp = buttons.get(i);
            for (int j = 0; j < 4; j++) {
                int finalI1 = i;
                int finalJ1 = j;
                tmp.get(j).setOnClickListener(v -> makeMove(v, finalI1, finalJ1));
            }
        }
        gameLogic.setGameStateListener(this::endOfGame);
        gameLogic.setOnBoardChangeListener(this::addComputerMoveOnButtons);
    }

    public void setPreviousState(Bundle savedInstanceState) {
        isGameOver = savedInstanceState.getBoolean("isGameOver");
        playerToken = savedInstanceState.getString("playerToken");
        moves = savedInstanceState.getParcelableArrayList("moves");
        playerTokenTextView.setText(savedInstanceState.getCharSequence("youPlayAs"));
        gameLogic = savedInstanceState.getParcelable("gameLogic");
    }

    private void startNewGame() {
        changePlayerToken();
        gameLogic.startNewGame(playerToken);
        clearButtons();
        clearMoves();
        printPlayerToken();
        setClickableAllButtons(true);
        setButtonsVisibility(true);
        isGameOver = false;
        if (!isPlayerTurn()) {
            gameLogic.computerMove();
        }
    }

    private void changePlayerToken() {
        if (playerToken.equals(getString(R.string.x_token)))
            playerToken = getString(R.string.o_token);
        else
            playerToken = getString(R.string.x_token);
    }

    private boolean isPlayerTurn() {
        return playerToken.equals(getString(R.string.o_token));
    }

    private void clearButtons() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons.get(i).get(j).setText(null);
            }
        }
    }

    private void clearMoves() {
        for (int i = 0; i < moves.size(); i++) {
            moves.get(i).setToken(null);
            moves.get(i).setIsEnabled(1);
        }
    }

    private void setClickableAllButtons(boolean state) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons.get(i).get(j).setEnabled(state);
            }
        }
    }

    private void setButtonsVisibility(boolean isVisible) {
        if (isVisible) {
            whoWins.setVisibility(GONE);
            whoseTurnTextView.setVisibility(View.VISIBLE);
            playerTokenTextView.setVisibility(View.VISIBLE);
        } else {
            whoWins.setVisibility(View.VISIBLE);
            whoseTurnTextView.setVisibility(GONE);
            playerTokenTextView.setVisibility(GONE);
        }
    }

    private void makeMove(View v, int i, int j) {
        gameLogic.addPlayerMove(playerToken, i, j);
        moves.get(i * 4 + j).setToken(playerToken);
        moves.get(i * 4 + j).setIsEnabled(0);
        ((Button) v).setText(playerToken);
        v.setEnabled(false);
        if (!isGameOver)
            gameLogic.computerMove();
    }

    private void printPlayerToken() {
        playerTokenTextView.setText(String.format(getString(R.string.you_play_as_token), playerToken));
    }

    private void addComputerMoveOnButtons(int compMoveA, int compMoveB) {
        Button button = buttons.get(compMoveA).get(compMoveB);
        System.out.println(gameLogic.getComputerToken());
        moves.get(compMoveA * 4 + compMoveB).setToken(gameLogic.getComputerToken());
        moves.get(compMoveA * 4 + compMoveB).setIsEnabled(0);
        button.setText(gameLogic.getComputerToken());
        button.setEnabled(false);
    }

    private void endOfGame(String playerToken) {
        if (playerToken == null)
            endOfGameState((getString(R.string.game_finished_a_draw)));
        else
            endOfGameState(String.format(getString(R.string.game_finished_win_token), playerToken));
    }

    private void endOfGameState(String textToPrint) {
        whoWins.setText(textToPrint);
        setButtonsVisibility(false);
        setClickableAllButtons(false);
        isGameOver = true;
    }
}
