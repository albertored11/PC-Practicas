package com.p5_2;

import java.io.Serializable;

public class Fichero implements Serializable {

    private final String _filepath; // ruta completa del fichero
    private final String _filename; // nombre del fichero
    private final Usuario _user; // usuario al que pertenece

    public Fichero(String filepath, String filename, Usuario user) {

        _filepath = filepath;
        _filename = filename;
        _user = user;

    }

    public String getFilepath() {
        return _filepath;
    }

    public boolean hasFilename(String filename) {
        return _filename.equals(filename);
    }

    public Usuario getUser() {
        return _user;
    }

    @Override
    public String toString() {
        return _filename;
    }

}
