package com.p5_2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Emisor extends Thread {

    private final String _file;
    private final int _port;

    public Emisor(String file, int port) {

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

            File file = new File(_file);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(fileReader);

//            System.out.println("Reading from " + _file + "...");

            StringBuilder textBuilder = new StringBuilder();

            String text = bufReader.readLine();

            while (text != null) {
                textBuilder.append(text);
                textBuilder.append('\n');
                text = bufReader.readLine();
            }

//            System.out.println("Sending text from " + _file + "...");

            objOutStr.writeObject(textBuilder.toString());

        }
        catch (Exception e) {}

    }

}
