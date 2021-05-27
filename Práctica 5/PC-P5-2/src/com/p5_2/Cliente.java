package com.p5_2;

import java.util.concurrent.Semaphore;

public class Cliente {

    private final Usuario _user;
    private final Stream _serverStream;
    private final Semaphore _sem;

    public Cliente(Usuario user, Stream serverStream) {

        _user = user;
        _serverStream = serverStream;
        _sem = new Semaphore(0);

    }

    public Usuario getUser() {
        return _user;
    }

    public Stream getServerStream() {
        return _serverStream;
    }

    public Semaphore getSem() {
        return _sem;
    }

}
