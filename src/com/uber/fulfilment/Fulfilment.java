package com.uber.fulfilment;

import java.util.ArrayList;
import java.util.List;

public class Fulfilment {

    private static final int MAX_INVENTORY = 1024;
    private int[][] dp;
    private boolean[][] vis;
    private int[] request;
    private List<Integer> result;
    private int N;

    public List<Integer> getPromisesToFulfill(int requestCount[], int inventoryCount) {
        init(requestCount);
        reconstruct(0, inventoryCount);
        return result;
    }

    private void reconstruct(int pos, int inventoryCount) {
        if (pos == N)
            return;

        int dontFulfill = solve(pos + 1, inventoryCount);
        int fulfill = request[pos] + solve(pos + 1, inventoryCount - request[pos]);
        if (fulfill >= dontFulfill) {
            result.add(pos);
            reconstruct(pos + 1, inventoryCount - request[pos]);
        }
        else {
            reconstruct(pos + 1, inventoryCount);
        }
    }

    private int solve(int pos, int inventoryCount) {

        if (inventoryCount < 0) return Integer.MIN_VALUE;

        if (pos == N)
            return 0;

        if (vis[pos][inventoryCount]) return dp[pos][inventoryCount];
        vis[pos][inventoryCount] = true;

        int d = 0;
        if (inventoryCount - request[pos] >= 0) {
            d = Math.max(d, request[pos] + solve(pos + 1, inventoryCount - request[pos]));
        }
        d = Math.max(d, solve(pos + 1, inventoryCount));

        return dp[pos][inventoryCount] = d;
    }



    private void init(int[] requestCount) {
        N = requestCount.length;
        dp = new int[N][MAX_INVENTORY];
        vis = new boolean[N][MAX_INVENTORY];
        this.request = requestCount;
        result = new ArrayList<Integer>();
    }
}
