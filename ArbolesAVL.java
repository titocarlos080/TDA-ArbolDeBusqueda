/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;



/**
 *
 * @author hp
 */
public class ArbolesAVL<K extends Comparable<K>,V> extends ArbolesBinarios<K,V> {
    public static final byte DIFERENCIA_MAXIMA=1;
    
    @Override
    public void insertar(K claveAInsertar,V valorAInsertar){
        
        if(claveAInsertar==null){
            throw new IllegalArgumentException("La clave es null insertar una clave valida");
        }
        if(valorAInsertar==null){
             throw new IllegalArgumentException("La clave es null insertar una clave valida");
        }
        
        super.raiz=insertar(this.raiz,claveAInsertar,valorAInsertar);
    }
    public NodoBinario<K,V>insertar(NodoBinario<K,V>nodoActual,K claveAInsertar,V valorAInsertar){
        if(NodoBinario.esNodoVacio(nodoActual)){
            NodoBinario<K,V>nuevo=new NodoBinario<K,V>(claveAInsertar,valorAInsertar);
            return nuevo;
        }
        K claveActual=nodoActual.getClave();
            if(claveAInsertar.compareTo(claveActual)>0){
                NodoBinario<K,V>derecho=insertar(nodoActual.getHijoDerecho(),claveAInsertar,valorAInsertar);
                nodoActual.setHijoDerecho(derecho);
                return balancear(nodoActual);
            }
            if(claveAInsertar.compareTo(claveActual)<0){
                NodoBinario<K,V>izquierdo=insertar(nodoActual.getHijoIzquierdo(),claveAInsertar,valorAInsertar);
                nodoActual.setHijoIzquierdo(izquierdo);
                return balancear(nodoActual);
            }
            //SI SE LLEGA A ESTE PUNTO ENTOCES LA CLAVE SE ENCUENTRA EN EL ARBOL Y ES LA RAIZ
            //SE REEMPLAZA EL VALOR EN LA RAIZ
            nodoActual.setValor(valorAInsertar);
            return (nodoActual);
    }
    private NodoBinario<K,V>balancear(NodoBinario<K,V>nodoActual){
        int alturaPorIzquierda=super.altura(nodoActual.getHijoIzquierdo());
        int alturaPorDerecha=super.altura(nodoActual.getHijoDerecho());
        int diferencia=alturaPorIzquierda-alturaPorDerecha;
            if(diferencia>DIFERENCIA_MAXIMA){
                //rotacion hacia la derecha
                NodoBinario<K,V>izquierdo=nodoActual.getHijoIzquierdo();
                alturaPorIzquierda=super.altura(izquierdo.getHijoIzquierdo());
                alturaPorDerecha=super.altura(izquierdo.getHijoDerecho());
                if(alturaPorDerecha>alturaPorIzquierda){//LA RAMA CONTRARIA A LA ROTACION ES MAYOR ENTONCES ES ROTACION DOBLE A DERECHA
                   return rotacionDobleADerecha(nodoActual); 
                }else if(alturaPorIzquierda>alturaPorDerecha){
                   return rotacionSimpleADerecha(nodoActual);
                }
            }else if(diferencia<-DIFERENCIA_MAXIMA){
                // rotacion hacia la izquierda
                NodoBinario<K,V>derecho=nodoActual.getHijoDerecho();
                alturaPorIzquierda=super.altura(derecho.getHijoIzquierdo());
                alturaPorDerecha=super.altura(derecho.getHijoDerecho());
                if(alturaPorIzquierda>alturaPorDerecha){// LA RAMA CONTRARIA A LA ROTACION BAJANDO UNO ES MAYOR POR TANTO ES ROTACION DOBLE
                        return rotacionDobleAIzquierda(nodoActual);
                }else if(alturaPorIzquierda<alturaPorDerecha){
                        return rotacionSimpleAIzquierda(nodoActual);
                }
            }
           return nodoActual;
    }
    private NodoBinario<K,V>rotacionSimpleAIzquierda(NodoBinario<K,V>nodoActual){//ROTACION SIMPLES ROTA EL HIJO
        NodoBinario<K,V>nodoQueRota=nodoActual.getHijoDerecho();
        nodoActual.setHijoDerecho(nodoQueRota.getHijoIzquierdo());
        nodoQueRota.setHijoIzquierdo(nodoActual);
        return nodoQueRota;
        
    }
    private NodoBinario<K,V>rotacionSimpleADerecha(NodoBinario<K,V>nodoActual){//ROTACION SIMPLE ROTA SOLO EL HIJO
        NodoBinario<K,V>nodoQueRota=nodoActual.getHijoIzquierdo();
        nodoActual.setHijoIzquierdo(nodoQueRota.getHijoDerecho());
        nodoQueRota.setHijoDerecho(nodoActual);
        return nodoQueRota;
    }
    private NodoBinario<K,V>rotacionDobleAIzquierda(NodoBinario<K,V>nodoActual){    //ROTACION DOBLE ROTA EL NIETO DEL NODO PROBLEMATICO
       nodoActual.setHijoDerecho(this.rotacionSimpleADerecha(nodoActual.getHijoDerecho()));
       return this.rotacionSimpleAIzquierda(nodoActual);       
    }
    private NodoBinario<K,V>rotacionDobleADerecha(NodoBinario<K,V>nodoActual){//ROTACION DOBLE ROTA EL NIETO DEL NODO PROBLEMATICO
        nodoActual.setHijoIzquierdo(this.rotacionSimpleAIzquierda(nodoActual.getHijoIzquierdo()));
        return this.rotacionSimpleADerecha(nodoActual);
    }
    
    @Override
    public V eliminar(K claveAEliminar){
      V valorRetorno=super.buscar(claveAEliminar);
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
                    return balancear(nodoActual);
                }
                if(claveAEliminar.compareTo(claveActual)>0){
                   NodoBinario<K,V>derecho=eliminar(nodoActual.getHijoDerecho(), claveAEliminar);
                   nodoActual.setHijoDerecho(derecho);
                   return balancear(nodoActual);
                }
             /// SI SE LLEGA A ESTE PUNTO SE ENCONTRO LA CLAVE A ELIMINAR
             ///YA QUE LA CLAVE A ELIMINAR NO ES MENOR NI MAYOR ,SINO IGUAL
            // # caso 1 el nodo a eliminar es una hoja
            if(nodoActual.esHoja()){
                return (NodoBinario<K,V>)NodoBinario.nodoVacio();
            }
            //# CASO 2 LA CLAVE A ELIMINAR ES UN NODO INCOMPLETO
            if(nodoActual.esVacioHijoDerecho() && !nodoActual.esVacioHijoIzquierdo()){
                return balancear(nodoActual.getHijoIzquierdo());
            }
            if(!nodoActual.esVacioHijoDerecho() && nodoActual.esVacioHijoIzquierdo()){
                return balancear(nodoActual.getHijoDerecho());
            }
            // # CASO 3 LA CLAVE A ELIMINAR ES UN NODO COMPLETO 
            // HAY QUE BUSCAR SU NODO SUCESOR
            NodoBinario<K,V>nodoSucesor=(cambiar(nodoActual.getHijoDerecho()));
            NodoBinario<K,V>posibleNuevo=(eliminar(nodoActual.getHijoDerecho(),nodoSucesor.getClave()));
           
            nodoActual.setHijoDerecho((posibleNuevo));
            nodoSucesor.setHijoDerecho((nodoActual.getHijoDerecho()));
            nodoSucesor.setHijoIzquierdo(nodoActual.getHijoIzquierdo());
            nodoActual.setHijoDerecho((NodoBinario<K,V>)NodoBinario.nodoVacio());
            nodoActual.setHijoIzquierdo((NodoBinario<K,V>)NodoBinario.nodoVacio());
        
        return ((nodoSucesor));
    }
    
    
   
    
}
