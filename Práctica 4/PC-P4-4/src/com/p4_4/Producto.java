package com.p4_4;

public class Producto {

    private final Integer _id;

    Producto(int id) {

        _id = id;

    }

    @Override
    public String toString() {

        return _id.toString();

    }

}
