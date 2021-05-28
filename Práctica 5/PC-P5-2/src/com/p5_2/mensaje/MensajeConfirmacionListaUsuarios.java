package com.p5_2.mensaje;

import com.p5_2.usuario.Usuario;

import java.util.List;

public class MensajeConfirmacionListaUsuarios extends Mensaje {

    private final List<Usuario> _userList;

    public MensajeConfirmacionListaUsuarios(List<Usuario> userList) {

        super("MENSAJE_CONFIRMACION_LISTA_USUARIOS");

        _userList = userList;

    }

    public List<Usuario> getUserList() {
        return _userList;
    }

}
