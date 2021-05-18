package com.p5_1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Servidor {

    public static void main(String[] args) throws IOException {

        Scanner in = new Scanner(System.in);
        int port;
        ServerSocket servSock;

        System.out.print("Port: ");
        port = in.nextInt();

        servSock = new ServerSocket(port);

        while (true) {

            System.out.println("Waiting for clients...");

            Socket sock = servSock.accept();

            System.out.println("Client connected from " + sock.getInetAddress());

            (new OyenteCliente(sock)).start();

        }

    }
}
