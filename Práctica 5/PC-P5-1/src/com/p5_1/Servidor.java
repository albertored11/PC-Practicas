package com.p5_1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Servidor {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        System.out.print("Port: ");
        int port = in.nextInt();

        try {

            ServerSocket servSock = new ServerSocket(port);

            while (true) {

                System.out.println("Waiting for clients...");

                Socket sock = servSock.accept();

                System.out.println("Client connected from " + sock.getInetAddress().toString().substring(1));

                OutputStream outStr = sock.getOutputStream();
                InputStream inStr = sock.getInputStream();

                (new OyenteCliente(outStr, inStr)).start();

            }

        }
        catch (IOException e) {
            System.err.println("ERROR: IO exception");
        }

    }
}
