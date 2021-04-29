package com.p4_2;

public class Consumidor extends Thread {

    private final int _n;
    private final Almacen _almacen;

    Consumidor(int n, Almacen almacen) {

        _n = n;
        _almacen = almacen;

    }

    public void run() {

        for (int i = 0; i < _n; ++i)
            _almacen.extraer();

    }

}
