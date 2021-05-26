package com.p5_2;

public class Cliente {

    private Usuario _user;
    private Stream _serverStream;

    public Cliente(Usuario user, Stream serverStream) {

        _user = user;
        _serverStream = serverStream;

    }

    public Usuario getUser() {
        return _user;
    }

    public Stream getServerStream() {
        return _serverStream;
    }
}
