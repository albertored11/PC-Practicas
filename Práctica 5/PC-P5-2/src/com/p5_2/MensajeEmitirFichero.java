package com.p5_2;

public class MensajeEmitirFichero extends Mensaje {

    private Fichero _file;

    public MensajeEmitirFichero(String origen, String destino, Fichero file) {

        super("MENSAJE_EMITIR_FICHERO", origen, destino);

        _file = file;

    }

    public Fichero getFile() {
        return _file;
    }
}
