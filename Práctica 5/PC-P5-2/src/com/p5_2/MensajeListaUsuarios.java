package com.p5_2;

public class MensajeListaUsuarios extends MensajeDesdeCliente {

    public MensajeListaUsuarios(String origen, String destino, String username) {
        super("MENSAJE_LISTA_USUARIOS", origen, destino, username);
    }
}
