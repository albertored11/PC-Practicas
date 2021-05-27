package com.p5_2;

public class MensajePreparadoClienteServidor extends MensajeDesdeCliente {

    private final int _port;
    private final Usuario _destUser;

    public MensajePreparadoClienteServidor(String origen, String destino, Usuario user, Usuario destUser) {

        super("MENSAJE_PREPARADO_CLIENTESERVIDOR", origen, destino, user);

        _port = 30000; // TODO generar puerto
        _destUser = destUser;

    }

    public int getPort() {
        return _port;
    }

    public Usuario getDestUser() {
        return _destUser;
    }

}
