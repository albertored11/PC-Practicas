package pc.p5.mensaje;

import pc.p5.usuario.Usuario;

public class MensajeListaUsuarios extends MensajeDesdeCliente {

    public MensajeListaUsuarios(Usuario user) {

        super("MENSAJE_LISTA_USUARIOS", user);

    }

}
