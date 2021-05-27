package com.p5_2;

public class MensajeEmitirFichero extends Mensaje {

    private final Fichero _file;
    private final Usuario _destUser;

    public MensajeEmitirFichero(String origen, String destino, Fichero file, Usuario destUser) {

        super("MENSAJE_EMITIR_FICHERO", origen, destino);

        _file = file;
        _destUser = destUser;

    }

    public Fichero getFile() {
        return _file;
    }

    public Usuario getDestUser() {
        return _destUser;
    }

}
