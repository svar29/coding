package com.uber.coinChange;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CoinChangeTest {

    @Test
    public void testCoinChanges() {

        CoinChange coinChange = new CoinChange();
        long resultFor3 = coinChange.getNoOfWays(3);
        assertEquals(resultFor3, 3);
        List<String> resultStringsFor3 = coinChange.getAllPossibleWays(3);
        assertEquals(resultStringsFor3, Arrays.asList("aaa", "ab", "c"));

        System.out.println(coinChange.getNoOfWays(5));
        System.out.println(coinChange.getAllPossibleWays(5));
    }
}
