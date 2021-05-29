package pc.p5.mensaje;

import pc.p5.usuario.Usuario;

import java.util.List;

public class MensajeConfirmacionListaUsuarios extends Mensaje {

    private final List<Usuario> _userList;

    public MensajeConfirmacionListaUsuarios(List<Usuario> userList) {

        super("MENSAJE_CONFIRMACION_LISTA_USUARIOS");

        _userList = userList;

    }

    public List<Usuario> getUserList() {
        return _userList;
    }

}
