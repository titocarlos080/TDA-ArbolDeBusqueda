/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;


import java.util.ArrayList;
import java.util.*;


public class ArbolesBinarios<K extends Comparable<K>,V> implements IArbolBusqueda<K, V>{

    protected NodoBinario<K,V> raiz;

    @Override
    public void vaciar() {
      this.raiz=(NodoBinario<K,V>)NodoBinario.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
      return NodoBinario.esNodoVacio(this.raiz);
    }

    @Override
    public int size() {
        Stack<NodoBinario<K,V>>pilaNodos=new Stack<>();
       int cantidad=0;
       if(this.esArbolVacio()){
           return cantidad;
       }
       pilaNodos.push(this.raiz);
       while(!pilaNodos.empty()){
           NodoBinario<K,V>nodo=pilaNodos.pop();
           cantidad++;
            if(!nodo.esVacioHijoDerecho()){
                pilaNodos.push(nodo.getHijoDerecho());
            }
            if(!nodo.esVacioHijoIzquierdo()){
                pilaNodos.push(nodo.getHijoIzquierdo());
            }
       }
       return cantidad;
    }

    @Override
    public int altura() {
        return altura(this.raiz);
    }
    protected int altura(NodoBinario<K,V>nodo){
        if(NodoBinario.esNodoVacio(nodo)){
            return 0;
        }else{
            int a=altura(nodo.getHijoDerecho());
            int b=altura(nodo.getHijoIzquierdo());
            return a>b?a+1:b+1;
        }
        
    }

  
       
    public int alturaIt(){
       //se usa una lista o vector para almacenar cada clave y su hijo de izquierda a derecha 
      if(esArbolVacio()){
          return 0;
      }
      int alturaArbol=0;
      Queue<NodoBinario<K,V>>colaNodos=new LinkedList<>();
      colaNodos.offer(this.raiz);  
        while(!colaNodos.isEmpty()){
            int cantidadDeNodosEnLaCola=colaNodos.size();
            int i=0;
           while(i<cantidadDeNodosEnLaCola ){    
           NodoBinario<K,V> actual=colaNodos.poll();
           if(!actual.esVacioHijoIzquierdo()){
               colaNodos.offer(actual.getHijoIzquierdo());
           }
           if(!actual.esVacioHijoDerecho()){
               colaNodos.offer(actual.getHijoDerecho());
           }
           i++;
          }
          alturaArbol++;
        }  
      return alturaArbol;           
    }
   

    @Override
    public int nivel() {
        return this.altura()-1;
    }

 
    public K minimo() {
       if(esArbolVacio()){
           return null;
       }
       NodoBinario<K,V>actual=this.raiz;
       NodoBinario<K,V>anterior=(NodoBinario<K,V>)NodoBinario.nodoVacio();
        while(!NodoBinario.esNodoVacio(actual)){
            anterior=actual;
            actual=actual.getHijoIzquierdo();
        }
       return anterior.getClave();
    }

     
    public K maximo() {
       if(esArbolVacio()){
           return null;
       }
        NodoBinario<K,V>actual=this.raiz;
        NodoBinario<K,V>anterior=(NodoBinario<K,V>)NodoBinario.nodoVacio();
        while(!NodoBinario.esNodoVacio(actual)){
            anterior=actual;
            actual=actual.getHijoDerecho();
        }
        return anterior.getClave();
    }

    @Override
    public void insertar(K claveInsertar, V valorInsertar) {
        if(claveInsertar==null){
            throw new IllegalArgumentException("Clave no puede ser nula");
        }
         if(valorInsertar==null){
            throw new IllegalArgumentException("Valor no puede ser nulo");
        }
        if(this.esArbolVacio()){
            raiz=new NodoBinario(claveInsertar,valorInsertar);
            return;
        }
            NodoBinario<K,V> actual=raiz;
            NodoBinario<K,V> anterior=(NodoBinario<K,V>)NodoBinario.nodoVacio();
                while(!NodoBinario.esNodoVacio(actual)){
                    anterior=actual;
                    K claveActual=actual.getClave();
                        if(claveInsertar.compareTo(claveActual)<0){
                            actual=actual.getHijoIzquierdo();
                        }else if(claveInsertar.compareTo(claveActual)>0) {
                                actual=actual.getHijoDerecho();
                        }else{
                            actual.setValor(valorInsertar);
                            return;
                        }
                } 
                   NodoBinario<K,V>nuevo=new NodoBinario<>(claveInsertar,valorInsertar);
                   K clavePadre=anterior.getClave();
                    if(claveInsertar.compareTo(clavePadre)<0){
                       anterior.setHijoIzquierdo(nuevo); 
                    }else{
                        anterior.setHijoDerecho(nuevo); 
                    }   
                    
        }

    @Override
    public V eliminar(K claveAEliminar) {
        V valorRetorno=this.buscar(claveAEliminar);
        if(valorRetorno==null){
          return null;
        }
        this.raiz=eliminar(raiz,claveAEliminar);
        return valorRetorno;
    }
    private NodoBinario<K,V>eliminar(NodoBinario<K,V>nodoActual,K claveAEliminar){
        if(NodoBinario.esNodoVacio(nodoActual)){
            return (NodoBinario<K,V>)NodoBinario.nodoVacio();
        }
            K claveActual=nodoActual.getClave();
                if(claveAEliminar.compareTo(claveActual)<0){
                    NodoBinario<K,V>izquierdo=eliminar(nodoActual.getHijoIzquierdo(), claveAEliminar);
                    nodoActual.setHijoIzquierdo(izquierdo);
                    return nodoActual;
                }
                if(claveAEliminar.compareTo(claveActual)>0){
                   NodoBinario<K,V>derecho=eliminar(nodoActual.getHijoDerecho(), claveAEliminar);
                   nodoActual.setHijoDerecho(derecho);
                   return nodoActual;
                }
             /// SI SE LLEGA A ESTE PUNTO SE ENCONTRO LA CLAVE A ELIMINAR
             ///YA QUE LA CLAVE A ELIMINAR NO ES MENOR NI MAYOR ,SINO IGUAL
            // # caso 1 el nodo a eliminar es una hoja
            if(nodoActual.esHoja()){
                return (NodoBinario<K,V>)NodoBinario.nodoVacio();
            }
            //# CASO 2 LA CLAVE A ELIMINAR ES UN NODO INCOMPLETO
            if(nodoActual.esVacioHijoDerecho() && !nodoActual.esVacioHijoIzquierdo()){
                return nodoActual.getHijoIzquierdo();
            }
            if(!nodoActual.esVacioHijoDerecho() && nodoActual.esVacioHijoIzquierdo()){
                return nodoActual.getHijoDerecho();
            }
            // # CASO 3 LA CLAVE A ELIMINAR ES UN NODO COMPLETO 
            // HAY QUE BUSCAR SU NODO SUCESOR
            NodoBinario<K,V>nodoSucesor=cambiar(nodoActual.getHijoDerecho());
            NodoBinario<K,V>posibleNuevo=eliminar(nodoActual.getHijoDerecho(),nodoSucesor.getClave());
            nodoActual.setHijoDerecho(posibleNuevo);
            nodoSucesor.setHijoDerecho(nodoActual.getHijoDerecho());
            nodoSucesor.setHijoIzquierdo(nodoActual.getHijoIzquierdo());
            nodoActual.setHijoDerecho((NodoBinario<K,V>)NodoBinario.nodoVacio());
            nodoActual.setHijoIzquierdo((NodoBinario<K,V>)NodoBinario.nodoVacio());
        
        return nodoSucesor;
    
    }
    public NodoBinario<K,V> cambiar(NodoBinario<K,V>nodoActual){ 
         NodoBinario<K,V>anterior=(NodoBinario<K,V>)NodoBinario.nodoVacio();
         if(NodoBinario.esNodoVacio(nodoActual)){
             return (NodoBinario<K,V>)NodoBinario.nodoVacio();
         }
         while(!NodoBinario.esNodoVacio(nodoActual)){
             anterior=nodoActual;
             nodoActual=nodoActual.getHijoIzquierdo();
         }
         return anterior;
    }

    @Override
    public boolean contiene(K clave) {
        return this.buscar(clave) != null;
    }
    
    public List<V>valoresEnInOrden(){
     List<V>recorrido=new LinkedList<>();
     valoresEnInOrden(this.raiz,recorrido);
     return recorrido;
    }
    private void valoresEnInOrden(NodoBinario<K,V>nodoActual,List<V>recorrido){
        if(NodoBinario.esNodoVacio(nodoActual)){
        return;
        }
        valoresEnInOrden(nodoActual.getHijoIzquierdo(),recorrido);
        recorrido.add(nodoActual.getValor());
        valoresEnInOrden(nodoActual.getHijoDerecho(),recorrido);
        
    }

    @Override
    public V buscar(K clave) {
       if(!esArbolVacio()){
           NodoBinario<K,V>actual=raiz;
           while(!NodoBinario.esNodoVacio(actual)){
               K claveActual=actual.getClave();
               if(clave.compareTo(claveActual)==0){
                   return actual.getValor();
               }else if(clave.compareTo(claveActual)>0){
                   actual=actual.getHijoDerecho();
               }else{
                   actual=actual.getHijoIzquierdo();
               }
           }
           return null;
       }else{
           return null;
       }
    }

    @Override
    public List<K> recorridoEnInOrden() {
        List<K>recorrido=new LinkedList<>();
        recorridoEnInOrden(this.raiz,recorrido);
        return recorrido;
    }
    private void recorridoEnInOrden(NodoBinario<K,V>nodoActual,List<K>recorrido){
        if(NodoBinario.esNodoVacio(nodoActual)){
            return ;
        }
        recorridoEnInOrden(nodoActual.getHijoIzquierdo(),recorrido);
        recorrido.add(nodoActual.getClave());
        recorridoEnInOrden(nodoActual.getHijoDerecho(),recorrido);
    }

    @Override
    public List<K> recorridoEnPreOrden() {
       Stack<NodoBinario<K,V>>pilaNodos=new Stack<>();
       List<K>lista=new ArrayList();
       if(this.esArbolVacio()){
           return lista;
       }
       pilaNodos.push(this.raiz);
       while(!pilaNodos.empty()){
           NodoBinario<K,V>nodo=pilaNodos.pop();
           lista.add(nodo.getClave());
            if(!nodo.esVacioHijoDerecho()){
                pilaNodos.push(nodo.getHijoDerecho());
            }
            if(!nodo.esVacioHijoIzquierdo()){
                pilaNodos.push(nodo.getHijoIzquierdo());
            }
       }
       return lista;
    }

    public List<K> recorridoEnPostOrden() {
        List<K>lista=new ArrayList<>();
        if(esArbolVacio()){
            return lista;
        }
        Stack<NodoBinario<K,V>>pilaNodos=new Stack<>();
        NodoBinario<K,V>actual=this.raiz;
        //el procesos inicial antes de iterar en la pila
        meterPilaParaPostOrden(pilaNodos,actual);
        //empezamos a iterar sobre la pila
            while(!pilaNodos.isEmpty()){
                actual=pilaNodos.pop();
                lista.add(actual.getClave());
                    if(!pilaNodos.isEmpty()){
                        NodoBinario<K,V>tope=pilaNodos.peek();
                            if(!tope.esVacioHijoDerecho() && (tope.getHijoDerecho() != actual)){
                                meterPilaParaPostOrden(pilaNodos,tope.getHijoDerecho());
                            }
                    }
            }
            return lista;
    }
    public void meterPilaParaPostOrden(Stack<NodoBinario<K,V>>pila,NodoBinario<K,V>nodo){
        while(!NodoBinario.esNodoVacio(nodo)){
            pila.push(nodo);
                if(!nodo.esVacioHijoIzquierdo()){
                    nodo=nodo.getHijoIzquierdo();
                }else{
                    nodo=nodo.getHijoDerecho();
                }
        }
    }

    public List<K> recorridoPorNiveles() {
      List<K>claves=new ArrayList<>();
     
      if(esArbolVacio()){
          return claves;
      }
      Queue<NodoBinario<K,V>>colaNodos=new LinkedList<>();
      colaNodos.offer(this.raiz);  
        while(!colaNodos.isEmpty()){
           NodoBinario<K,V> actual=colaNodos.poll();
           claves.add(actual.getClave());
           if(!actual.esVacioHijoIzquierdo()){
               colaNodos.offer(actual.getHijoIzquierdo());
           }
           if(!actual.esVacioHijoDerecho()){
               colaNodos.offer(actual.getHijoDerecho());
           }
        }  
      return claves;         
    }
    public List<K> recorridoIn(){
       List<K>recorrido=new ArrayList<>();
       recorridoIn(recorrido,this.raiz);
       return recorrido;
    }
    private void recorridoIn(List<K> lista,NodoBinario<K,V>nodo){
        if(NodoBinario.esNodoVacio(nodo)){
        }else{
         recorridoIn(lista,nodo.getHijoIzquierdo());
         lista.add(nodo.getClave());
         recorridoIn(lista,nodo.getHijoDerecho());
        }
    }

    @Override
    public List<K> recorridoEnPosOrden() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<K> recorridoPorNivelesOrden() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


}
