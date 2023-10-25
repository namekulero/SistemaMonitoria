package com.Clases.Estructuras.node;

public class NodoArbolBinario<T> extends Nodo<T> {
    private NodoArbolBinario<T> subDerecha = null;
    private NodoArbolBinario<T> subIzquierda = null;

    public NodoArbolBinario(T objeto) {
        super(objeto);
    }

    public NodoArbolBinario<T> getSubDerecha() {
        return subDerecha;
    }

    public NodoArbolBinario<T> getSubIzquierda() {
        return subIzquierda;
    }

    public void setSubDerecha(NodoArbolBinario<T> subDerecha) {
        this.subDerecha = subDerecha;
    }

    public void setSubIzquierda(NodoArbolBinario<T> subIzquierda) {
        this.subIzquierda = subIzquierda;
    }
}
