package com.p5_2;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Emisor extends Thread {

    private Fichero _file;
    private final int _port;

    public Emisor(Fichero file, int port) {

        _file = file;
        _port = port;

    }

    @Override
    public void run() {

        try { // TODO tratar excepciones

            ServerSocket servSock = new ServerSocket(_port);

            Socket sock = servSock.accept();

            OutputStream outStr = sock.getOutputStream();
            ObjectOutputStream objOutStr = new ObjectOutputStream(outStr);

            objOutStr.writeObject(_file);

        }
        catch (Exception e) {}

    }

}
