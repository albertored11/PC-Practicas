package com.p5_2.servidor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.exit;

public class ServidorApp {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        // Leer puerto
        System.out.print("Port: ");
        int port = in.nextInt();

        System.out.println();

        // Crear instancia de servidor
        Servidor server = new Servidor(port);

        while (true) {

            System.out.println("Waiting for clients...");

            Socket sock = null;

            try {
                sock = server.getServSock().accept();
            } catch (IOException e) {
                System.err.println("ERROR: I/O error in server socket");
                exit(1);
            }

            System.out.println("Client connected from " + sock.getInetAddress().toString().substring(1));

            // Obtener flujos de entrada y de salida
            OutputStream outStr = null;
            InputStream inStr = null;

            try {
                outStr = sock.getOutputStream();
                inStr = sock.getInputStream();
            } catch (IOException e) {
                System.err.println("ERROR: I/O error in socket");
                in.close();
                exit(1);
            }

            // Lanzar hilo OyenteCliente para gestionar los mensajes recibidos
            (new OyenteCliente(server, outStr, inStr)).start();

        }

    }

}
