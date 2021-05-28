package com.p5_2.mensaje;

import com.p5_2.usuario.Usuario;

public abstract class MensajeDesdeCliente extends Mensaje {

    private final Usuario _user;

    public MensajeDesdeCliente(String tipo, Usuario user) {

        super(tipo);

        _user = user;

    }

    public Usuario getUser() {
        return _user;
    }

}
