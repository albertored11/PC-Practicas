package com.p5_2;

public class MensajeListaUsuarios extends MensajeDesdeCliente {

    public MensajeListaUsuarios(String origen, String destino, Usuario user) {
        super("MENSAJE_LISTA_USUARIOS", origen, destino, user);
    }

}
