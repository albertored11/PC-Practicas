package com.p5_2;

public class MensajeCerrarConexion extends MensajeDesdeCliente {

    public MensajeCerrarConexion(String origen, String destino, Usuario user) {

        super("MENSAJE_CERRAR_CONEXION", origen, destino, user);

    }

}
