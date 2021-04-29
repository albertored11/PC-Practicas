package com.p4_4;

public class Consumidor extends Thread {

    private final int _n;
    private final int _m;
    private final Almacen _almacen;

    Consumidor(int n, int m, Almacen almacen) {

        _n = n;
        _m = m;
        _almacen = almacen;

    }

    public void run() {

        for (int i = 0; i < _n; ++i)
            _almacen.extraer(_m);

    }

}
