package com.p5_2;

import java.util.List;

public class MensajeConexion extends MensajeDesdeCliente {

    public MensajeConexion(String origen, String destino, Usuario user) {

        super("MENSAJE_CONEXION", origen, destino, user);

    }

}
