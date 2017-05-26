package com.am.demo.editdistance;

import java.util.ArrayList;

/**
 * Created by malbor806 on 16.05.2017.
 */

public class EditDistance {
    private int firstWordLength;
    private int secondWordLength;
    private int[][] tab;
    private ArrayList<String> suggestions;
    private ArrayList<Integer> distances;

    public EditDistance() {
        firstWordLength = 0;
        secondWordLength = 0;
    }

    public int getEditDistance(String firstWord, String secondWord) {

        createEditDistanceArray(firstWord, secondWord);
        return tab[firstWordLength][secondWordLength];
    }

    private void createEditDistanceArray(String firstWord, String secondWord) {
        firstWordLength = firstWord.length();
        secondWordLength = secondWord.length();
        tab = new int[firstWordLength + 1][secondWordLength + 1];
        for (int i = 0; i <= firstWordLength; i++) {
            tab[i][0] = i;
        }
        for (int j = 0; j <= secondWordLength; j++) {
            tab[0][j] = j;
        }
        for (int i = 1; i <= firstWordLength; i++) {
            for (int j = 1; j <= secondWordLength; j++) {
                if (firstWord.charAt(i - 1) == secondWord.charAt(j - 1)) {
                    tab[i][j] = tab[i - 1][j - 1];
                } else {
                    tab[i][j] = Math.min(Math.min(tab[i][j - 1], tab[i - 1][j - 1]), tab[i - 1][j]) + 1;
                }
            }
        }
    }

    public ArrayList<String> showSuggestion(String firstWord, ArrayList<String> stops) {
        suggestions = new ArrayList<>();
        distances = new ArrayList<>();
        ArrayList<String> wordsContainsFirstWord = findWordsContainsFirstWord(firstWord, stops);
        if (wordsContainsFirstWord.size() > 0) {
            countDistances(firstWord, wordsContainsFirstWord);
            int index, k = 0;
            while (k < wordsContainsFirstWord.size() && suggestions.size() < 3) {
                index = findWordWithMinimumDistance();
                addWordToSuggestions(wordsContainsFirstWord, index);
                k++;
            }
        }
        return suggestions;
    }

    private void addWordToSuggestions(ArrayList<String> wordsContainsFirstWord, int index) {
        if (!suggestions.contains(wordsContainsFirstWord.get(index))) {
            suggestions.add(wordsContainsFirstWord.get(index));
        }
        distances.set(index, 9999);
    }

    private int findWordWithMinimumDistance() {
        int index = 0, min = 999;
        for (int j = 0; j < distances.size(); j++) {
            if (distances.get(j) < min) {
                min = distances.get(j);
                index = j;
            }
        }
        return index;
    }

    private void countDistances(String firstWord, ArrayList<String> wordsContainsFirstWord) {
        int distance, i = 0;
        while (i < wordsContainsFirstWord.size()) {
            distance = getEditDistance(firstWord, wordsContainsFirstWord.get(i));
            distances.add(distance);
            i++;
        }
    }

    private ArrayList<String> findWordsContainsFirstWord(String firstWord, ArrayList<String> stops) {
        ArrayList<String> wordsContainsFirstWord = new ArrayList<>();
        for (String stop : stops) {
            if (stop.toLowerCase().contains(firstWord)) {
                wordsContainsFirstWord.add(stop);
            }
        }
        return wordsContainsFirstWord;
    }
}
