package com.p1_3;

import java.util.Vector;

public class Main {

    public static final int N = 10;

    public static void main(String[] args) throws InterruptedException {

        Vector<ProcesoFila> procesos = new Vector<>(N);

        int A[][] = new int[N][N];
        int B[][] = new int[N][N];
        int C[][] = new int[N][N];

        int val = 0;

        for (int i = 0; i < N; ++i)
            for (int j = 0; j < N; ++j) {
                A[i][j] = val;
                ++val;
            }

        for (int i = 0; i < N; ++i)
            for (int j = 0; j < N; ++j) {
                B[i][j] = val;
                ++val;
            }

        for (int i = 0; i < N; ++i)
            procesos.add(new ProcesoFila(i, A, B, C));

        for (int i = 0; i < N; ++i)
            procesos.elementAt(i).start();

        for (int i = 0; i < N; ++i)
            procesos.elementAt(i).join();

        System.out.println("A:");

        for (int i = 0; i < N; ++i) {

            for (int j = 0; j < N - 1; ++j)
                System.out.printf("%2d ", A[i][j]);

            System.out.printf("%2d\n", A[i][N - 1]);

        }

        System.out.println();

        System.out.println("B:");

        for (int i = 0; i < N; ++i) {

            for (int j = 0; j < N; ++j)
                System.out.printf("%3d ", B[i][j]);

            System.out.printf("%3d\n", B[i][N - 1]);

        }

        System.out.println();

        System.out.println("C:");

        for (int i = 0; i < N; ++i) {

            for (int j = 0; j < N - 1; ++j)
                System.out.printf("%6d ", C[i][j]);

            System.out.printf("%6d\n", C[i][N - 1]);

        }

    }

}
