package com.p5_2;

public abstract class MensajeDesdeCliente extends Mensaje {

    private final String _username;

    public MensajeDesdeCliente(String tipo, String origen, String destino, String username) {

        super(tipo, origen, destino);

        _username = username;

    }

    public String getUsername() {
        return _username;
    }
}
