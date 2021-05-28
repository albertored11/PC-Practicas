package com.p5_2;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.concurrent.Semaphore;

public class OyenteServidor extends Thread {

    private final Cliente _client;
    private final ObjectOutputStream _objOutStr;
    private final ObjectInputStream _objInStr;

    public OyenteServidor(Cliente client, ObjectOutputStream objOutStr, ObjectInputStream objInStr) {

        _client = client;
        _objOutStr = objOutStr;
        _objInStr = objInStr;

    }

    @Override
    public void run() {
        
        try { // TODO tratar excepciones

            while (true) {

                Mensaje m = (Mensaje)_objInStr.readObject();

                Semaphore sem = _client.getSem();

                switch (m.getTipo()) {

                    case "MENSAJE_CONFIRMACION_CONEXION":

                        System.out.println("Connection established");
                        System.out.println();

                        sem.release();

                        break;

                    case "MENSAJE_CONFIRMACION_LISTA_USUARIOS":

                        MensajeConfirmacionListaUsuarios mclu = (MensajeConfirmacionListaUsuarios)m;

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

                        sem.release();

                        break;

                    case "MENSAJE_EMITIR_FICHERO":

                        MensajeEmitirFichero mef = (MensajeEmitirFichero)m;

                        MensajePreparadoClienteServidor mpcs = new MensajePreparadoClienteServidor(_client.getUser(), mef.getDestUser(), mef.getPort());

                        _objOutStr.writeObject(mpcs);

                        (new Emisor(mef.getFile(), mef.getPort())).start();

                        break;

                    case "MENSAJE_PREPARADO_SERVIDORCLIENTE":

                        MensajePreparadoServidorCliente mpsc = (MensajePreparadoServidorCliente)m;

                        (new Receptor(mpsc.getUser(), mpsc.getPort(), sem)).start();

                        break;

                    case "MENSAJE_CONFIRMACION_CERRAR_CONEXION":

                        System.out.println("Connection closed");

                        return;

                    case "MENSAJE_USUARIO_REPETIDO":

                        MensajeUsuarioRepetido mur = (MensajeUsuarioRepetido)m;

                        System.err.println("ERROR: user " + mur.getUsername() + " already exists");

                        _client.terminate();

                        sem.release();

                        return;

                    case "MENSAJE_NO_EXISTE_FICHERO":

                        MensajeNoExisteFichero mnef = (MensajeNoExisteFichero)m;

                        System.err.println("ERROR: file " + mnef.getFilename() + " is not available");
                        System.out.println();

                        sem.release();

                        break;

                    default:

                        System.err.println("ERROR: (internal) unknown message");

                        break;

                }

            }

        } catch (Exception e) {}

    }

}
