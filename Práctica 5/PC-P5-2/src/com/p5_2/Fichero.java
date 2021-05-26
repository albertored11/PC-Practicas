package com.p5_2;

import java.io.Serializable;

public class Fichero implements Serializable {

    private final String _filepath;

    public Fichero(String filepath) {

        _filepath = filepath;

    }

    public String getFilePath() {
        return _filepath;
    }

}
