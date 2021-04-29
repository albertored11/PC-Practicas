package com.p4_3;

import java.util.LinkedList;
import java.util.List;

public class AlmacenMultiple implements Almacen {

    private int _capacidad;
    private LinkedList<Producto> _colaProductos;

    AlmacenMultiple(int capacidad) {

        _capacidad = capacidad;
        _colaProductos = new LinkedList<>();

    }

    @Override
    public synchronized void almacenar(List<Producto> productos) {

        for (Producto producto : productos) {

            while (_colaProductos.size() == _capacidad) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }

            _colaProductos.addLast(producto);

            System.out.println("Objeto con ID " + producto + " almacenado");

            notifyAll();

        }

    }

    @Override
    public synchronized void extraer(int n) {

        for (int i = 0; i < n; ++i) {

            while (_colaProductos.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }

            Producto producto = _colaProductos.getFirst();

            _colaProductos.removeFirst();

            System.out.println("Objeto con ID " + producto + " extraído");

            notifyAll();

        }

    }
}
