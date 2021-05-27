package com.p5_2;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import static java.lang.System.exit;

public class ClienteApp {

    public static void main(String[] args) throws SocketException {

        Scanner in = new Scanner(System.in);

        System.out.print("Username: ");
        String username = in.nextLine();

        Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
        StringBuilder sb = new StringBuilder();

        if (ifaces.hasMoreElements())
            sb.append(ifaces.nextElement().getDisplayName());

        while (ifaces.hasMoreElements()) {
            sb.append(", ");
            sb.append(ifaces.nextElement().getDisplayName());
        }

        System.out.print("Network interface (" + sb + "): ");

        String iface = in.nextLine();

        NetworkInterface ni = NetworkInterface.getByName(iface);
        Enumeration<InetAddress> en = ni.getInetAddresses();

        InetAddress i = en.nextElement();

        while (i.getClass() != Inet4Address.class)
            i = en.nextElement();

        String hostname = i.toString().substring(1);

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

            Cliente client = new Cliente(user);

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

            Mensaje mc = new MensajeConexion(user);

            objOutStr.writeObject(mc);

            Semaphore sem = client.getSem();

            int option;

            do {

                sem.acquire();

                if (client.isTerminate())
                    exit(1);

                System.out.println("    ~ MENU ~");
                System.out.println("1. Get user list");
                System.out.println("2. Request file");
                System.out.println("0. Exit");
                System.out.println();
                System.out.print("Choose an option: ");

                String line = in.nextLine();

                System.out.println();

                try {
                    option = Integer.parseInt(line);
                }
                catch (NumberFormatException e) {

                    System.err.println("ERROR: option must be an integer");
                    System.out.println();

                    option = -1;

                }

                switch (option) {

                    case 1:

                        MensajeListaUsuarios m = new MensajeListaUsuarios(user);

                        objOutStr.writeObject(m);

                        break;

                    case 2:

                        // TODO si nadie lo tiene, error

                        System.out.print("Path to file: ");
                        String file = in.nextLine();

                        System.out.println();

                        MensajePedirFichero mpf = new MensajePedirFichero(file, user);

                        objOutStr.writeObject(mpf);

                        break;

                    case 0:

                        MensajeCerrarConexion mcc = new MensajeCerrarConexion(user);

                        objOutStr.writeObject(mcc);

                        break;

                    case -1:

                        sem.release();

                        break;

                    default:

                        System.err.println("ERROR: option " + option + " not valid");
                        System.out.println();

                        sem.release();

                        break;

                }

            } while (option != 0);

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
