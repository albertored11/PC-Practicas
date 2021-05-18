package com.p5_1;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) throws IOException {

        Scanner in = new Scanner(System.in);
        String hostname, filepath;
        int port;
        Socket sock;
        InputStream inStr;
        OutputStream outStr;
        PrintWriter writer;
        BufferedReader reader;
        String text;

        System.out.print("Hostname: ");
        hostname = in.nextLine();

        System.out.print("Port: ");
        port = in.nextInt();
        in.nextLine(); // consume \n from int

        sock = new Socket(hostname, port);

        inStr = sock.getInputStream();
        outStr = sock.getOutputStream();

        System.out.print("Path to file: ");
        filepath = in.nextLine();

        writer = new PrintWriter(outStr, true);

        writer.println(filepath);

        reader = new BufferedReader(new InputStreamReader(inStr));

        text = reader.readLine();

        while (text != null) {
            System.out.println(text);
            text = reader.readLine();
        }

        outStr.close();
        inStr.close();
        writer.close();
        reader.close();
        in.close();

    }

}
