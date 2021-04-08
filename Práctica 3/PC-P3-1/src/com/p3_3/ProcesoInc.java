package com.p3_3;

import java.util.concurrent.Semaphore;

public class ProcesoInc extends Thread {

    private A _a;
    private Semaphore _mutex;
    private int _n;

    ProcesoInc(A a, Semaphore mutex, int n) {

        _a = a;
        _mutex = mutex;
        _n = n;

    }

    public void run() {

        for (int i = 0; i < _n; ++i) {

            try {
                _mutex.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            _a.incrementarN();

            _mutex.release();

        }

    }

}
