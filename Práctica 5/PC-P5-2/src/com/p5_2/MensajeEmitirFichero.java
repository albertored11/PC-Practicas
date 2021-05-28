package com.p5_2;

public class MensajeEmitirFichero extends Mensaje {

    private final Fichero _file; // fichero a emitir
    private final Usuario _destUser; // usuario receptor
    private final int _port; // puerto del socket que se establecerá entre emisor y receptor

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
