package com.p4_2;

public class Producto {

    private Integer _id;

    Producto(int id) {

        _id = id;

    }

    @Override
    public String toString() {

        return _id.toString();

    }

}
