package com.p1_2;

import java.util.Vector;

public class Main {

    public static final int M = 100;

    public static void main(String[] args) throws InterruptedException {

        A a = new A();
        Vector<ProcesoInc> procesosInc = new Vector<>(M);
        Vector<ProcesoDec> procesosDec = new Vector<>(M);

        for (int i = 0; i < M; ++i) {
            procesosInc.add(new ProcesoInc(a));
            procesosDec.add(new ProcesoDec(a));
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
