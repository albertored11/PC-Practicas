package com.p5_2;

public class MensajeCerrarConexion extends MensajeDesdeCliente {

    private final int _userId;

    public MensajeCerrarConexion(String origen, String destino, String username, int userId) {

        super("MENSAJE_CERRAR_CONEXION", origen, destino, username);

        _userId = userId;

    }

    public int getUserId() {
        return _userId;
    }
}
