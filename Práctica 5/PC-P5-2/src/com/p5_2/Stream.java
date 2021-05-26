package com.p5_2;

import java.io.*;

public class Stream implements Serializable {

    private ObjectOutputStream _objOutStr;
    private ObjectInputStream _objInStr;

    public Stream(ObjectOutputStream objOutStr, ObjectInputStream objInStr) {

        _objOutStr = objOutStr;
        _objInStr = objInStr;

    }

    public ObjectOutputStream getObjOutStr() {
        return _objOutStr;
    }

    public ObjectInputStream getObjInStr() {
        return _objInStr;
    }

}
