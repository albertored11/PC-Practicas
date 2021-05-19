package com.p5_1;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.exit;

public class Cliente {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        System.out.print("Hostname: ");
        String hostname = in.nextLine();

        System.out.print("Port: ");
        int port = in.nextInt();
        in.nextLine(); // consume \n from int

        try {

            Socket sock = new Socket(hostname, port);

            OutputStream outStr = sock.getOutputStream();
            InputStream inStr = sock.getInputStream();

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
