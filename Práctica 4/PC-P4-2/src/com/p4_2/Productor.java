package com.p4_2;

public class Productor extends Thread {

    private final int _id;
    private final int _n;
    private final Almacen _almacen;

    Productor(int id, int n, Almacen almacen) {

        _id = id;
        _n = n;
        _almacen = almacen;

    }

    public void run() {

        for (int i = 0; i < _n; ++i)
            _almacen.almacenar(new Producto(_id * _n + i));

    }

}
