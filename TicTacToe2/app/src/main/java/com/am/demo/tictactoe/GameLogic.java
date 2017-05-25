package com.am.demo.tictactoe;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by malbor806 on 14.03.2017.
 */

class GameLogic implements Parcelable {
    private List<ArrayList<String>> boardContent;
    private Computer computer;
    private OnBoardChange onBoardChange;
    private GameStateListener gameStateListener;
    private boolean isEndOfGame;


    public GameLogic(String playerToken) {
        prepareLists();
        computer = new Computer(playerToken);
        isEndOfGame = false;
    }

    protected GameLogic(Parcel in) {

    }

    public static final Creator<GameLogic> CREATOR = new Creator<GameLogic>() {
        @Override
        public GameLogic createFromParcel(Parcel in) {
            return new GameLogic(in);
        }

        @Override
        public GameLogic[] newArray(int size) {
            return new GameLogic[size];
        }
    };

    private void prepareLists() {
        boardContent = new ArrayList<>();
        ArrayList<String> tmp;
        for (int i = 0; i < 4; i++) {
            tmp = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                tmp.add(null);
            }
            boardContent.add(tmp);
        }
    }

    public void startNewGame(String playerToken) {
        isEndOfGame = false;
        computer.setComputerToken(playerToken);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                boardContent.get(i).set(j, null);
            }
        }
    }

    public void addPlayerMove(String playerToken, int i, int j) {
        boardContent.get(i).set(j, playerToken);
        findWinningPattern();
    }

    public void computerMove() {
        boolean isComputerMakeMove = true;
        checkIfThereAreEmptyFields();
        do {
            computer.makeMove(boardContent);
            int compMoveA = computer.getFirstIndex();
            int compMoveB = computer.getSecondIndex();
            if (boardContent.get(compMoveA).get(compMoveB) == null) {
                boardContent.get(compMoveA).set(compMoveB, computer.getComputerToken());
                onBoardChange.onBoardChange(compMoveA, compMoveB);
                isComputerMakeMove = false;
            }
        } while (isComputerMakeMove);
        findWinningPattern();
    }

    private void findWinningPattern() {
        checkHorizontalPattern();
        checkDiagonalPattern();
        checkVerticalPattern();
        if (!isEndOfGame)
            checkIfThereAreEmptyFields();
    }

    private void checkHorizontalPattern() {
        ArrayList<String> tmp;
        for (int i = 0; i < 4; i++) {
            tmp = boardContent.get(i);
            if (!tmp.contains(null) && tmp.get(0).equals(tmp.get(1)) && tmp.get(1).equals(tmp.get(2)) && tmp.get(2).equals(tmp.get(3)))
                isEndOfGame = true;
            if (isEndOfGame) {
                gameStateListener.onEndOfGame(tmp.get(0));
                return;
            }
        }
    }

    private void checkVerticalPattern() {
        for (int j = 0; j < 4; j++) {
            if (boardContent.get(0).get(j) != null &&
                    boardContent.get(0).get(j).equals(boardContent.get(1).get(j)) &&
                    boardContent.get(1).get(j).equals(boardContent.get(2).get(j)) &&
                    boardContent.get(2).get(j).equals(boardContent.get(3).get(j))) {
                isEndOfGame = true;
                gameStateListener.onEndOfGame(boardContent.get(0).get(j));
            }
        }
    }

    private void checkDiagonalPattern() {
        List<ArrayList<String>> tmp = boardContent;
        if (tmp.get(0).get(0) != null &&
                tmp.get(0).get(0).equals(tmp.get(1).get(1)) &&
                tmp.get(1).get(1).equals(tmp.get(2).get(2)) &&
                tmp.get(2).get(2).equals(tmp.get(3).get(3))) {
            isEndOfGame = true;
            gameStateListener.onEndOfGame(tmp.get(0).get(0));
        } else if (tmp.get(0).get(3) != null &&
                tmp.get(0).get(3).equals(tmp.get(1).get(2)) &&
                tmp.get(1).get(2).equals(tmp.get(2).get(1)) &&
                tmp.get(2).get(1).equals(tmp.get(3).get(0))) {
            isEndOfGame = true;
            gameStateListener.onEndOfGame(tmp.get(0).get(3));
        }
    }

    private void checkIfThereAreEmptyFields() {
        for (int i = 0; i < 4; i++) {
            if (boardContent.get(i).contains(null))
                return;
        }
        gameStateListener.onEndOfGame(null);
    }

    public String getComputerToken() {
        return computer.getComputerToken();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public interface GameStateListener {
        void onEndOfGame(String playerToken);
    }

    public void setGameStateListener(GameStateListener gameStateListener) {
        this.gameStateListener = gameStateListener;
    }

    public interface OnBoardChange {
        void onBoardChange(int i, int j);
    }

    public void setOnBoardChangeListener(OnBoardChange onBoardChange) {
        this.onBoardChange = onBoardChange;
    }
}
