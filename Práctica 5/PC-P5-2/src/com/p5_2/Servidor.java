package com.p5_2;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.exit;

public class Servidor {

    private List<Stream> _userStreamList;
    private List<List<Fichero>> _userFileList;
    private String _inetAddress;
    private int _port;
    private ServerSocket _servSock;

    public Servidor(int port) {

        _userStreamList = new ArrayList<>();
        _userFileList = new ArrayList<>();

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

    public List<Stream> getUserStreamList() {
        return _userStreamList;
    }

    public List<List<Fichero>> getUserFileList() {
        return _userFileList;
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

    public void addToUserStreamList(Stream stream) {
        _userStreamList.add(stream);
    }

    public void addToUserFileList(List<Fichero> fileList) {
        _userFileList.add(fileList);
    }

}
