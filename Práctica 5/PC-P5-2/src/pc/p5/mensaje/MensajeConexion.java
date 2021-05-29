package pc.p5.mensaje;

import pc.p5.usuario.Usuario;

public class MensajeConexion extends MensajeDesdeCliente {

    public MensajeConexion(Usuario user) {

        super("MENSAJE_CONEXION", user);

    }

}
