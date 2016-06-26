package com.uber.power;

public class Power {

    private static int count = 0;
    private static final int MOD = (int)1e9 + 7;
    public static int power(int a, int b) {
        count++;
        if (b == 0) return 1;

        int result = power(a, b / 2);
        result = (int) ((1L * result * result) % MOD);
        if (b % 2 == 1)
            result = (int) ((1L * result * a) % MOD);
        return result;
    }

    public static void main(String args[]) {
        System.out.println(power(2, (int) 5e6));
        System.out.println(count);
    }
}
