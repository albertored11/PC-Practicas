package com.p5_2;

import java.io.Serializable;

public abstract class Mensaje implements Serializable {

    private String _tipo;
    private String _origen;
    private String _destino;

    public Mensaje(String tipo, String origen, String destino) {

        _tipo = tipo;
        _origen = origen;
        _destino = destino;

    }

    public String getTipo() {
        return _tipo;
    }

    public String getOrigen() {
        return _origen;
    }

    public String getDestino() {
        return _destino;
    }

}
