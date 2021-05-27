package com.p5_2;

public class MensajeEmitirFichero extends Mensaje {

    private final String _file;
    private final Usuario _destUser;

    public MensajeEmitirFichero(String file, Usuario destUser) {

        super("MENSAJE_EMITIR_FICHERO");

        _file = file;
        _destUser = destUser;

    }

    public String getFile() {
        return _file;
    }

    public Usuario getDestUser() {
        return _destUser;
    }

}
