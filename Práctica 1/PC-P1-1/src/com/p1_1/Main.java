package com.p1_1;

import java.util.Vector;

public class Main {

    public static final int N = 100;

    public static void main(String[] args) throws InterruptedException {

        Vector<Proceso> procesos = new Vector<>(N);

        for (int i = 0; i < N; ++i)
            procesos.add(new Proceso(i));

        for (int i = 0; i < N; ++i)
            procesos.elementAt(i).start();

        for (int i = 0; i < N; ++i)
            procesos.elementAt(i).join();

        System.out.println("Todos los threads han terminado");

    }

}
