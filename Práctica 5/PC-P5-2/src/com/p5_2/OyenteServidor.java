package com.p5_2;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;

public class OyenteServidor extends Thread {

    private final Cliente _client;
    private final OutputStream _outStr;
    private final InputStream _inStr;

    public OyenteServidor(Cliente client, OutputStream outStr, InputStream inStr) {

        _client = client;
        _outStr = outStr;
        _inStr = inStr;

    }

    @Override
    public void run() {
        
        try { // TODO tratar excepciones

            ObjectInputStream objInStr = new ObjectInputStream(_inStr);
            ObjectOutputStream objOutStr = new ObjectOutputStream(_outStr);

            while (true) {

                Mensaje m = (Mensaje)objInStr.readObject();

                switch (m.getTipo()) {

                    case "MENSAJE_CONFIRMACION_CONEXION":

                        System.out.println("Conexión establecida");

                        break;

                    case "MENSAJE_CONFIRMACION_LISTA_USUARIOS":

                        MensajeConfirmacionListaUsuarios mclu = (MensajeConfirmacionListaUsuarios)m;

                        System.out.println("LISTA DE USUARIOS:");

                        List<Usuario> userList = mclu.getUserList();

                        for (Usuario user : userList)
                            System.out.println(user);

                        break;

                    case "MENSAJE_EMITIR_FICHERO":

                        MensajeEmitirFichero mef = (MensajeEmitirFichero)m;

                        MensajePreparadoClienteServidor mpcs = new MensajePreparadoClienteServidor(mef.getDestino(), mef.getOrigen(), _client.getUser());

                        objOutStr.writeObject(mpcs);

                        (new Emisor(mef.getFile(), mpcs.getPort())).start();

                        break;

                    case "MENSAJE_PREPARADO_SERVIDORCLIENTE":

                        MensajePreparadoServidorCliente mpsc = (MensajePreparadoServidorCliente)m;

                        (new Receptor(mpsc.getUser(), mpsc.getPort())).start();

                        break;

                    case "MENSAJE_CONFIRMACION_CERRAR_CONEXION":

                        System.out.println("Conexión terminada");

                        break;

                }

            }

        } catch (Exception e) {}

    }

}
