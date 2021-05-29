package pc.p5.mensaje;

import pc.p5.usuario.Usuario;

public class MensajePedirFichero extends MensajeDesdeCliente {

    private final String _filename; // nombre del fichero

    public MensajePedirFichero(String filename, Usuario user) {

        super("MENSAJE_PEDIR_FICHERO", user);

        _filename = filename;

    }

    public String getFile() {
        return _filename;
    }

}
