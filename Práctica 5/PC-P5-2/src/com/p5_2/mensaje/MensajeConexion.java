package com.p5_2.mensaje;

import com.p5_2.usuario.Usuario;

public class MensajeConexion extends MensajeDesdeCliente {

    public MensajeConexion(Usuario user) {

        super("MENSAJE_CONEXION", user);

    }

}
