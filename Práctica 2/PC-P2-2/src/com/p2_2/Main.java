package com.p2_2;

import java.util.Vector;

public class Main {

    public static final int M = 2;

    public static void main(String[] args) throws InterruptedException {

        A a = new A();
//        Lock lock = new LockRompeEmpate(2 * M);
//        Lock lock = new LockTicket(2 * M);
        Lock lock = new LockBakery(2 * M);

        Vector<ProcesoInc> procesosInc = new Vector<>(M);
        Vector<ProcesoDec> procesosDec = new Vector<>(M);

        for (int i = 1; i <= M; ++i) {
            procesosInc.add(new ProcesoInc(a, 2 * i - 1, lock));
            procesosDec.add(new ProcesoDec(a, 2 * i, lock));
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
