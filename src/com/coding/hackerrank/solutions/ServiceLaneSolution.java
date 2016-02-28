package com.coding.hackerrank.solutions;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;
/*
INPUT:
8 5
2 3 1 2 3 2 3 3
0 3
4 6
6 7
3 5
0 7

OUTPUT:
1
2
3
2
1
 */
public class ServiceLaneSolution {
    public static void main(String[] args){
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out=new PrintWriter(System.out);
        String line=null;
        try {
            line = in.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        StringTokenizer st=new StringTokenizer(line);
        int N=Integer.parseInt(st.nextToken());
        int T=Integer.parseInt(st.nextToken());
        try {
            line=in.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        st=new StringTokenizer(line);
        int arr[]=new int[100002];
        for(int i=0;i<N;i++)
        {
            arr[i]=Integer.parseInt(st.nextToken());
        }
        int j=0,k=0;
        for(int i=0;i<T;i++)
        {
            try {
                line=in.readLine();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            st=new StringTokenizer(line);
            j=Integer.parseInt(st.nextToken());
            k=Integer.parseInt(st.nextToken());
            int min=Integer.MAX_VALUE;
            for(int l=j;l<=k;l++)
            {
                if(arr[l]<min)
                    min=arr[l];
            }
            out.println(min);
        }
        out.close();
    }
}

