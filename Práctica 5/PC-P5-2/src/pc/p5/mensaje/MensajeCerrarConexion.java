package pc.p5.mensaje;

import pc.p5.usuario.Usuario;

public class MensajeCerrarConexion extends MensajeDesdeCliente {

    public MensajeCerrarConexion(Usuario user) {

        super("MENSAJE_CERRAR_CONEXION", user);

    }

}
