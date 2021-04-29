package com.p4_2;

import java.util.LinkedList;

public class AlmacenMultiple implements Almacen {

    private final int _capacidad;
    private final LinkedList<Producto> _colaProductos;

    AlmacenMultiple(int capacidad) {

        _capacidad = capacidad;
        _colaProductos = new LinkedList<>();

    }

    @Override
    public synchronized void almacenar(Producto producto) {

        while (_colaProductos.size() == _capacidad) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }

        _colaProductos.addLast(producto);

        System.out.println("Objeto con ID " + producto + " almacenado");

        notifyAll();

    }

    @Override
    public synchronized void extraer() {

        while (_colaProductos.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }

        Producto producto = _colaProductos.getFirst();

        _colaProductos.removeFirst();

        System.out.println("Objeto con ID " + producto + " extraído");

        notifyAll();

    }
}
