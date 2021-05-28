package com.p5_2.cliente;

import com.p5_2.mensaje.*;
import com.p5_2.usuario.Fichero;
import com.p5_2.usuario.Usuario;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.concurrent.Semaphore;

public class OyenteServidor extends Thread {

    private final Cliente _client; // cliente
    private final ObjectOutputStream _objOutStr; // flujo de salida hacia el servidor
    private final ObjectInputStream _objInStr; // flujo de entrada desde el servidor

    public OyenteServidor(Cliente client, ObjectOutputStream objOutStr, ObjectInputStream objInStr) {

        _client = client;
        _objOutStr = objOutStr;
        _objInStr = objInStr;

    }

    @Override
    public void run() {

        while (true) {

            // Semáforo para controlar el flujo de stdout del cliente
            Semaphore sem = _client.getSem();

            // Leer mensaje
            Mensaje m;

            try {
                m = (Mensaje)_objInStr.readObject();
            } catch (IOException e) {
                System.err.println("ERROR: I/O error in stream");
                _client.terminate(); // decirle al cliente que termine
                sem.release(); // release a semáforo
                return;
            } catch (ClassNotFoundException e) {
                System.err.println("ERROR: (internal) wrong message class");
                _client.terminate(); // decirle al cliente que termine
                sem.release(); // release a semáforo
                return;
            }

            switch (m.getTipo()) {

                case "MENSAJE_CONFIRMACION_CONEXION":

                    System.out.println("Connection established");
                    System.out.println();

                    sem.release(); // release a semáforo

                    break;

                case "MENSAJE_CONFIRMACION_LISTA_USUARIOS":

                    MensajeConfirmacionListaUsuarios mclu = (MensajeConfirmacionListaUsuarios)m;

                    // Mostrar lista de usuarios y ficheros
                    System.out.println("~ USER LIST ~");
                    System.out.println();

                    List<Usuario> userList = mclu.getUserList();

                    for (Usuario user : userList) {

                        System.out.println(user);

                        if (user.getFileList().isEmpty())
                            System.out.println("    [no files]");

                        for (Fichero file : user.getFileList())
                            System.out.println("    " + file);

                        System.out.println();

                    }

                    sem.release(); // release a semáforo

                    break;

                case "MENSAJE_EMITIR_FICHERO":

                    MensajeEmitirFichero mef = (MensajeEmitirFichero)m;

                    // Mandar MENSAJE_PREPARADO_CLIENTESERVIDOR a OyenteCliente
                    MensajePreparadoClienteServidor mpcs = new MensajePreparadoClienteServidor(_client.getUser(), mef.getDestUser(), mef.getPort());

                    try {
                        _objOutStr.writeObject(mpcs);
                    } catch (IOException e) {
                        System.err.println("ERROR: I/O error in stream");
                        _client.terminate(); // decirle al cliente que termine
                        sem.release(); // release a semáforo
                        return;
                    }

                    // Crear hilo emisor
                    (new Emisor(mef.getFile(), mef.getPort())).start();

                    break;

                case "MENSAJE_PREPARADO_SERVIDORCLIENTE":

                    MensajePreparadoServidorCliente mpsc = (MensajePreparadoServidorCliente)m;

                    // Crear hilo receptor
                    (new Receptor(mpsc.getUser(), mpsc.getPort(), sem)).start();

                    break;

                case "MENSAJE_CONFIRMACION_CERRAR_CONEXION":

                    System.out.println("Connection closed");

                    return;

                case "MENSAJE_USUARIO_REPETIDO":

                    MensajeUsuarioRepetido mur = (MensajeUsuarioRepetido)m;

                    System.err.println("ERROR: user " + mur.getUsername() + " already exists");

                    _client.terminate(); // decirle al cliente que termine

                    sem.release(); // release a semáforo

                    return;

                case "MENSAJE_NO_EXISTE_FICHERO":

                    MensajeNoExisteFichero mnef = (MensajeNoExisteFichero)m;

                    System.err.println("ERROR: file " + mnef.getFilename() + " is not available");
                    System.out.println();

                    sem.release(); // release a semáforo

                    break;

                default:

                    System.err.println("ERROR: (internal) unknown message");

                    return;

            }

        }

    }

}
