package com.p5_2.mensaje;

import com.p5_2.usuario.Usuario;

public class MensajePreparadoServidorCliente extends Mensaje {

    private final Usuario _user; // usuario receptor
    private final int _port; // puerto del socket entre emisor y receptor

    public MensajePreparadoServidorCliente(Usuario user, int port) {

        super("MENSAJE_PREPARADO_SERVIDORCLIENTE");

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
