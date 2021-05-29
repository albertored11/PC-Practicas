package pc.p5.mensaje;

import java.io.Serializable;

public abstract class Mensaje implements Serializable {

    private final String _tipo; // String único para cada tipo de mensaje que lo identifiac

    public Mensaje(String tipo) {

        _tipo = tipo;

    }

    public String getTipo() {
        return _tipo;
    }

}
