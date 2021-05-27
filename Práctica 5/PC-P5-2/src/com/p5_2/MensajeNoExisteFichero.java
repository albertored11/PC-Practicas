package com.p5_2;

public class MensajeNoExisteFichero extends Mensaje {

    private final String _filename;

    public MensajeNoExisteFichero(String filename) {

        super("MENSAJE_NO_EXISTE_FICHERO");

        _filename = filename;

    }

    public String getFilename() {
        return _filename;
    }
}
