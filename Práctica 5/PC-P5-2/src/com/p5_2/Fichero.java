package com.p5_2;

import java.io.Serializable;

public class Fichero implements Serializable {

    private String _name;

    public Fichero(String name) {

        _name = name;

    }

    public String getName() {
        return _name;
    }
}
