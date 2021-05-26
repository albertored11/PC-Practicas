package com.p5_2;

public class MensajePreparadoClienteServidor extends MensajeDesdeCliente {

    private int _port;

    public MensajePreparadoClienteServidor(String origen, String destino, Usuario user) {

        super("MENSAJE_PREPARADO_CLIENTESERVIDOR", origen, destino, user);

        _port = 30000; // TODO generar puerto

    }

    public int getPort() {
        return _port;
    }

}
