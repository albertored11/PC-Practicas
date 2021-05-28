package com.p5_2;

public class MensajeEmitirFichero extends Mensaje {

    private final Fichero _file;
    private final Usuario _destUser;
    private final int _port;

    public MensajeEmitirFichero(Fichero file, Usuario destUser, int port) {

        super("MENSAJE_EMITIR_FICHERO");

        _file = file;
        _destUser = destUser;
        _port = port;

    }

    public Fichero getFile() {
        return _file;
    }

    public Usuario getDestUser() {
        return _destUser;
    }

    public int getPort() {
        return _port;
    }

}
