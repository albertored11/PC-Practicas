package pc.p5.mensaje;

import pc.p5.usuario.Usuario;

public class MensajePreparadoClienteServidor extends MensajeDesdeCliente {

    private final int _port; // puerto del socket entre emisor y receptor
    private final Usuario _destUser; // usuario receptor

    public MensajePreparadoClienteServidor(Usuario user, Usuario destUser, int port) {

        super("MENSAJE_PREPARADO_CLIENTESERVIDOR", user);

        _destUser = destUser;
        _port = port;

    }

    public int getPort() {
        return _port;
    }

    public Usuario getDestUser() {
        return _destUser;
    }

}
