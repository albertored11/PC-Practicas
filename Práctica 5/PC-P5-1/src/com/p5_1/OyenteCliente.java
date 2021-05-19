package com.p5_1;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;

public class OyenteCliente extends Thread {

    private final OutputStream _outStr;
    private final InputStream _inStr;

    OyenteCliente(OutputStream outStr, InputStream inStr) {

        _outStr = outStr;
        _inStr = inStr;

    }

    public void run() {

        PrintWriter writer = new PrintWriter(_outStr, true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(_inStr));
        String filepath = null;

        try {

            filepath = reader.readLine();

            File file = new File(filepath);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(fileReader);

            System.out.println("Reading from " + filepath + "...");

            StringBuilder textBuilder = new StringBuilder();

            String text = bufReader.readLine();

            while (text != null) {
                textBuilder.append(text);
                textBuilder.append('\n');
                text = bufReader.readLine();
            }

            System.out.println("Sending text from " + filepath + "...");

            writer.println(textBuilder);

            _inStr.close();
            _outStr.close();
            bufReader.close();
            reader.close();
            writer.close();

        } catch (FileNotFoundException e) {
            System.err.println("ERROR: File " + filepath + " not found");
            writer.println("FILE_NOT_FOUND");
        }
        catch (IOException e) {
            System.err.println("ERROR: IO exception");
        }

    }

}
