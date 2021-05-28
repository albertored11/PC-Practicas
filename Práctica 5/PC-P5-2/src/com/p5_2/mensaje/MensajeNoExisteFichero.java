package com.p5_2.mensaje;

public class MensajeNoExisteFichero extends Mensaje {

    private final String _filename; // nombre del fichero

    public MensajeNoExisteFichero(String filename) {

        super("MENSAJE_NO_EXISTE_FICHERO");

        _filename = filename;

    }

    public String getFilename() {
        return _filename;
    }

}
