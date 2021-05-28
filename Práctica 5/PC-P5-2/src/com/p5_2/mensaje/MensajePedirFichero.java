package com.p5_2.mensaje;

import com.p5_2.usuario.Usuario;

public class MensajePedirFichero extends MensajeDesdeCliente {

    private final String _filename; // nombre del fichero

    public MensajePedirFichero(String filename, Usuario user) {

        super("MENSAJE_PEDIR_FICHERO", user);

        _filename = filename;

    }

    public String getFile() {
        return _filename;
    }

}
