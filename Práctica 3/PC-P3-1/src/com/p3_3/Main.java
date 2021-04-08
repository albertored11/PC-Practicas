package com.p3_3;

import java.util.Vector;
import java.util.concurrent.Semaphore;

public class Main {

    public static final int M = 10;
    public static final int N = 1000;

    public static void main(String[] args) throws InterruptedException {

        A a = new A();
        Semaphore mutex = new Semaphore(1);

        Vector<ProcesoInc> procesosInc = new Vector<>(M);
        Vector<ProcesoDec> procesosDec = new Vector<>(M);

        for (int i = 0; i < M; ++i) {
            procesosInc.add(new ProcesoInc(a, mutex, N));
            procesosDec.add(new ProcesoDec(a, mutex, N));
        }

        for (int i = 0; i < M; ++i) {
            procesosInc.elementAt(i).start();
            procesosDec.elementAt(i).start();
        }

        for (int i = 0; i < M; ++i) {
            procesosInc.elementAt(i).join();
            procesosDec.elementAt(i).join();
        }

        System.out.println(a.getN());

    }

}
