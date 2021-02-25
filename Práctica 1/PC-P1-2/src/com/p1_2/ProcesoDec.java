package com.p1_2;

public class ProcesoDec extends Thread {

    public static final int N = 50;

    private A _a;

    ProcesoDec(A a) {
        _a = a;
    }

    public void run() {

        for (int i = 0; i < N; ++i)
            _a.decrementarN();

    }

}
