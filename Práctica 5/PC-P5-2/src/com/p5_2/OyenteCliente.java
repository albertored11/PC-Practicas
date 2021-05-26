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

                        Stream stream = new Stream(_outStr, _inStr);

                        _server.putInUserStreamMap(mc.getUser(), stream);
                        _server.addToUserList(user, user.getFileList());

                        Mensaje mcc = new MensajeConfirmacionConexion(mc.getDestino(), mc.getOrigen());

                        objOutStr.writeObject(mcc);

                        break;

                    case "MENSAJE_LISTA_USUARIOS":

                        Mensaje mclu = new MensajeConfirmacionListaUsuarios(
                                m.getDestino(),
                                m.getOrigen(),
                                _server.getUserList());

                        objOutStr.writeObject(mclu);

                        break;

                    case "MENSAJE_CERRAR_CONEXION":

                        MensajeCerrarConexion mcco = (MensajeCerrarConexion) m;

                        _server.removeFromUserLists(mcco.getUser());

                        Mensaje mccc = new MensajeConfirmacionCerrarConexion(mcco.getDestino(), mcco.getOrigen());

                        objOutStr.writeObject(mccc);

                        break;

                    case "MENSAJE_PEDIR_FICHERO":

                        MensajePedirFichero mpf = (MensajePedirFichero) m;

                        Fichero file = mpf.getFile();

                        Usuario user1 = _server.getFileUser(file);

                        ObjectOutputStream objOutStr1 = new ObjectOutputStream(_server.getOutputStream(user1));

                        Mensaje mef = new MensajeEmitirFichero(mpf.getDestino(), mpf.getOrigen(), file);

                        objOutStr1.writeObject(mef);

                        break;

                    case "MENSAJE_PREPARADO_CLIENTESERVIDOR":

                        if (user == null) {
                            System.err.println("INTERNAL ERROR: user info not found");
                            break;
                        }

                        MensajePreparadoClienteServidor mpcs = (MensajePreparadoClienteServidor) m;

                        ObjectOutputStream objOutStr2 = new ObjectOutputStream(_server.getOutputStream(mpcs.getUser()));

                        Mensaje mpsc = new MensajePreparadoServidorCliente(mpcs.getDestino(), mpcs.getOrigen(), user, mpcs.getPort());

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
