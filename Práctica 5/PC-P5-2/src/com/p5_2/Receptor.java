package com.p5_2;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class Receptor extends Thread {

    private final Usuario _emisor;
    private final int _port;
    private final Semaphore _sem;

    public Receptor(Usuario emisor, int port, Semaphore sem) {

        _emisor = emisor;
        _port = port;
        _sem = sem;

    }

    @Override
    public void run() {

        try { // TODO tratar excepciones

            Socket sock = new Socket(_emisor.getInetAddress(), _port);

//            System.out.println("Socket establecido para recibir fichero!");

            InputStream inStr = sock.getInputStream();
            ObjectInputStream objInStr = new ObjectInputStream(inStr);

            String file = (String)objInStr.readObject();

            // TODO save String to file

            System.out.println(file);

            _sem.release();

        }
        catch (Exception e) {}

    }

}
