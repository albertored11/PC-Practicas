package com.p5_2;

import com.p5_2.sync.ReadersWritersController;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.System.exit;

public class Servidor {

    private final Map<Usuario, Stream> _userStreamMap;
    private final List<Usuario> _userList;
    private String _inetAddress;
    private final int _port;
    private ServerSocket _servSock;
    private int _nextPort;
    private final ReadersWritersController _userStreamMapController;
    private final ReadersWritersController _userListController;
    private final Lock _nextPortLock;

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

        _nextPort = 30000;

        _userStreamMapController = new ReadersWritersController();
        _userListController = new ReadersWritersController();

        _nextPortLock = new ReentrantLock(true);

    }

    public Map<Usuario, Stream> getUserStreamMap() {
        return _userStreamMap;
    }

    public ObjectOutputStream getObjectOutputStream(Usuario user) {

        _userStreamMapController.requestRead();

        ObjectOutputStream ret = _userStreamMap.get(user).getObjOutStr();

        _userStreamMapController.releaseRead();

        return ret;

    }

    public List<Usuario> getUserList() {

        _userListController.requestRead();

        List<Usuario> ret = _userList;

        _userListController.releaseRead();

        return ret;

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

    public void putInUserStreamMap(Usuario user, Stream stream) {

        _userStreamMapController.requestWrite();

        _userStreamMap.put(user, stream);

        _userStreamMapController.releaseWrite();

    }

    public void addToUserList(Usuario user) {

        _userListController.requestWrite();

        _userList.add(user);

        _userListController.releaseWrite();

    }

    public void removeFromUserLists(Usuario user) {

        _userStreamMapController.requestWrite();

        _userStreamMap.remove(user);

        _userStreamMapController.releaseWrite();

        _userListController.requestWrite();

        _userList.remove(user);

        _userListController.releaseWrite();

    }

    public Usuario getFileUser(String filename) {

        _userListController.requestRead();

        for (Usuario user : _userList)
            for (String file : user.getFileList())
                if (file.equals(filename)) {
                    _userListController.releaseRead();
                    return user;
                }

        _userListController.releaseRead();

        return null;

    }

    // Get original reference for a username; returns itself it not found
    public Usuario getOriginalUser(Usuario user) {

        _userListController.requestRead();

        for (Usuario u : _userList)
            if (u.toString().equals(user.toString())) {
                _userListController.releaseRead();
                return u;
            }

        _userListController.releaseRead();

        return user;

    }

    public int getAndIncrementNextPort() {

        _nextPortLock.lock();

        int ret = _nextPort;
        _nextPort++;

        _nextPortLock.unlock();

        return ret;

    }

}
