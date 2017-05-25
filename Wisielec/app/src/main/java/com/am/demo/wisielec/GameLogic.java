package com.am.demo.wisielec;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

/**
 * Created by malbor806 on 26.03.2017.
 */

public class GameLogic implements Parcelable {
    private String wordToGuess;
    private String wordToShow;
    private String[] words;
    private Random random;
    private int counter;

    public GameLogic(String[] words) {
        this.words = words;
        wordToGuess = "";
        wordToShow = "";
        random = new Random();
        generateRandomWord();
        counter = 0;
    }

    protected GameLogic(Parcel in) {
        wordToGuess = in.readString();
        wordToShow = in.readString();
        words = in.createStringArray();
        counter = in.readInt();
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

    private void generateRandomWord() {
        int index = random.nextInt(words.length);
        wordToGuess = words[index];
        wordToShow = buildWordToShow(wordToGuess);
    }

    private String buildWordToShow(String wordToGuess) {
        StringBuilder sb = new StringBuilder(wordToGuess.length());
        for (int i = 0; i < wordToGuess.length(); i++) {
            sb.append('?');
        }
        return sb.toString();
    }

    public boolean checkIsWordContainsLetter(CharSequence text) {
        if (wordToGuess.contains(text)) {
            int indexOfChar = 0;
            char charFromWordToShow = text.charAt(0);
            while (indexOfChar < wordToGuess.length()) {
                if (wordToGuess.charAt(indexOfChar) == charFromWordToShow) {
                    wordToShow.replace('?', charFromWordToShow);
                    wordToShow = wordToShow.substring(0, indexOfChar) + charFromWordToShow + wordToShow.substring(indexOfChar + 1);
                }
                indexOfChar++;
            }
            return true;
        }
        return false;
    }

    public String getWordToGuess() {
        return wordToGuess;
    }

    public String getWordToShow() {
        return wordToShow;
    }

    public int getAttemptsCounter() {
        return counter;
    }

    public void increaseAttemptsCount() {
        counter++;
    }

    public boolean isGameEnd() {
        return !wordToShow.contains("?");
    }

    public void startNewGame() {
        counter = 0;
        generateRandomWord();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(wordToGuess);
        dest.writeString(wordToShow);
        dest.writeInt(counter);
    }
}
