package com.p5_2;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class Stream implements Serializable {

    private OutputStream _out;
    private InputStream _in;

    public Stream(OutputStream out, InputStream in) {

        _out = out;
        _in = in;

    }

    public OutputStream getOut() {
        return _out;
    }

    public InputStream getIn() {
        return _in;
    }

}
