package com.p4_2;

public class Consumidor extends Thread {

    private int _n;
    private Almacen _almacen;

    Consumidor(int n, Almacen almacen) {

        _n = n;
        _almacen = almacen;

    }

    public void run() {

        for (int i = 0; i < _n; ++i)
            _almacen.extraer();

    }

}
