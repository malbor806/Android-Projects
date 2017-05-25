package com.am.demo.tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by malbor806 on 15.03.2017.
 */

public class Computer {
    private Random random;
    private int firstIndex;
    private int secondIndex;
    private String computerToken;
    private String playerToken;

    public Computer(String playerToken) {
        random = new Random();
        this.playerToken = playerToken;
        setComputerToken(playerToken);
    }

    public void makeMove(List<ArrayList<String>> boardContent) {
        if (checkPlayerMove(boardContent)) {
            return;
        }
        firstIndex = random.nextInt(4);
        secondIndex = random.nextInt(4);
    }

    private boolean checkPlayerMove(List<ArrayList<String>> boardContent) {
        boolean checkIfContainsComputerToken;
        int frequency;
        for (int i = 0; i < 4; i++) {
            frequency = 0;
            checkIfContainsComputerToken = true;
            for (int j = 0; j < 4; j++) {
                if (boardContent.get(i).get(j) == null) {
                    firstIndex = i;
                    secondIndex = j;
                    checkIfContainsComputerToken = false;
                } else if (boardContent.get(i).get(j).equals(playerToken)) {
                    frequency++;
                }
                if (frequency == 3) {
                    if (!checkIfContainsComputerToken) {
                        return true;
                    }
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            frequency = 0;
            checkIfContainsComputerToken = true;
            for (int j = 0; j < 4; j++) {
                if (boardContent.get(j).get(i) == null) {
                    firstIndex = j;
                    secondIndex = i;
                    checkIfContainsComputerToken = false;
                } else if (boardContent.get(j).get(i).equals(playerToken)) {
                    frequency++;
                }
                if (frequency == 3) {
                    if (!checkIfContainsComputerToken)
                        return true;
                }
            }
        }
        return false;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    public int getSecondIndex() {
        return secondIndex;
    }

    public void setComputerToken(String playerToken) {
        if (playerToken.equals("X"))
            computerToken = "O";
        else
            computerToken = "X";
    }

    public String getComputerToken() {
        return computerToken;
    }
}
