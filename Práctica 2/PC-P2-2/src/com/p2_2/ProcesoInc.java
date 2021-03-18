package com.p2_2;

public class ProcesoInc extends Thread {

    public static final int N = 1000;

    private A _a;
    private int _id;
    private Lock _lock;

    ProcesoInc(A a, int id, Lock lock) {

        _a = a;
        _id = id;
        _lock = lock;

    }

    public void run() {

        for (int i = 0; i < N; ++i) {

            _lock.takeLock(_id);

            _a.incrementarN();

            _lock.releaseLock(_id);

        }

    }

}
