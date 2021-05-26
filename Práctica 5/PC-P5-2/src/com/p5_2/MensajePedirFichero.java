package com.p5_2;

public class MensajePedirFichero extends MensajeDesdeCliente {

    private final Fichero _file;

    public MensajePedirFichero(String origen, String destino, Fichero filename, Usuario user) {

        super("MENSAJE_PEDIR_FICHERO", origen, destino, user);

        _file = filename;

    }

    public Fichero getFile() {
        return _file;
    }

}
