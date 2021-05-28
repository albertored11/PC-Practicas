package com.p5_2;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class Receptor extends Thread {

    private final Usuario _emisor; // usuario emisor
    private final int _port; // puerto del socket que se establecerá entre emisor y receptor
    private final Semaphore _sem; // semáforo para controlar el flujo de stdout del cliente

    public Receptor(Usuario emisor, int port, Semaphore sem) {

        _emisor = emisor;
        _port = port;
        _sem = sem;

    }

    @Override
    public void run() {

        // Crear socket con receptor
        Socket sock;

        try {
            sock = new Socket(_emisor.getInetAddress(), _port);
        } catch (IOException e) {
            System.err.println("ERROR: server not found");
            _sem.release(); // release a semáforo
            return;
        }

        // Obtener flujo de entrada
        InputStream inStr;

        try {
            inStr = sock.getInputStream();
        } catch (IOException e) {
            System.err.println("ERROR: I/O error in socket");
            _sem.release(); // release a semáforo
            return;
        }

        // Obtener flujo de entrada para objetos
        ObjectInputStream objInStr;
        try {
            objInStr = new ObjectInputStream(inStr);
        } catch (IOException e) {
            System.err.println("ERROR: I/O error in stream");
            _sem.release(); // release a semáforo
            return;
        }

        String file;
        try {
            file = (String)objInStr.readObject();
        } catch (IOException e) {
            System.err.println("ERROR: I/O error in stream");
            _sem.release(); // release a semáforo
            return;
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: (internal) wrong message class");
            _sem.release(); // release a semáforo
            return;
        }

        // TODO save String to file

        System.out.println(file);

        _sem.release(); // release a semáforo

    }

}
