package com.p5_2;

import java.util.List;

public class MensajeConexion extends MensajeDesdeCliente {

    private final Stream _stream;
    private final List<Fichero> _fileList;

    public MensajeConexion(String origen, String destino, Stream stream, String username, List<Fichero> fileList) {

        super("MENSAJE_CONEXION", origen, destino, username);

        _stream = stream;
        _fileList = fileList;

    }

    public Stream getStream() {
        return _stream;
    }

    public List<Fichero> getFileList() {
        return _fileList;
    }

}
