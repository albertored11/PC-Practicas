package com.p5_2;

import java.util.List;

public class MensajeConfirmacionListaUsuarios extends Mensaje {

    private final List<List<Fichero>> _userFileList;

    public MensajeConfirmacionListaUsuarios(String origen, String destino, List<List<Fichero>> userFileList) {

        super("MENSAJE_CONFIRMACION_LISTA_USUARIOS", origen, destino);

        _userFileList = userFileList;

    }

    public List<List<Fichero>> getUserFileList() {
        return _userFileList;
    }
}
