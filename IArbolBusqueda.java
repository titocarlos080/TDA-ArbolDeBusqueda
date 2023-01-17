package proyecto;


import java.util.List;
 


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author hp
 * @param <K>
 * @param <V>
 */
public interface IArbolBusqueda<K extends Comparable<K>,V> {
    void insertar(K clave,V valor);
        
    V eliminar(K clave) ;
    V buscar(K clave);
  
    boolean contiene(K clave); 
    int size();
    
    int altura();
    void vaciar();
    boolean esArbolVacio();
    int nivel();
    

   //claves
    List<K> recorridoEnInOrden();
    List<K> recorridoEnPreOrden();
    List<K> recorridoEnPosOrden();
    List<K> recorridoPorNivelesOrden();
    //valores
 
    
}
