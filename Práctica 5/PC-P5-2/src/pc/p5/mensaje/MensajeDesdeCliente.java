package pc.p5.mensaje;

import pc.p5.usuario.Usuario;

public abstract class MensajeDesdeCliente extends Mensaje {

    private final Usuario _user;

    public MensajeDesdeCliente(String tipo, Usuario user) {

        super(tipo);

        _user = user;

    }

    public Usuario getUser() {
        return _user;
    }

}
