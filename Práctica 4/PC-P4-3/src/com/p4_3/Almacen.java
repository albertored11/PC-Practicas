package com.p4_3;

import java.util.List;

public interface Almacen {

    /*
    Almacena (como último) un producto en el almacén. Si no hay hueco, el
    proceso que ejecute el método bloqueará hasta que lo haya.
     */
    void almacenar(List<Producto> productos);

    /*
    Extrae el primer producto disponible. Si no hay productos, el proceso que
    ejecute el método bloqueará hasta que se almacene un dato.
     */
    void extraer(int n);

}
