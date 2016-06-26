package com.uber.sudoku;

import java.math.BigInteger;
import java.util.*;

public class ValidPermutation {

    private boolean solutionFoundForRow = false;
    private int N;
    private List<Integer>[] values;
    private Map<StateKey, Boolean> dp;
    private int[] res;
    private boolean[] used;
    private BigInteger[] bigIntegerValues;

    /*
        Given an array indicating all possible values at each position, return a valid permutation found if any
    */
    public int[] getValidPermutation(List<Integer>[] values) throws Exception {

        init(values);
//        boolean result = solve(0, BigInteger.ZERO);
//        if (result) {
//            reconstruct(0, BigInteger.ZERO, res);
//            return res;
//        }
        go(0, values, res, used);

        if (solutionFoundForRow)
            return res;
        throw new Exception("No valid solution found");
    }

    private void init(List<Integer>[] values) {
        this.values = values;
        N = this.values.length;
        dp = new HashMap<StateKey, Boolean>();
        res = new int[N];
        Arrays.fill(res, -1);
        used = new boolean[N];
        solutionFoundForRow = false;
    }

    private void go(int pos, List<Integer>[] values, int[] res, boolean[] used) {
        if (pos == values.length) {
            solutionFoundForRow = true;
            return;
        }
        for (Integer val: values[pos]) {
            if (!used[val]) {
                res[pos] = val;
                used[val] = true;
                go(pos + 1, values, res, used);
                if (solutionFoundForRow)
                    return;
                res[pos] = -1;
                used[val] = false;
            }
        }
    }

    private boolean solve(int pos, BigInteger used) {

        if (pos == N)
            return true;

        StateKey state = new StateKey(pos, used);
        if (dp.containsKey(state))
            return dp.get(state);

        boolean result = false;

        for (Integer value: values[pos]) {
            if (used.testBit(value))
                continue;
            result = solve(pos + 1, used.setBit(value));

            if (result) break;
        }

        dp.put(state, result);
        return dp.get(state);
    }

    private void reconstruct(int pos, BigInteger used, int[] solution) {

        if (pos == N)
            return;

        boolean result = false;

        for (Integer value: values[pos]) {
            if (used.testBit(value))
                continue;
            result = solve(pos + 1, used.setBit(value));

            if (result) {
                solution[pos] = value;
                reconstruct(pos + 1, used.setBit(value), solution);
            }
        }
    }

    public int[] getValidPermutationWithBits(BigInteger[] values) throws Exception {
        initBits(values);
        boolean result = solveBits(0, BigInteger.ZERO);
        if (result) {
            reconstructBits(0, BigInteger.ZERO, res);
            return res;
        }

        throw new Exception("No valid solution found");
    }

    private void reconstructBits(int pos, BigInteger used, int[] solution) {
        if (pos == N)
            return;

        boolean result = false;

        for (int value = 0; value < N; value++) {
            if(!bigIntegerValues[pos].testBit(value))
                continue;
            if (used.testBit(value))
                continue;
            result = solveBits(pos + 1, used.setBit(value));

            if (result) {
                solution[pos] = value;
                reconstructBits(pos + 1, used.setBit(value), solution);
            }
        }
    }

    private boolean solveBits(int pos, BigInteger used) {
        if (pos == N)
            return true;

        StateKey state = new StateKey(pos, used);
        if (dp.containsKey(state))
            return dp.get(state);

        boolean result = false;

        for (int value = 0; value < N; value++) {
            if(!bigIntegerValues[pos].testBit(value))
                continue;
            if (used.testBit(value))
                continue;
            result = solveBits(pos + 1, used.setBit(value));

            if (result) break;
        }

        dp.put(state, result);
        return dp.get(state);
    }

    private void initBits(BigInteger[] values) {
        this.bigIntegerValues = values;
        N = values.length;
        dp = new HashMap<StateKey, Boolean>();
        res = new int[N];
        Arrays.fill(res, -1);
    }

    private class StateKey {
        private int pos;
        private BigInteger used;

        public StateKey(int pos, BigInteger used) {
            this.pos = pos;
            this.used = used;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof StateKey)) return false;

            StateKey stateKey = (StateKey) o;

            if (pos != stateKey.pos) return false;
            if (!used.equals(stateKey.used)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = pos;
            result = 31 * result + used.hashCode();
            return result;
        }
    }
}
