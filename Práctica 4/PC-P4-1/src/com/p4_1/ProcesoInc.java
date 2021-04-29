package com.p4_1;

public class ProcesoInc extends Thread {

    public static final int N = 1000;

    private A _a;

    ProcesoInc(A a) {

        _a = a;

    }

    public void run() {

        for (int i = 0; i < N; ++i)
            _a.incrementarN();

    }

}
