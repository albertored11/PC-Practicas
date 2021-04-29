package com.p4_3;

public class Consumidor extends Thread {

    private int _n;
    private int _m;
    private Almacen _almacen;

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
