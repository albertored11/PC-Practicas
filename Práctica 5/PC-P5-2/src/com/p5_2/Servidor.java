package com.p5_2;

import com.p5_2.sync.ReadersWritersController;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.System.exit;

public class Servidor {

    private final Map<Usuario, Stream> _userStreamMap; // mapa usuarios con object streams cliente-servidor
    private final List<Usuario> _userList; // lista de usuarios
    private ServerSocket _servSock; // server socket para aceptar conexiones entrantes
    private int _nextPort; // puerto que se asignará a la siguiente comunicación P2P
    private final ReadersWritersController _userStreamMapController; // monitor para controlar el acceso al mapa usuarios-streams
    private final ReadersWritersController _userListController; // monitor para controlar el acceso a la lista de usuarios
    private final Lock _nextPortLock; // lock para acceder a nextPort

    public Servidor(int port) {

        _userStreamMap = new HashMap<>();
        _userList = new ArrayList<>();

        // Crear server socket
        try {
            _servSock = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("ERROR: IO exception");
            exit(1);
        }

        // Asignar puertos para las comunicaciones P2P a partir del 30000 (hasta el 30999)
        _nextPort = 30000;

        _userStreamMapController = new ReadersWritersController();
        _userListController = new ReadersWritersController();

        _nextPortLock = new ReentrantLock(true);

    }

    // Obtener flujo de salida para objetos para un usuario
    public ObjectOutputStream getObjectOutputStream(Usuario user) {

        _userStreamMapController.requestRead();

        ObjectOutputStream ret = _userStreamMap.get(user).getObjOutStr();

        _userStreamMapController.releaseRead();

        return ret;

    }

    // Obtener lista de usuarios
    public List<Usuario> getUserList() {

        _userListController.requestRead();

        // Crear nueva lista haciendo copia de la original para evitar accesos externos
        List<Usuario> ret = new ArrayList<>(_userList);

        _userListController.releaseRead();

        return ret;

    }

    // Obtener server socket
    public ServerSocket getServSock() {
        return _servSock;
    }

    // Añadir usuario y flujos para objetos al mapa
    public void putInUserStreamMap(Usuario user, Stream stream) {

        _userStreamMapController.requestWrite();

        _userStreamMap.put(user, stream);

        _userStreamMapController.releaseWrite();

    }

    // Añadir usuario a la lista
    public void addToUserList(Usuario user) {

        _userListController.requestWrite();

        _userList.add(user);

        _userListController.releaseWrite();

    }

    // Eliminar usuario del mapa y de la lista
    public void removeFromUserLists(Usuario user) {

        // Eliminar del mapa
        _userStreamMapController.requestWrite();

        _userStreamMap.remove(user);

        _userStreamMapController.releaseWrite();

        // Eliminar de la lista
        _userListController.requestWrite();

        _userList.remove(user);

        _userListController.releaseWrite();

    }

    // Obtener fichero a partir de un nombre de fichero
    public Fichero getFileFromFilename(String filename) {

        _userListController.requestRead();

        for (Usuario user : _userList)
            for (Fichero file : user.getFileList())
                if (file.hasFilename(filename)) { // devuelve el primero que encuentre
                    _userListController.releaseRead();
                    return file;
                }

        _userListController.releaseRead();

        return null;

    }

    // Devuelve la referencia original de un usuario; devuelve la entrada si no lo encuentra
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

    // Método tipo "fetch-and-add" para obtener el puerto para la siguiente comunicación P2P e incrementarlo
    public int getAndIncrementNextPort() {

        _nextPortLock.lock();

        int ret = _nextPort;
        _nextPort++;

        // Si se llega a 31000, volver a 30000
        if (_nextPort == 31000)
            _nextPort = 30000;

        _nextPortLock.unlock();

        return ret;

    }

    // Comprobar si hay algún usuario registrado con un nombre
    public boolean hasUser(String username) {

        _userListController.requestRead();

        for (Usuario u : _userList)
            if (u.toString().equals(username)) {
                _userListController.releaseRead();
                return true;
            }

        _userListController.releaseRead();

        return false;

    }

}
