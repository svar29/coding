package com.uber.dictionary;

import java.util.*;

public class MeaningfulSentenceFromADictionary {

    private char[] data;
    private boolean[][] isWindowValid;
    private Set<String> dictionary;
    private boolean[] suffixIsValidSentence;
    private List<String> result;
    private int[] next;

    public List<String> breakWordIntoMeaningfulSentence(String sentence, Set<String> dictionary) {
        init(sentence, dictionary);
        solve();
        return result;
    }

    private void solve() {
        int n = data.length;
        for (int left = 0;left < n; left++) {
            String currentString = "";
            for (int length = 0; left + length < n; length++) {
                int right = left + length;
                currentString += data[right];
                if (dictionary.contains(currentString)) {
                    isWindowValid[left][right] = true;
                    if (right == n-1) {
                        suffixIsValidSentence[left] = true;
                        next[left] = right + 1;
                    }

                }

            }
        }

        for (int right = n-2; right >= 0; right--) {
            for (int left = 0; left <= right; left++) {
                if (isWindowValid[left][right] && suffixIsValidSentence[right + 1]) {
                    suffixIsValidSentence[left] = true;
                    next[left] = right + 1;
                }
            }
        }

        int pos = 0;
        while(pos != n && suffixIsValidSentence[0]) {
            String word = "";
            for (int i = pos; i < next[pos]; i++)
                word += data[i];
            result.add(word);
            pos = next[pos];
        }
    }

    private void init(String sentence, Set<String> dictionary) {
        data = sentence.toCharArray();
        isWindowValid = new boolean[data.length][data.length];
        suffixIsValidSentence = new boolean[data.length];
        next = new int[data.length];
        Arrays.fill(next, -1);
        this.dictionary = dictionary;
        result = new ArrayList<String>();
    }
}
