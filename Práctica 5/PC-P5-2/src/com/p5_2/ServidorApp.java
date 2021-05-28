package com.p5_2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ServidorApp {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        System.out.print("Port: ");
        int port = in.nextInt();

        System.out.println();

        Servidor server = new Servidor(port);

        try {

            while (true) {

                System.out.println("Waiting for clients...");

                Socket sock = server.getServSock().accept();

                System.out.println("Client connected from " + sock.getInetAddress().toString().substring(1));

                OutputStream outStr = sock.getOutputStream();
                InputStream inStr = sock.getInputStream();

//                ObjectOutputStream objOutStr = new ObjectOutputStream(sock.getOutputStream());
//
//                List<Fichero> fileList = new ArrayList<>();
//
//                // ... añadir ficheros
//
//                for (Fichero file : fileList) {
//                    objOutStr.writeObject(file);
//                    objOutStr.flush();
//                }

                (new OyenteCliente(server, outStr, inStr)).start();

            }

        }
        catch (IOException e) {
            System.err.println("ERROR: IO exception");
        }

    }

}
