package com.p5_2;

public class MensajeUsuarioRepetido extends Mensaje {

    private String _username;

    public MensajeUsuarioRepetido(String username) {

        super("MENSAJE_USUARIO_REPETIDO");

        _username = username;

    }

    public String getUsername() {
        return _username;
    }
}
