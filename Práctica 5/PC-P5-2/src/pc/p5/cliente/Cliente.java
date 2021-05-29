package pc.p5.cliente;

import pc.p5.usuario.Usuario;

import java.util.concurrent.Semaphore;

public class Cliente {

    private final Usuario _user;
    private final Semaphore _sem; // semáforo para controlar el flujo de stdout
    private boolean _terminate; // para que OyenteServidor nos pueda pedir que terminemos

    public Cliente(Usuario user) {

        _user = user;
        _sem = new Semaphore(0);
        _terminate = false;

    }

    public Usuario getUser() {
        return _user;
    }

    public Semaphore getSem() {
        return _sem;
    }

    public void terminate() {
        _terminate = true;
    }

    public boolean isTerminate() {
        return _terminate;
    }

}
