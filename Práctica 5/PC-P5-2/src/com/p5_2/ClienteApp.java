package com.p5_2;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import static java.lang.System.exit;

public class ClienteApp {

    public static void main(String[] args) throws UnknownHostException {

        Scanner in = new Scanner(System.in);

        String hostname = "localhost";

        System.out.print("Username: ");
        String username = in.nextLine();

        System.out.print("Server: ");
        String server = in.nextLine();

        System.out.print("Port: ");
        int port = in.nextInt();
        in.nextLine(); // consume \n from int

        System.out.println();

        Usuario user = new Usuario(username, hostname);

        try {

            Socket sock = new Socket(server, port);

            OutputStream outStr = sock.getOutputStream();
            InputStream inStr = sock.getInputStream();

            ObjectOutputStream objOutStr = new ObjectOutputStream(outStr);
            ObjectInputStream objInStr = new ObjectInputStream(inStr);

            Stream stream = new Stream(objOutStr, objInStr);

            Cliente client = new Cliente(user, stream);

            (new OyenteServidor(client, objOutStr, objInStr)).start();

            System.out.println("Files to share? (end with empty input)");

            System.out.print("Path to file: ");
            String filepath = in.nextLine();

            while (!filepath.isEmpty()) {

                user.addFile(filepath);

                System.out.print("Path to file: ");
                filepath = in.nextLine();

            }

            System.out.println();

            // TODO cambiar origen y destino
            Mensaje mc = new MensajeConexion("Client 1", "Server", user);

            objOutStr.writeObject(mc);

            Semaphore sem = client.getSem();

            int option;

            do {

                sem.acquire();

                System.out.println("    ~ MENU ~");
                System.out.println("1. Get user list");
                System.out.println("2. Request file");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");

                option = in.nextInt();
                in.nextLine(); // consume \n from int

                System.out.println();

                switch (option) {

                    case 1:

                        MensajeListaUsuarios m = new MensajeListaUsuarios("Client 1", "Server", user);

                        objOutStr.writeObject(m);

                        break;

                    case 2:

                        System.out.print("Path to file: ");
                        String file = in.nextLine();

                        MensajePedirFichero mpf = new MensajePedirFichero("Client 1", "Server", file, user);

                        objOutStr.writeObject(mpf);

                        break;

                    case 0:

                        MensajeCerrarConexion mcc = new MensajeCerrarConexion("Client 1", "Server", user);

                        objOutStr.writeObject(mcc);

                        break;

                }

            } while (option != 0);

//            in.close();

//            ObjectInputStream objInStr = new ObjectInputStream(sock.getInputStream());
//
//            Fichero file = (Fichero)objInStr.readObject();
//
//            System.out.print("Path to file: ");
//            String filepath = in.nextLine();
//
//            PrintWriter writer = new PrintWriter(outStr, true);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inStr));
//
//            writer.println(filepath);
//
//            String text = reader.readLine();
//
//            if (text.equals("FILE_NOT_FOUND")) {
//                System.err.println("ERROR: File " + filepath + " not found");
//                exit(1);
//            }
//
//            while (text != null) {
//                System.out.println(text);
//                text = reader.readLine();
//            }
//
//            outStr.close();
//            inStr.close();
//            writer.close();
//            reader.close();

        }
        catch (IOException e) {
            System.err.println("ERROR: IO exception");
            e.printStackTrace(); // TODO quitar
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
