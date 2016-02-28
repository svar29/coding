package com.coding.hackerrank.solutions;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.lang.*;
/**
 * Created by sharma.varun on 30/09/15.
 */

/*
INPUT:
2
4 3
-1 -3 4 2
4 2
0 -1 2 1

OUTPUT:
YES
NO
 */
public class AngryProfessorSolution {
    public static void main(String[] args) throws Exception {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int T = Integer.parseInt(br.readLine());
            while(T-- > 0){
                String input[] = br.readLine().split(" ");
                int N = Integer.parseInt(input[0]);
                int K = Integer.parseInt(input[1]);
                String array[] = br.readLine().split(" ");
                int count =0;
                for (int i = 0; i< N ;i++){
                    if(Integer.parseInt(array[i])<=0){
                        count++;
                    }
                }
                if(count>=K){
                    System.out.println("NO");
                }
                else{
                    System.out.println("YES");
                }
            }
        }
    }
