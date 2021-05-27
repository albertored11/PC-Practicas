package com.p5_2.sync;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReadersWritersController {

    private int _numReaders;
    private int _numWriters;
    private final Lock _lock;
    private final Condition _okToRead;
    private final Condition _okToWrite;

    public ReadersWritersController() {

        _numReaders = 0;
        _numWriters = 0;
        _lock = new ReentrantLock(true);
        _okToRead = _lock.newCondition();
        _okToWrite = _lock.newCondition();

    }

    public void requestRead() {

        _lock.lock();

        while (_numWriters > 0) {
            try {
                _okToRead.await();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        _numReaders++;

        _lock.unlock();

    }

    public void releaseRead() {

        _lock.lock();

        _numReaders--;

        if (_numReaders == 0)
            _okToWrite.signal();

        _lock.unlock();

    }

    public void requestWrite() {

        _lock.lock();

        while (_numReaders > 0 || _numWriters > 0) {
            try {
                _okToWrite.await();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        _numWriters++;

        _lock.unlock();

    }

    public void releaseWrite() {

        _lock.lock();

        _numWriters--;

        _okToWrite.signal();
        _okToRead.signalAll();

        _lock.unlock();

    }

}
