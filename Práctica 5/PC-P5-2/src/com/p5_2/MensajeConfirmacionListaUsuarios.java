package com.p5_2;

import java.util.List;

public class MensajeConfirmacionListaUsuarios extends Mensaje {

    private final List<Usuario> _userList;

    public MensajeConfirmacionListaUsuarios(String origen, String destino, List<Usuario> userList) {

        super("MENSAJE_CONFIRMACION_LISTA_USUARIOS", origen, destino);

        _userList = userList;

    }

    public List<Usuario> getUserList() {
        return _userList;
    }
}
