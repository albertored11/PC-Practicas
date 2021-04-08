package com.p3_3;

import java.util.concurrent.Semaphore;

public class ProcesoDec extends Thread {

    private A _a;
    private Semaphore _mutex;
    private int _n;

    ProcesoDec(A a, Semaphore mutex, int n) {

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

            _a.decrementarN();

            _mutex.release();

        }

    }

}
