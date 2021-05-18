package com.p5_1;

import java.io.*;
import java.net.Socket;

public class OyenteCliente extends Thread {

    private final Socket _sock;

    OyenteCliente(Socket sock) {

        _sock = sock;

    }

    public void run() {

        InputStream inStr;
        OutputStream outStr;
        String filepath;
        BufferedReader reader;
        File file;
        FileReader fileReader;
        String text;
        StringBuilder textBuilder;
        BufferedReader bufFileReader;
        PrintWriter writer;

        try {

            inStr = _sock.getInputStream();
            outStr = _sock.getOutputStream();

            reader = new BufferedReader(new InputStreamReader(inStr));

            filepath = reader.readLine();

            file = new File(filepath);
            fileReader = new FileReader(file);
            bufFileReader = new BufferedReader(fileReader);

            System.out.println("Reading from " + filepath + "...");

            textBuilder = new StringBuilder();

            text = bufFileReader.readLine();

            while (text != null) {
                textBuilder.append(text);
                textBuilder.append('\n');
                text = bufFileReader.readLine();
            }

            System.out.println("Sending text from " + filepath + "...");

            writer = new PrintWriter(outStr, true);
            writer.println(textBuilder);

            _sock.close();
            inStr.close();
            outStr.close();
            bufFileReader.close();
            reader.close();
            writer.close();

        } catch (IOException ignored) {}

    }

}
