package com.am.demo.editdistance;

import java.util.Random;

/**
 * Created by malbor806 on 19.05.2017.
 */

public class RandomString {
    private static Random random;
    private static char[] symbols;

    public RandomString() {
        random = new Random();
        createAlphabet();
    }

    private void createAlphabet() {
        StringBuilder tmp = new StringBuilder();
        for (char ch = 'A'; ch <= 'Z'; ch++)
            tmp.append(ch);
        symbols = tmp.toString().toCharArray();
    }

    public String createRandomWord(int wordLength) {
        char[] buf = new char[wordLength];
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }
}
