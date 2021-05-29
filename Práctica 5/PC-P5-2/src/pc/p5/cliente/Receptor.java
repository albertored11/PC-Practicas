package pc.p5.cliente;

import pc.p5.usuario.Usuario;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class Receptor extends Thread {

    private final Usuario _emisor; // usuario emisor
    private final int _port; // puerto del socket que se establecerá entre emisor y receptor
    private final Semaphore _sem; // semáforo para controlar el flujo de stdout del cliente

    public Receptor(Usuario emisor, int port, Semaphore sem) {

        _emisor = emisor;
        _port = port;
        _sem = sem;

    }

    @Override
    public void run() {

        // Crear socket con receptor
        Socket sock;

        try {
            sock = new Socket(_emisor.getInetAddress(), _port);
        } catch (IOException e) {
            System.err.println("ERROR: server not found");
            System.out.println();
            _sem.release(); // release a semáforo
            return;
        }

        // Obtener flujo de entrada
        InputStream inStr;

        try {
            inStr = sock.getInputStream();
        } catch (IOException e) {
            System.err.println("ERROR: I/O error in socket");
            System.out.println();
            _sem.release(); // release a semáforo
            return;
        }

        // Obtener flujo de entrada para objetos
        ObjectInputStream objInStr;

        try {
            objInStr = new ObjectInputStream(inStr);
        } catch (IOException e) {
            System.err.println("ERROR: I/O error in stream");
            System.out.println();
            _sem.release(); // release a semáforo
            return;
        }

        // Leer si el fichero se ha encontrado
        String fileFound;

        try {

            fileFound = (String)objInStr.readObject();

            if (!fileFound.equals("OK")) {
                System.out.println(fileFound);
                _sem.release();
                return;
            }

        } catch (IOException e) {
            System.err.println("ERROR: I/O error in stream");
            System.out.println();
            _sem.release(); // release a semáforo
            return;
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: (internal) wrong message class");
            System.out.println();
            _sem.release(); // release a semáforo
            return;
        }

        // Leer nombre de fichero del flujo
        String filename;

        try {
            filename = (String)objInStr.readObject();
        } catch (IOException e) {
            System.err.println("ERROR: I/O error in stream");
            System.out.println();
            _sem.release(); // release a semáforo
            return;
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: (internal) wrong message class");
            System.out.println();
            _sem.release(); // release a semáforo
            return;
        }

        // Crear flujo de salida para escribir en el fichero
        FileOutputStream fileOutStr;

        try {
            fileOutStr = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: error writing in filesystem");
            System.out.println();
            _sem.release();
            return;
        }

        // Crear flujo de entrada de datos para recibir los bytes del fichero
        DataInputStream dataInStr = new DataInputStream(new BufferedInputStream(inStr));

        byte[] byteBuffer = new byte[1024]; // buffer de bytes
        int read;

        while (true) {

            // Leer bytes del flujo de entrada de datos y guardarlos en el buffer
            try {
                if ((read = dataInStr.read(byteBuffer)) < 0)
                    break;
            } catch (IOException e) {
                System.err.println("ERROR: I/O error in file stream");
                System.out.println();
                _sem.release();
                return;
            }

            // Leer bytes del buffer y escribirlos en el fichero
            try {
                fileOutStr.write(byteBuffer, 0, read);
            } catch (IOException e) {
                System.err.println("ERROR: I/O error in data stream");
                System.out.println();
                _sem.release();
                return;
            }

        }

        try {
            dataInStr.close();
        } catch (IOException e) {
            System.err.println("ERROR: I/O error in data stream");
            System.out.println();
            _sem.release();
            return;
        }

        try {
            fileOutStr.close();
        } catch (IOException e) {
            System.err.println("ERROR: I/O error in file stream");
            System.out.println();
            _sem.release();
            return;
        }

        System.out.println("File saved to " + System.getProperty("user.dir") + "/" + filename);
        System.out.println();

        _sem.release(); // release a semáforo

    }

}
