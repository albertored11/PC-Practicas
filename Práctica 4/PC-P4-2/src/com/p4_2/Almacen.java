package com.p4_2;

public interface Almacen {

    /*
    Almacena (como último) un producto en el almacén. Si no hay hueco, el
    proceso que ejecute el método bloqueará hasta que lo haya.
     */
    void almacenar(Producto producto);

    /*
    Extrae el primer producto disponible. Si no hay productos, el proceso que
    ejecute el método bloqueará hasta que se almacene un dato.
     */
    void extraer();

}
