package com.uber.sudoku;

import java.util.*;

public class Sudoku {

    public int[][] generateValidBoard(int n) throws Exception {
        int res[][] = new int[n][n];

        List<Integer>[] columnValues = new List[n];
        List<Integer>[] squareValues = new List[n];
        int smallN = (int) Math.sqrt(n);
        for (int i=0;i<smallN;i++)
            squareValues[i] = new ArrayList<Integer>();

        for (int i=0;i<n;i++) {
            columnValues[i] = new LinkedList<Integer>();
            columnValues[i].add(i);
            squareValues[i/smallN].add(i);
            res[0][i] = i;
        }

        solve(res, 1, smallN, columnValues, squareValues);
        return res;
    }

    private void solve(int[][] res, int row, int smallN, List<Integer>[] columnValues, List<Integer>[] squareValues) throws Exception {
        int N = res.length;
        if (row == N)
            return;

        if (row % smallN == 0) {
            squareValues = new List[N];
            for (int i=0;i<N;i++)
                squareValues[i] = new ArrayList<Integer>();
        }

        List<Integer>[] possibleValuesForColumnInThisRow = new List[N];
        for (int i=0;i<N;i++) {
            possibleValuesForColumnInThisRow[i] = new ArrayList<Integer>();
            for (int j=0;j<N;j++) {
                if (!columnValues[i].contains(j) && !squareValues[i/smallN].contains(j))
                    possibleValuesForColumnInThisRow[i].add(j);
            }
        }

        int[] permutation = new ValidPermutation().getValidPermutation(possibleValuesForColumnInThisRow);

        for (int i=0;i<N;i++) {
            res[row][i] = permutation[i];
            columnValues[i].add(permutation[i]);
            squareValues[i/smallN].add(permutation[i]);
        }

        solve(res, row + 1, smallN, columnValues, squareValues);
    }

    public static void main(String args[]) throws Exception {
        Sudoku sudoku = new Sudoku();
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
