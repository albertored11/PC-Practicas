package com.p5_2;

import java.io.*;
import java.net.Socket;

public class Receptor extends Thread {

    private final Usuario _emisor;
    private final int _port;

    public Receptor(Usuario emisor, int port) {

        _emisor = emisor;
        _port = port;

    }

    @Override
    public void run() {

        try { // TODO tratar excepciones

            Socket sock = new Socket(_emisor.getInetAddress(), _port);

            System.out.println("Socket establecido para recibir fichero!");

            InputStream inStr = sock.getInputStream();
            ObjectInputStream objInStr = new ObjectInputStream(inStr);

            Fichero file = (Fichero)objInStr.readObject();

            System.out.println(file);

        }
        catch (Exception e) {}

    }

}
