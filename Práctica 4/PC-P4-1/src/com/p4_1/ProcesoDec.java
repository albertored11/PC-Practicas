package com.p4_1;

public class ProcesoDec extends Thread {

    public static final int N = 1000;

    private final A _a;

    ProcesoDec(A a) {

        _a = a;

    }

    public void run() {

        for (int i = 0; i < N; ++i)
            _a.decrementarN();

    }

}
