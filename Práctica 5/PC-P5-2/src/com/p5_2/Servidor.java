package com.p5_2;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import static java.lang.System.exit;

public class Servidor {

    private Map<Usuario, Stream> _userStreamMap;
    private List<Usuario> _userList;
    private String _inetAddress;
    private int _port;
    private ServerSocket _servSock;
    private Semaphore _semUserStreamMap;
    private int _nextPort;

    public Servidor(int port) {

        _userStreamMap = new HashMap<>();
        _userList = new ArrayList<>();

        try {
            _inetAddress = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            System.err.println("ERROR: unable to get localhost");
            exit(1);
        }

        _port = port;

        try {
            _servSock = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("ERROR: IO exception");
            exit(1);
        }

        _semUserStreamMap = new Semaphore(1);

        _nextPort = 30000;

    }

    public Map<Usuario, Stream> getUserStreamMap() {
        return _userStreamMap;
    }

    public ObjectOutputStream getObjectOutputStream(Usuario user) throws InterruptedException {

        return _userStreamMap.get(user).getObjOutStr();

    }

    public List<Usuario> getUserList() {
        return _userList;
    }

    public String getInetAddress() {
        return _inetAddress;
    }

    public int getPort() {
        return _port;
    }

    public ServerSocket getServSock() {
        return _servSock;
    }

    public void putInUserStreamMap(Usuario user, Stream stream) throws InterruptedException {

        _userStreamMap.put(user, stream);

    }

    public void addToUserList(Usuario user) {
        _userList.add(user);
    }

    public void removeFromUserLists(Usuario user) {

        _userStreamMap.remove(user);
        _userList.remove(user);

    }

    public Usuario getFileUser(String filename) {

        for (Usuario user : _userList)
            for (String file : user.getFileList())
                if (file.equals(filename))
                    return user;

        return null;

    }

    public Semaphore getSemUserStreamMap() {
        return _semUserStreamMap;
    }

    // Get original reference for a username; returns itself it not found
    public Usuario getOriginalUser(Usuario user) {

        for (Usuario u : _userList)
            if (u.toString().equals(user.toString()))
                return u;

        return user;

    }

    public int getAndIncrementNextPort() {

        // TODO coger lock

        int ret = _nextPort;
        _nextPort++;

        // TODO soltar lock

        return ret;

    }

}
