package com.p5_2;

import java.io.Serializable;

public class Fichero implements Serializable {

    private final String _text;

    public Fichero(String text) {

        _text = text;

    }

    @Override
    public String toString() {
        return _text;
    }
    
}
