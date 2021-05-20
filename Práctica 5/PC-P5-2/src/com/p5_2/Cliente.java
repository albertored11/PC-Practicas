package com.p5_2;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import static java.lang.System.exit;

public class Cliente {

    private int _id;

    public static void main(String[] args) throws UnknownHostException {

        // TODO username y hostname como atributos? main es static

        Scanner in = new Scanner(System.in);

        String hostname = InetAddress.getLocalHost().toString();

        System.out.print("Username: ");
        String username = in.nextLine();

        System.out.print("Server: ");
        String server = in.nextLine();

        System.out.print("Port: ");
        int port = in.nextInt();
        in.nextLine(); // consume \n from int

        try {

            Socket sock = new Socket(server, port);

            OutputStream outStr = sock.getOutputStream();
            InputStream inStr = sock.getInputStream();

//            ObjectInputStream objInStr = new ObjectInputStream(sock.getInputStream());
//
//            Fichero file = (Fichero)objInStr.readObject();

            System.out.print("Path to file: ");
            String filepath = in.nextLine();

            PrintWriter writer = new PrintWriter(outStr, true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStr));

            writer.println(filepath);

            String text = reader.readLine();

            if (text.equals("FILE_NOT_FOUND")) {
                System.err.println("ERROR: File " + filepath + " not found");
                exit(1);
            }

            while (text != null) {
                System.out.println(text);
                text = reader.readLine();
            }

            outStr.close();
            inStr.close();
            writer.close();
            reader.close();

        }
        catch (IOException e) {
            System.err.println("ERROR: IO exception");
        }

        in.close();

    }

}
