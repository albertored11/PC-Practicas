package com.p3_2;

import java.util.concurrent.Semaphore;

public class Consumidor extends Thread {

    private int _id;
    private int _n;
    private Semaphore _lleno, _vacio;
    private Almacen _almacen;

    Consumidor(int id, int n, Semaphore lleno, Semaphore vacio, Almacen almacen) {

        _id = id;
        _n = n;
        _lleno = lleno;
        _vacio = vacio;
        _almacen = almacen;

    }

    public void run() {

        for (int i = 0; i < _n; ++i) {

            try {
                _lleno.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Objeto con ID " + _almacen.extraer()
                    + " extraído por el consumidor con ID " + _id);

            _vacio.release();

        }

    }

}
