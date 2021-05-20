package com.p5_2;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.exit;

public class Servidor {

    private Map<String, Stream> _userStreamMap;
    private Map<String, List<Fichero>> _userFileMap;
    private String _inetAddress;
    private int _port;
    private ServerSocket _servSock;

    public Servidor(int port) {

        _userStreamMap = new HashMap<>();
        _userFileMap = new HashMap<>();

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

    public Map<String, Stream> getUserStreamMap() {
        return _userStreamMap;
    }

    public Map<String, List<Fichero>> getUserFileMap() {
        return _userFileMap;
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

    public void putInUserStreamMap(String username, Stream stream) {
        _userStreamMap.put(username, stream);
    }

    public void putInUserFileMap(String username, List<Fichero> fileList) {
        _userFileMap.put(username, fileList);
    }

    public void removeFromUserLists(String username) {

        _userStreamMap.remove(username);

    }

}
