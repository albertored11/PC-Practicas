package com.p5_2;

public class MensajePreparadoClienteServidor extends MensajeDesdeCliente {

    private final int _port;
    private final Usuario _destUser;

    public MensajePreparadoClienteServidor(Usuario user, Usuario destUser, int port) {

        super("MENSAJE_PREPARADO_CLIENTESERVIDOR", user);

        _destUser = destUser;
        _port = port;

    }

    public int getPort() {
        return _port;
    }

    public Usuario getDestUser() {
        return _destUser;
    }

}
