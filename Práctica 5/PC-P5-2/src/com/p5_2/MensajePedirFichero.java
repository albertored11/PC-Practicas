package com.p5_2;

public class MensajePedirFichero extends MensajeDesdeCliente {

    private final String _file;

    public MensajePedirFichero(String filename, Usuario user) {

        super("MENSAJE_PEDIR_FICHERO", user);

        _file = filename;

    }

    public String getFile() {
        return _file;
    }

}
