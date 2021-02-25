package com.p1_2;

public class ProcesoInc extends Thread {

    public static final int N = 50;

    private A _a;

    ProcesoInc(A a) {
        _a = a;
    }

    public void run() {

        for (int i = 0; i < N; ++i)
            _a.incrementarN();

    }

}
