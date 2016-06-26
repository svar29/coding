package com.uber.sudoku;

import java.math.BigInteger;

public class SudokuBits {

    public int[][] generateValidBoard(int n) throws Exception {
        int res[][] = new int[n][n];

        BigInteger[] columnValues = new BigInteger[n];
        int smallN = (int) Math.sqrt(n);
        for (int i=0;i<n;i++) {
            columnValues[i] = BigInteger.ZERO.setBit(i);
            res[0][i] = i;
        }

        solve(res, 1, smallN, columnValues);
        return res;
    }

    private void solve(int[][] res, int row, int smallN, BigInteger[] columnValues) throws Exception {
        int N = res.length;
        if (row == N)
            return;

        int[] permutation;
        if (row % smallN == 0) {
            BigInteger[] possibleValuesForColumnInThisRow = new BigInteger[N];
            for (int i=0;i<N;i++) {
                possibleValuesForColumnInThisRow[i] = BigInteger.ZERO.add(BigInteger.ZERO);
                for (int j=0;j<N;j++) {
                    if (!columnValues[i].testBit(j))
                        possibleValuesForColumnInThisRow[i] = possibleValuesForColumnInThisRow[i].setBit(j);
                }
            }

            permutation = new ValidPermutation().getValidPermutationWithBits(possibleValuesForColumnInThisRow);
        }
        else {
            permutation = new int[N];
            for (int i=smallN;i<N;i++) {
                permutation[i] = res[row-1][i-smallN];
            }

            for (int i=0;i<smallN;i++)
                permutation[i] = res[row-1][N-smallN-i];
        }


        for (int i=0;i<N;i++) {
            res[row][i] = permutation[i];
            columnValues[i] = columnValues[i].setBit(permutation[i]);
        }
        solve(res, row + 1, smallN, columnValues);

    }

    public static void main(String args[]) throws Exception {
        SudokuBits sudoku = new SudokuBits();
        int res[][] = sudoku.generateValidBoard(4);
        sudoku.printBoard(res);
        res = sudoku.generateValidBoard(9);
        sudoku.printBoard(res);
        res = sudoku.generateValidBoard(16);
        sudoku.printBoard(res);

        res = sudoku.generateValidBoard(25);
        sudoku.printBoard(res);
    }

    public void printBoard(int board[][]) {
        for (int i=0;i<board.length;i++, System.out.println())
            for (int j=0;j<board[0].length;j++)
                System.out.print(board[i][j] + 1 + " ");

        System.out.println();
        System.out.println();
    }
}
