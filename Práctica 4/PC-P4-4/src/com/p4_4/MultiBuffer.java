package com.p4_4;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MultiBuffer implements Almacen {

    private final int _capacidad;
    private final LinkedList<Producto> _colaProductos;
    private final Lock _lock;
    private final Condition _condAlmacenar;
    private final Condition _condExtraer;

    MultiBuffer(int capacidad) {

        _capacidad = capacidad;
        _colaProductos = new LinkedList<>();
        _lock = new ReentrantLock(true);
        _condAlmacenar = _lock.newCondition();
        _condExtraer = _lock.newCondition();

    }

    @Override
    public void almacenar(List<Producto> productos) {

        _lock.lock();

        for (Producto producto : productos) {

            while (_colaProductos.size() == _capacidad) {
                try {
                    _condAlmacenar.await();
                } catch (InterruptedException ignored) {
                }
            }

            _colaProductos.addLast(producto);

            System.out.println("Objeto con ID " + producto + " almacenado");

            _condExtraer.signal();

        }

        _lock.unlock();

    }

    @Override
    public void extraer(int n) {

        _lock.lock();

        for (int i = 0; i < n; ++i) {

            while (_colaProductos.isEmpty()) {
                try {
                    _condExtraer.await();
                } catch (InterruptedException ignored) {
                }
            }

            Producto producto = _colaProductos.getFirst();

            _colaProductos.removeFirst();

            System.out.println("Objeto con ID " + producto + " extraído");

            _condAlmacenar.signal();

        }

        _lock.unlock();

    }
}
