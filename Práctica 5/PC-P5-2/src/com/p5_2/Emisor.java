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

        // Crear server socket
        ServerSocket servSock;

        try {
            servSock = new ServerSocket(_port);
        } catch (IOException e) {
            System.err.println("ERROR: I/O error in socket");
            return;
        }

        // Esperar a que se conecte el receptor y obtener socket
        Socket sock;

        try {
            sock = servSock.accept();
        } catch (IOException e) {
            System.err.println("ERROR: I/O error in server socket");
            return;
        }

        // Obtener flujo de salida
        OutputStream outStr;

        try {
            outStr = sock.getOutputStream();
        } catch (IOException e) {
            System.err.println("ERROR: I/O error in socket");
            return;
        }

        // Obtener flujo de salida para objetos
        ObjectOutputStream objOutStr;

        try {
            objOutStr = new ObjectOutputStream(outStr);
        } catch (IOException e) {
            System.err.println("ERROR: I/O error in stream");
            return;
        }

        // Leer del fichero
        File file = new File(_file);

        FileReader fileReader;

        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {

            try {
                objOutStr.writeObject("ERROR: file " + _file + " not found\n");
            } catch (IOException ioException) {
                System.err.println("ERROR: I/O error in stream");
            }

            return;

        }

        BufferedReader bufReader = new BufferedReader(fileReader);

        // TODO ir enviando lineas?

        // Guardar el contenido del fichero en un String
        StringBuilder textBuilder = new StringBuilder();

        String text;

        try {
            text = bufReader.readLine();
        } catch (IOException e) {
            System.err.println("ERROR: I/O error in buffer");
            return;
        }

        while (text != null) {

            textBuilder.append(text);
            textBuilder.append('\n');

            try {
                text = bufReader.readLine();
            } catch (IOException e) {

                try {
                    objOutStr.writeObject("ERROR: I/O error in buffer\n");
                } catch (IOException ioException) {
                    System.err.println("ERROR: I/O error in stream");
                }

                return;

            }

        }

        // Enviar el String al receptor
        try {
            objOutStr.writeObject(textBuilder.toString());
        } catch (IOException e) {
            System.err.println("ERROR: I/O error in stream");
        }

    }

}
