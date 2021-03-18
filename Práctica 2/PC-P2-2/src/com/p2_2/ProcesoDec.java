package com.p2_2;

public class ProcesoDec extends Thread {

    public static final int N = 1000;

    private A _a;
    private int _id;
    private Lock _lock;

    ProcesoDec(A a, int id, Lock lock) {

        _a = a;
        _id = id;
        _lock = lock;

    }

    public void run() {

        for (int i = 0; i < N; ++i) {

            _lock.takeLock(_id);

            _a.decrementarN();

            _lock.releaseLock(_id);

        }

    }

}
