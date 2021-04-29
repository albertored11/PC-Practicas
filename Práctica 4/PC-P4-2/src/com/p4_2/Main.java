package com.p4_2;

import java.util.Vector;

public class Main {

    public static final int NUM_PROD = 5;
    public static final int NUM_CONS = 5;
    public static final int N = 20;
    public static final int CAP_ALMACEN = 5000;

    public static void main(String[] args) throws InterruptedException {

        Almacen almacen = new AlmacenMultiple(CAP_ALMACEN);

        Vector<Productor> productores = new Vector<>(NUM_PROD);
        Vector<Consumidor> consumidores = new Vector<>(NUM_CONS);

        for (int i = 0; i < NUM_PROD; ++i)
            productores.add(new Productor(i, N, almacen));

        for (int i = 0; i < NUM_CONS; ++i)
            consumidores.add(new Consumidor(N, almacen));

        for (int i = 0; i < NUM_PROD; ++i)
            productores.elementAt(i).start();

        for (int i = 0; i < NUM_CONS; ++i)
            consumidores.elementAt(i).start();

        for (int i = 0; i < NUM_PROD; ++i)
            productores.elementAt(i).join();

        for (int i = 0; i < NUM_CONS; ++i)
            consumidores.elementAt(i).join();

    }

}
