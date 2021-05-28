package com.p5_2.usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Serializable {

    private final String _name; // nombre de usuario
    private final String _inetAddress; // dirección IP
    private final ArrayList<Fichero> _fileList; // lista de ficheros

    public Usuario(String name, String inetAddress) {

        _name = name;
        _inetAddress = inetAddress;
        _fileList = new ArrayList<>();

    }

    public void addFile(Fichero filepath) {
        _fileList.add(filepath);
    }

    public List<Fichero> getFileList() {
        return _fileList;
    }

    public String getInetAddress() {
        return _inetAddress;
    }

    @Override
    public String toString() {
        return _name;
    }

}
