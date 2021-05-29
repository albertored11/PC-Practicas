package pc.p5.monitor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Monitor readers/writers implementado con lock & conditions
public class ReadersWritersController {

    private int _numReaders; // número actual de lectores
    private int _numWriters; // número actual de escritores
    private final Lock _lock; // lock para garantizar que los métodos del monitor se ejecutan en exclusión mutua
    private final Condition _okToRead; // variable condicional para indicar que se puede leer
    private final Condition _okToWrite; // variable condicional para indicar que se puede escribir

    public ReadersWritersController() {

        _numReaders = 0;
        _numWriters = 0;
        _lock = new ReentrantLock(true);
        _okToRead = _lock.newCondition();
        _okToWrite = _lock.newCondition();

    }

    public boolean requestRead() {

        _lock.lock();

        // Si hay writers, esperar a que nos digan que se puede leer
        while (_numWriters > 0) {
            try {
                _okToRead.await();
            }
            catch (InterruptedException e) {

                System.err.println("ERROR: interrupted thread");
                _lock.unlock();

                return false;

            }
        }

        _numReaders++;

        _lock.unlock();

        return true;

    }

    public void releaseRead() {

        _lock.lock();

        _numReaders--;

        // Si no quedan readers, indicar que se puede escribir
        if (_numReaders == 0)
            _okToWrite.signal();

        _lock.unlock();

    }

    public boolean requestWrite() {

        _lock.lock();

        // Si hay readers o writers, esperar a que nos digan que se puede escribir
        while (_numReaders > 0 || _numWriters > 0) {
            try {
                _okToWrite.await();
            }
            catch (InterruptedException e) {

                System.err.println("ERROR: interrupted thread");
                _lock.unlock();

                return false;

            }
        }

        _numWriters++;

        _lock.unlock();

        return true;

    }

    public void releaseWrite() {

        _lock.lock();

        _numWriters--;

        // Indicar que se puede leer y escribir
        _okToWrite.signal();
        _okToRead.signalAll();

        _lock.unlock();

    }

}
