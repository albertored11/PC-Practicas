package com.p5_2;

import java.io.Serializable;

public abstract class Mensaje implements Serializable {

    private final String _tipo;

    public Mensaje(String tipo) {

        _tipo = tipo;

    }

    public String getTipo() {
        return _tipo;
    }

}
