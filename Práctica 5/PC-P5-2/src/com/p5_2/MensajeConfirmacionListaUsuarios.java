package com.p5_2;

import java.util.List;
import java.util.Map;

public class MensajeConfirmacionListaUsuarios extends Mensaje {

    private final Map<String, List<Fichero>> _userFileMap;

    public MensajeConfirmacionListaUsuarios(String origen, String destino, Map<String, List<Fichero>> userFileMap) {

        super("MENSAJE_CONFIRMACION_LISTA_USUARIOS", origen, destino);

        _userFileMap = userFileMap;

    }

    public Map<String, List<Fichero>> getUserFileMap() {
        return _userFileMap;
    }
}
