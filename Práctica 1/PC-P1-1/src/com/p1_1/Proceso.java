package com.p1_1;

public class Proceso extends Thread {

    public static final int T = 10;

    private int _id;

    Proceso (int id) {

        _id = id;

    }

    public void run() {

        System.out.println(_id + " empieza");

        try {
            Thread.sleep(T);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(_id + " termina");

    }

}
