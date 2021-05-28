package com.p5_2.mensaje;

public class MensajeUsuarioRepetido extends Mensaje {

    private final String _username; // nombre de usuario

    public MensajeUsuarioRepetido(String username) {

        super("MENSAJE_USUARIO_REPETIDO");

        _username = username;

    }

    public String getUsername() {
        return _username;
    }
}
