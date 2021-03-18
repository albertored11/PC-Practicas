package com.p2_1;

public class Main {

    public static final int M = 1;

    public static void main(String[] args) throws InterruptedException {

        A a = new A();
        Lock lock = new LockRompeEmpate(2 * M);

        ProcesoInc procesoInc = new ProcesoInc(a, M, lock);
        ProcesoDec procesoDec = new ProcesoDec(a, 2 * M, lock);

        procesoInc.start();
        procesoDec.start();

        procesoInc.join();
        procesoDec.join();

        System.out.println(a.getN());

    }

}
