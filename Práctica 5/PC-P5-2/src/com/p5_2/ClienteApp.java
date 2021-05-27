package com.p5_2;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import static java.lang.System.exit;

public class ClienteApp {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        // Leer nombre de usuario
        System.out.print("Username: ");
        String username = in.nextLine();

        // Obtener interfaces de red del sistema
        Enumeration<NetworkInterface> ifaces = null;

        try {
            ifaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            System.err.println("ERROR: could not retrieve network interfaces");
            in.close();
            exit(1);
        }

        // Guardar la lista de interfaces de red en un String
        StringBuilder sb = new StringBuilder();

        if (ifaces.hasMoreElements())
            sb.append(ifaces.nextElement().getDisplayName());

        while (ifaces.hasMoreElements()) {
            sb.append(", ");
            sb.append(ifaces.nextElement().getDisplayName());
        }

        // Mostrar interfaces de red disponibles
        System.out.print("Network interface (" + sb + "): ");

        // Leer interfaz de red
        String iface = in.nextLine();

        // Guardar la primera dirección IPv4 que se encuentre en la interfaz
        NetworkInterface ni = null;

        try {
            ni = NetworkInterface.getByName(iface);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        Enumeration<InetAddress> en = ni.getInetAddresses();

        InetAddress i = en.nextElement();

        while (i.getClass() != Inet4Address.class)
            i = en.nextElement();

        String hostname = i.toString().substring(1);

        // Leer dirección del servidor
        System.out.print("Server: ");
        String server = in.nextLine();

        // Leer puerto del servidor
        System.out.print("Port: ");
        int port = in.nextInt();
        in.nextLine(); // consume \n from int

        System.out.println();

        // Crear instancia de usuario
        Usuario user = new Usuario(username, hostname);

        try {

            // Crear socket cliente-servidor
            Socket sock = new Socket(server, port);

            // Obtener flujos de entrada y de salida
            OutputStream outStr = sock.getOutputStream();
            InputStream inStr = sock.getInputStream();

            ObjectOutputStream objOutStr = new ObjectOutputStream(outStr);
            ObjectInputStream objInStr = new ObjectInputStream(inStr);

            // Crear instancia de cliente
            Cliente client = new Cliente(user);

            // Lanzar hilo OyenteServidor para gestionar los mensajes recibidos
            (new OyenteServidor(client, objOutStr, objInStr)).start();

            // Leer rutas de ficheros que se pondrán a disposición de los demás clientes
            System.out.println("Files to share? (end with empty input)");

            System.out.print("Path to file: ");
            String filepath = in.nextLine();

            while (!filepath.isEmpty()) {

                user.addFile(filepath);

                System.out.print("Path to file: ");
                filepath = in.nextLine();

            }

            System.out.println();

            // Enviar MENSAJE_CONEXION
            Mensaje mc = new MensajeConexion(user);

            objOutStr.writeObject(mc);

            Semaphore sem = client.getSem();

            int option;

            do {

                // Post a semáforo: espera a que OyenteServidor haga release antes de volver a mostrar el menú
                sem.acquire();

                // Será true si el usuario ya existe, con lo que cerramos la aplicación
                if (client.isTerminate()) {

                    in.close();
                    outStr.close();
                    inStr.close();
                    objOutStr.close();
                    objInStr.close();

                    exit(1);

                }

                // Mostrar menú
                System.out.println("    ~ MENU ~");
                System.out.println("1. Get user list");
                System.out.println("2. Request file");
                System.out.println("0. Exit");
                System.out.println();
                System.out.print("Choose an option: ");

                // Leer opción
                String line = in.nextLine();

                System.out.println();

                // Comprobar que la opción es un entero
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

                        // Mandar MENSAJE_LISTA_USUARIOS a OyenteCliente
                        MensajeListaUsuarios m = new MensajeListaUsuarios(user);

                        objOutStr.writeObject(m);

                        break;

                    case 2:

                        // Leer ruta del fichero que se quiere obtener
                        System.out.print("Path to file: ");
                        String file = in.nextLine();

                        System.out.println();

                        // Mandar MENSAJE_PEDIR_FICHERO a OyenteCliente
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
