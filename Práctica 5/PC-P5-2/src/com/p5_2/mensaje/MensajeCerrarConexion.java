package com.p5_2.mensaje;

import com.p5_2.usuario.Usuario;

public class MensajeCerrarConexion extends MensajeDesdeCliente {

    public MensajeCerrarConexion(Usuario user) {

        super("MENSAJE_CERRAR_CONEXION", user);

    }

}
