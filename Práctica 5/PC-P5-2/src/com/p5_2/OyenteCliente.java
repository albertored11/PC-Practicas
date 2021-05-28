package com.p5_2;

import java.io.*;

public class OyenteCliente extends Thread {

    private final Servidor _server;
    private final OutputStream _outStr;
    private final InputStream _inStr;

    public OyenteCliente(Servidor server, OutputStream outStr, InputStream inStr) {

        _server = server;
        _outStr = outStr;
        _inStr = inStr;

    }

    @Override
    public void run() {

        try { // TODO tratar excepciones

            ObjectInputStream objInStr = new ObjectInputStream(_inStr);
            ObjectOutputStream objOutStr = new ObjectOutputStream(_outStr);

            Usuario user = null;

            while (true) {

                Mensaje m = (Mensaje)objInStr.readObject();

                switch (m.getTipo()) {

                    case "MENSAJE_CONEXION":

                        MensajeConexion mc = (MensajeConexion)m;
                        user = mc.getUser();

                        if (_server.hasUser(user.toString())) {

                            Mensaje mur = new MensajeUsuarioRepetido(user.toString());

                            objOutStr.writeObject(mur);

                            break;

                        }

                        Stream stream = new Stream(objOutStr, objInStr);

                        _server.putInUserStreamMap(user, stream);

                        _server.addToUserList(user);

                        Mensaje mcc = new MensajeConfirmacionConexion();

                        objOutStr.writeObject(mcc);

                        break;

                    case "MENSAJE_LISTA_USUARIOS":

                        Mensaje mclu = new MensajeConfirmacionListaUsuarios(_server.getUserList());

                        objOutStr.reset();
                        objOutStr.writeObject(mclu);

                        break;

                    case "MENSAJE_CERRAR_CONEXION":

                        MensajeCerrarConexion mcco = (MensajeCerrarConexion)m;

                        _server.removeFromUserLists(mcco.getUser());

                        Mensaje mccc = new MensajeConfirmacionCerrarConexion();

                        objOutStr.writeObject(mccc);

                        break;

                    case "MENSAJE_PEDIR_FICHERO":

                        MensajePedirFichero mpf = (MensajePedirFichero)m;

                        String filename = mpf.getFile();

                        Fichero file = _server.getFileFromFilename(filename);

                        if (file == null) {

                            MensajeNoExisteFichero mnef = new MensajeNoExisteFichero(filename);

                            objOutStr.writeObject(mnef);

                            break;

                        }

                        ObjectOutputStream objOutStr1 = _server.getObjectOutputStream(file.getUser());

                        MensajeEmitirFichero mef = new MensajeEmitirFichero(file, mpf.getUser(), _server.getAndIncrementNextPort());

                        objOutStr1.writeObject(mef);

                        break;

                    case "MENSAJE_PREPARADO_CLIENTESERVIDOR":

                        if (user == null) {
                            System.err.println("ERROR: (internal) user info not found");
                            break;
                        }

                        MensajePreparadoClienteServidor mpcs = (MensajePreparadoClienteServidor)m;

                        Usuario destUser = _server.getOriginalUser(mpcs.getDestUser());

                        ObjectOutputStream objOutStr2 = _server.getObjectOutputStream(destUser);

                        Mensaje mpsc = new MensajePreparadoServidorCliente(user, mpcs.getPort());

                        objOutStr2.writeObject(mpsc);

                        break;

                    default:

                        System.err.println("ERROR: (internal) unknown message");

                        break;

                }

            }

        } catch (Exception e) {}

    }

}
