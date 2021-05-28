package com.p5_2.mensaje;

import com.p5_2.usuario.Usuario;

public class MensajeListaUsuarios extends MensajeDesdeCliente {

    public MensajeListaUsuarios(Usuario user) {

        super("MENSAJE_LISTA_USUARIOS", user);

    }

}
