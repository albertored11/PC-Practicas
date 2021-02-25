package com.p1_3;

public class ProcesoFila extends Thread {

    public static final int N = 10;

    private int _i;
    private int _A[][];
    private int _B[][];
    private int _C[][];

    ProcesoFila(int i, int A[][], int B[][], int C[][]) {

        _i = i;
        _A = A;
        _B = B;
        _C = C;

    }

    public void run() {

        for (int j = 0; j < N; ++j) {

            _C[_i][j] = 0;

            for (int k = 0; k < N; ++k)
                _C[_i][j] += _A[_i][k] * _B[k][j];

        }

    }

}
