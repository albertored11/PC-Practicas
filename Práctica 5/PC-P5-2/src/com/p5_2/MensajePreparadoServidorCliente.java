package com.p5_2;

public class MensajePreparadoServidorCliente extends Mensaje {

    private final Usuario _user;
    private final int _port;

    public MensajePreparadoServidorCliente(String origen, String destino, Usuario user, int port) {

        super("MENSAJE_PREPARADO_SERVIDORCLIENTE", origen, destino);

        _user = user;
        _port = port;

    }

    public Usuario getUser() {
        return _user;
    }

    public int getPort() {
        return _port;
    }

}
