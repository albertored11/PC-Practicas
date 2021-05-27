package com.p5_2;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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

                        System.out.println("Conexión establecida");
                        System.out.println();

                        break;

                    case "MENSAJE_CONFIRMACION_LISTA_USUARIOS":

                        MensajeConfirmacionListaUsuarios mclu = (MensajeConfirmacionListaUsuarios)m;

                        System.out.println("LISTA DE USUARIOS:");

                        List<Usuario> userList = mclu.getUserList();

                        for (Usuario user : userList)
                            System.out.println(user);

                        System.out.println();

                        break;

                    case "MENSAJE_EMITIR_FICHERO":

                        MensajeEmitirFichero mef = (MensajeEmitirFichero)m;

                        System.out.println("MENSAJE_EMITIR_FICHERO recibido!");

                        MensajePreparadoClienteServidor mpcs = new MensajePreparadoClienteServidor(mef.getDestino(), mef.getOrigen(), _client.getUser(), mef.getDestUser());

                        _objOutStr.writeObject(mpcs);

                        (new Emisor(mef.getFile(), mpcs.getPort())).start();

                        break;

                    case "MENSAJE_PREPARADO_SERVIDORCLIENTE":

                        MensajePreparadoServidorCliente mpsc = (MensajePreparadoServidorCliente)m;

                        System.out.println("MENSAJE_PREPARADO_SERVIDORCLIENTE recibido!");

                        (new Receptor(mpsc.getUser(), mpsc.getPort())).start();

                        break;

                    case "MENSAJE_CONFIRMACION_CERRAR_CONEXION":

                        System.out.println("Conexión terminada");
                        System.out.println();

                        break;

                }

                sem.release();

            }

        } catch (Exception e) {}

    }

}
