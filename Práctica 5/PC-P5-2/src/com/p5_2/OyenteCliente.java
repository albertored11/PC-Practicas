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

        ObjectInputStream objInStr = new ObjectInputStream(_inStr);
        ObjectOutputStream objOutStr = new ObjectOutputStream(_outStr);

        while (true) {

            Mensaje m = (Mensaje)objInStr.readObject();

            switch (m.getTipo()) {

                case "MENSAJE_CONEXION":

                    MensajeConexion mc = (MensajeConexion)m;

                    _server.putInUserStreamMap(mc.getUsername(), mc.getStream());
                    _server.putInUserFileMap(mc.getUsername(), mc.getFileList());

                    Mensaje mcc = new MensajeConfirmacionConexion(mc.getDestino(), mc.getOrigen());

                    objOutStr.writeObject(mcc);

                    break;

                case "MENSAJE_LISTA_USUARIOS":

                    Mensaje mclu = new MensajeConfirmacionListaUsuarios(
                            m.getDestino(),
                            m.getOrigen(),
                            _server.getUserFileMap());

                    objOutStr.writeObject(mclu);

                    break;

                case "MENSAJE_CERRAR_CONEXION":



                    break;

            }

        }

        // ...

        PrintWriter writer = new PrintWriter(_outStr, true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(_inStr));
        String filepath = null;

        try {

            filepath = reader.readLine();

            File file = new File(filepath);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(fileReader);

            System.out.println("Reading from " + filepath + "...");

            StringBuilder textBuilder = new StringBuilder();

            String text = bufReader.readLine();

            while (text != null) {
                textBuilder.append(text);
                textBuilder.append('\n');
                text = bufReader.readLine();
            }

            System.out.println("Sending text from " + filepath + "...");

            writer.println(textBuilder);

            _inStr.close();
            _outStr.close();
            bufReader.close();
            reader.close();
            writer.close();

        } catch (FileNotFoundException e) {
            System.err.println("ERROR: File " + filepath + " not found");
            writer.println("FILE_NOT_FOUND");
        }
        catch (IOException e) {
            System.err.println("ERROR: IO exception");
        }

    }

}
