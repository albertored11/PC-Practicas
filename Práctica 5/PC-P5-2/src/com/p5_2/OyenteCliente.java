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

                        Stream stream = new Stream(objOutStr, objInStr);

                        _server.getSemUserStreamMap().acquire();
                        _server.putInUserStreamMap(user, stream);
                        _server.getSemUserStreamMap().release();

                        _server.addToUserList(user);

                        Mensaje mcc = new MensajeConfirmacionConexion();

                        objOutStr.writeObject(mcc);

                        break;

                    case "MENSAJE_LISTA_USUARIOS":

                        Mensaje mclu = new MensajeConfirmacionListaUsuarios(_server.getUserList());

                        objOutStr.writeObject(mclu);

                        break;

                    case "MENSAJE_CERRAR_CONEXION":

                        MensajeCerrarConexion mcco = (MensajeCerrarConexion) m;

                        _server.removeFromUserLists(mcco.getUser());

                        Mensaje mccc = new MensajeConfirmacionCerrarConexion();

                        objOutStr.writeObject(mccc);

                        break;

                    case "MENSAJE_PEDIR_FICHERO":

                        MensajePedirFichero mpf = (MensajePedirFichero)m;

                        String file = mpf.getFile();

                        Usuario user1 = _server.getFileUser(file);

                        _server.getSemUserStreamMap().acquire();
                        ObjectOutputStream objOutStr1 = _server.getObjectOutputStream(user1);
                        _server.getSemUserStreamMap().release();

                        MensajeEmitirFichero mef = new MensajeEmitirFichero(file, mpf.getUser());

                        objOutStr1.writeObject(mef);

                        break;

                    case "MENSAJE_PREPARADO_CLIENTESERVIDOR":

                        if (user == null) {
                            System.err.println("INTERNAL ERROR: user info not found");
                            break;
                        }

                        MensajePreparadoClienteServidor mpcs = (MensajePreparadoClienteServidor)m;

                        Usuario destUser = _server.getOriginalUser(mpcs.getDestUser());

                        _server.getSemUserStreamMap().acquire();
                        ObjectOutputStream objOutStr2 = _server.getObjectOutputStream(destUser);
                        _server.getSemUserStreamMap().release();

                        // TODO: MENSAJE_PREPARADO_SERVIDORCLIENTE debería recibirlo el otro cliente

                        Mensaje mpsc = new MensajePreparadoServidorCliente(user, mpcs.getPort());

                        objOutStr2.writeObject(mpsc);

                        break;

                    default:

                        System.err.println("INTERNAL ERROR: unknown message");

                        break;

                }

            }

        } catch (Exception e) {}

        // ...

//        PrintWriter writer = new PrintWriter(_outStr, true);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(_inStr));
//        String filepath = null;
//
//        try {
//
//            filepath = reader.readLine();
//
//            File file = new File(filepath);
//            FileReader fileReader = new FileReader(file);
//            BufferedReader bufReader = new BufferedReader(fileReader);
//
//            System.out.println("Reading from " + filepath + "...");
//
//            StringBuilder textBuilder = new StringBuilder();
//
//            String text = bufReader.readLine();
//
//            while (text != null) {
//                textBuilder.append(text);
//                textBuilder.append('\n');
//                text = bufReader.readLine();
//            }
//
//            System.out.println("Sending text from " + filepath + "...");
//
//            writer.println(textBuilder);
//
//            _inStr.close();
//            _outStr.close();
//            bufReader.close();
//            reader.close();
//            writer.close();
//
//        } catch (FileNotFoundException e) {
//            System.err.println("ERROR: File " + filepath + " not found");
//            writer.println("FILE_NOT_FOUND");
//        }
//        catch (IOException e) {
//            System.err.println("ERROR: IO exception");
//        }

    }

}
