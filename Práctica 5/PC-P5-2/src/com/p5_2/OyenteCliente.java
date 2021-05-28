package com.p5_2;

import java.io.*;
import java.util.List;

public class OyenteCliente extends Thread {

    private final Servidor _server; // servidor
    private final OutputStream _outStr; // flujo de salida hacia el cliente
    private final InputStream _inStr; // flujo de entrada desde el cliente

    public OyenteCliente(Servidor server, OutputStream outStr, InputStream inStr) {

        _server = server;
        _outStr = outStr;
        _inStr = inStr;

    }

    @Override
    public void run() {

        // Obtener flujos de entrada y de salida para objetos
        ObjectOutputStream objOutStr;
        ObjectInputStream objInStr;

        try {
            objOutStr = new ObjectOutputStream(_outStr);
            objInStr = new ObjectInputStream(_inStr);
        } catch (IOException e) {
            System.err.println("ERROR: I/O error in stream");
            return;
        }

        Usuario user = null;

        while (true) {

            // Leer mensaje
            Mensaje m;

            try {
                m = (Mensaje)objInStr.readObject();
            } catch (IOException e) {
                System.err.println("ERROR: I/O error in stream");
                return;
            } catch (ClassNotFoundException e) {
                System.err.println("ERROR: (internal) wrong message class");
                return;
            }

            switch (m.getTipo()) {

                case "MENSAJE_CONEXION":

                    MensajeConexion mc = (MensajeConexion)m;
                    user = mc.getUser();

                    // Si el usuario ya existe, mandar MENSAJE_USUARIO_REPETIDO a OyenteServidor
                    if (_server.hasUser(user.toString())) {

                        Mensaje mur = new MensajeUsuarioRepetido(user.toString());

                        try {
                            objOutStr.writeObject(mur);
                        } catch (IOException e) {
                            System.err.println("ERROR: I/O error in stream");
                            return;
                        }

                        return;

                    }

                    // Añadir usuario a las tablas del servidor
                    if (!_server.putInUserStreamMap(user, objOutStr))
                        return;

                    if (!_server.addToUserList(user))
                        return;

                    // Mandar MENSAJE_CONFIRMACION_CONEXION a OyenteServidor
                    Mensaje mcc = new MensajeConfirmacionConexion();

                    try {
                        objOutStr.writeObject(mcc);
                    } catch (IOException e) {
                        System.err.println("ERROR: I/O error in stream");
                        return;
                    }

                    break;

                case "MENSAJE_LISTA_USUARIOS":

                    List<Usuario> userList = _server.getUserList();

                    if (userList == null)
                        return;

                    // Mandar MENSAJE_CONFIRMACION_LISTA_USUARIOS a OyenteServidor
                    Mensaje mclu = new MensajeConfirmacionListaUsuarios(userList);

                    // Si no hacemos un reset en el flujo de salida para objetos, obtendremos siempre la misma lista
                    try {
                        objOutStr.reset();
                    } catch (IOException e) {
                        System.err.println("ERROR: I/O error in stream");
                        return;
                    }

                    try {
                        objOutStr.writeObject(mclu);
                    } catch (IOException e) {
                        System.err.println("ERROR: I/O error in stream");
                        return;
                    }

                    break;

                case "MENSAJE_CERRAR_CONEXION":

                    MensajeCerrarConexion mcco = (MensajeCerrarConexion)m;

                    // Eliminar al usuario de las tablas del servidor
                    if (!_server.removeFromUserLists(mcco.getUser()))
                        return;

                    // Mandar MENSAJE_CONFIRMACION_CERRAR_CONEXION a OyenteServidor
                    Mensaje mccc = new MensajeConfirmacionCerrarConexion();

                    try {
                        objOutStr.writeObject(mccc);
                    } catch (IOException e) {
                        System.err.println("ERROR: I/O error in stream");
                        return;
                    }

                    return;

                case "MENSAJE_PEDIR_FICHERO":

                    MensajePedirFichero mpf = (MensajePedirFichero)m;

                    String filename = mpf.getFile();

                    Fichero file = _server.getFileFromFilename(filename);

                    // Si el fichero no existe, mandar MENSAJE_CONFIRMACION_CONEXION a OyenteServidor
                    if (file == null) {

                        MensajeNoExisteFichero mnef = new MensajeNoExisteFichero(filename);

                        try {
                            objOutStr.writeObject(mnef);
                        } catch (IOException e) {
                            System.err.println("ERROR: I/O error in stream");
                            return;
                        }

                        break;

                    }

                    // Mandar MENSAJE_EMITIR_FICHERO a OyenteServidor
                    ObjectOutputStream objOutStr1 = _server.getObjectOutputStream(file.getUser());

                    if (objOutStr1 == null)
                        return;

                    MensajeEmitirFichero mef = new MensajeEmitirFichero(file, mpf.getUser(), _server.getAndIncrementNextPort());

                    try {
                        objOutStr1.writeObject(mef);
                    } catch (IOException e) {
                        System.err.println("ERROR: I/O error in stream");
                        return;
                    }

                    break;

                case "MENSAJE_PREPARADO_CLIENTESERVIDOR":

                    if (user == null) {
                        System.err.println("ERROR: (internal) user info not found");
                        break;
                    }

                    MensajePreparadoClienteServidor mpcs = (MensajePreparadoClienteServidor)m;

                    // Obtener referencia original del usuario receptor (si no se hace, aparece una referencia nueva)
                    Usuario destUser = _server.getOriginalUser(mpcs.getDestUser());

                    if (destUser == null)
                        return;

                    // Mandar MENSAJE_PREPARADO_SERVIDORCLIENTE a OyenteServidor
                    ObjectOutputStream objOutStr2 = _server.getObjectOutputStream(destUser);

                    if (objOutStr2 == null)
                        return;

                    Mensaje mpsc = new MensajePreparadoServidorCliente(user, mpcs.getPort());

                    try {
                        objOutStr2.writeObject(mpsc);
                    } catch (IOException e) {
                        System.err.println("ERROR: I/O error in stream");
                        return;
                    }

                    break;

                default:

                    System.err.println("ERROR: (internal) unknown message");

                    return;

            }

        }

    }

}
