package com.p2_1;

public abstract class Lock {

    protected int _N;

    Lock(int N) {

        _N = N;

    }

    public abstract void takeLock(int procId);
    public abstract void releaseLock(int procId);

}
