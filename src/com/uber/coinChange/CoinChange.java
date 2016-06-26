package com.uber.coinChange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CoinChange {

    private final int MAX_VALUE  = 10000;
    private final char[] characters;
    private final int[] values;
    private long dp[][] = new long[MAX_VALUE][26];
    private boolean vis[][] = new boolean[MAX_VALUE][26];

    public CoinChange() {
        characters = new char[26];
        values = new int[26];
        for (char i='a';i<='z';i++) {
            characters[i - 'a'] = i;
            values[i - 'a'] = i - 'a' + 1;
        }
    }

    public long getNoOfWays(int val) {
        return solve(val, 0);
    }

    public List<String> getAllPossibleWays(int val) {
        List<String> result = new ArrayList<String>();
        reconstruct(val, 0, "", result);
        Collections.sort(result);
        return result;
    }

    private void reconstruct(int left, int pos, String previousValue, List<String> result) {
        if (left == 0) {
            if (!previousValue.equals(""))
                result.add(previousValue);
            return;
        }

        if (left < 0 || pos == characters.length) return;

        if (solve(left - values[pos], pos) > 0)
            reconstruct(left - values[pos], pos, previousValue + characters[pos], result);

        if (solve(left, pos+1) > 0)
            reconstruct(left, pos + 1, previousValue, result);
    }

    private long solve(int left, int pos) {

        if (left < 0) return 0;
        if(pos == characters.length) return left == 0 ? 1 : 0;

        if (vis[left][pos]) return dp[left][pos];
        vis[left][pos] = true;

        int d = 0;
        d += solve(left - values[pos], pos);
        d += solve(left, pos + 1);

        return dp[left][pos] = d;
    }
}
