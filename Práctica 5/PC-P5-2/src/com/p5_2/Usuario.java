package com.p5_2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Serializable {

//    private int _id;
    private String _name;
    private String _inetAddress;
    private ArrayList<Fichero> _fileList;

    Usuario(/*int id, */String name, String inetAddress) {

//        _id = id;
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
