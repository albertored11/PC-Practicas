package com.p4_1;

public class A {

    private int _n;

    A() {
        _n = 0;
    }

    public synchronized void incrementarN() {
        ++_n;
    }

    public synchronized void decrementarN() {
        --_n;
    }

    public int getN() {
        return _n;
    }

}
