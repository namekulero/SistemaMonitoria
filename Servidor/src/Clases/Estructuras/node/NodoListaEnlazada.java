package com.Clases.Estructuras.node;

public class NodoListaEnlazada<T> extends Nodo<T> {
    NodoListaEnlazada<T> siguiente;
    
    public NodoListaEnlazada() {
        super();
        siguiente = null;
    }
    
    public NodoListaEnlazada(T objeto) {
        super(objeto);
        siguiente = null;
    }
    
    public NodoListaEnlazada(T objeto, NodoListaEnlazada<T> siguiente) {
        super(objeto);
        this.siguiente = siguiente;
    }

    public NodoListaEnlazada<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoListaEnlazada<T> siguiente) {
        this.siguiente = siguiente;
    }

    public boolean hasSiguiente() {
        if(this.siguiente != null) {
            return true;
        }
        return false;
    }
}
