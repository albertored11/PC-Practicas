package pc.p5.cliente;

import pc.p5.usuario.Fichero;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Emisor extends Thread {

    private final Fichero _file; // fichero que se va a enviar
    private final int _port; // puerto del socket que se establecerá entre emisor y receptor

    public Emisor(Fichero file, int port) {

        _file = file;
        _port = port;

    }

    @Override
    public void run() {

        // Crear server socket
        ServerSocket servSock;

        try {
            servSock = new ServerSocket(_port);
        } catch (IOException e) {
            System.err.println("ERROR: I/O error in socket");
            return;
        }

        // Esperar a que se conecte el receptor y obtener socket
        Socket sock;

        try {
            sock = servSock.accept();
        } catch (IOException e) {
            System.err.println("ERROR: I/O error in server socket");
            return;
        }

        // Obtener flujo de salida
        OutputStream outStr;

        try {
            outStr = sock.getOutputStream();
        } catch (IOException e) {
            System.err.println("ERROR: I/O error in socket");
            return;
        }

        // Obtener flujo de salida para objetos
        ObjectOutputStream objOutStr;

        try {
            objOutStr = new ObjectOutputStream(outStr);
        } catch (IOException e) {
            System.err.println("ERROR: I/O error in stream");
            return;
        }

        // Crear flujo de entrada para leer el fichero
        FileInputStream fileInStr;

        try {
            fileInStr = new FileInputStream(_file.getFilepath());
        } catch (FileNotFoundException e) {

            try {
                objOutStr.writeObject("ERROR: file " + _file + " not found\n");
            } catch (IOException ioException) {
                System.err.println("ERROR: I/O error in stream");
                return;
            }

            return;

        }

        // Enviar que el fichero se ha encontrado
        try {
            objOutStr.writeObject("OK");
        } catch (IOException e) {
            System.err.println("ERROR: I/O error in stream");
            return;
        }

        // Enviar nombre del fichero
        try {
            objOutStr.writeObject(_file.toString());
        } catch (IOException e) {
            System.err.println("ERROR: I/O error in stream");
            return;
        }

        // Crear flujo de salida de datos para enviar los bytes del fichero
        DataOutputStream dataOutStr = new DataOutputStream(new BufferedOutputStream(outStr));

        byte[] byteBuffer = new byte[1024]; // buffer de bytes
        int read;

        while (true) {

            // Leer bytes del fichero y guardarlos en el buffer
            try {
                if ((read = fileInStr.read(byteBuffer)) < 0)
                    break;
            } catch (IOException e) {
                System.err.println("ERROR: I/O error in file stream");
                return;
            }

            // Leer bytes del buffer y escribirlos en el flujo de salida de datos
            try {
                dataOutStr.write(byteBuffer, 0, read);
            } catch (IOException e) {
                System.err.println("ERROR: I/O error in data stream");
                return;
            }

        }

        try {
            dataOutStr.close();
        } catch (IOException e) {
            System.err.println("ERROR: I/O error in data stream");
            return;
        }

        try {
            fileInStr.close();
        } catch (IOException e) {
            System.err.println("ERROR: I/O error in file stream");
        }

    }

}
