package com.p4_4;

import java.util.ArrayList;
import java.util.List;

public class Productor extends Thread {

    private final int _id;
    private final int _n;
    private final int _m;
    private final Almacen _almacen;

    Productor(int id, int n, int m, Almacen almacen) {

        _id = id;
        _n = n;
        _m = m;
        _almacen = almacen;

    }

    public void run() {

        for (int i = 0; i < _n; ++i) {

            List<Producto> productos = new ArrayList<>();

            for (int j = 0; j < _m; ++j)
                productos.add(new Producto(_id * _n * _m + i * _m + j));

            _almacen.almacenar(productos);

        }

    }

}
