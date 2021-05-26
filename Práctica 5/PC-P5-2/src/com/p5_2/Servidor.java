package com.p5_2;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.exit;

public class Servidor {

    private Map<Usuario, Stream> _userStreamMap;
    private List<Usuario> _userList;
    private String _inetAddress;
    private int _port;
    private ServerSocket _servSock;

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

    }

    public Map<Usuario, Stream> getUserStreamMap() {
        return _userStreamMap;
    }

    public OutputStream getOutputStream(Usuario user) {
        return _userStreamMap.get(user).getOut();
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

    public void putInUserStreamMap(Usuario user, Stream stream) {
        _userStreamMap.put(user, stream);
    }

    public void addToUserList(Usuario user, List<Fichero> fileList) {
        _userList.add(user);
    }

    public void removeFromUserLists(Usuario user) {

        _userStreamMap.remove(user);
        _userList.remove(user);

    }

    public Usuario getFileUser(Fichero filename) {

        for (Usuario user : _userList)
            for (Fichero file : user.getFileList())
                if (file.getFilePath().equals(filename))
                    return user;

        return null;

    }

}
