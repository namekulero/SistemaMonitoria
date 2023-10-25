package com.Clases.Estructuras.tree;

import com.Clases.Estructuras.linkedlist.ListaEnlazada;
import com.Clases.Estructuras.node.NodoArbolBinario;
import com.Clases.Estructuras.queue.Cola;

public class ArbolBinario<T> {
    NodoArbolBinario<T> raiz = null;

    public ArbolBinario(T objeto) {
        raiz = new NodoArbolBinario<T>(objeto);
    }

    public ArbolBinario(NodoArbolBinario<T> raiz) {
        this.raiz = raiz;
    }

    public int getHeight() {
        int altura = 0;
        if (raiz != null) {
            altura = 1;
            int alturaIzquierda = getHeight(altura, raiz.getSubIzquierda());
            int alturaDerecha = getHeight(altura, raiz.getSubDerecha());
            if (alturaIzquierda >= alturaDerecha) {
                altura = alturaIzquierda;
            } else {
                altura = alturaDerecha;
            }
        }
        return altura;
    }

    public int getHeight(int alturaActual, NodoArbolBinario<T> nodoActual) {
        if (nodoActual != null) {
            alturaActual++;
            int alturaIzquierda = getHeight(alturaActual, raiz.getSubIzquierda());
            int alturaDerecha = getHeight(alturaActual, raiz.getSubDerecha());
            if (alturaIzquierda >= alturaDerecha) {
                return alturaIzquierda;
            } else {
                return alturaDerecha;
            }
        }
        return alturaActual;
    }

    public boolean insert(T objeto) {
        Cola<NodoArbolBinario<T>> colaRestante = new Cola<>();
        return insert(objeto, raiz, colaRestante);
    }

    public boolean insert(T objeto, NodoArbolBinario<T> nodoActual, Cola<NodoArbolBinario<T>> colaActual) {
        if (nodoActual != null) {
            if (nodoActual.getSubIzquierda() != null) {
                colaActual.insert(nodoActual.getSubIzquierda());
            } else {
                nodoActual.setSubIzquierda(new NodoArbolBinario<T>(objeto));
                return true;
            }
            if (nodoActual.getSubDerecha() != null) {
                colaActual.insert(nodoActual.getSubDerecha());
            } else {
                nodoActual.setSubDerecha(new NodoArbolBinario<T>(objeto));
                return true;
            }
            if (!colaActual.isEmpty()) {
                return insert(objeto, colaActual.extract(), colaActual);
            }
        }
        return false;
    }

    public boolean search(T objeto) {
        return search(objeto, raiz);
    }

    public boolean search(T objeto, NodoArbolBinario<T> nodoActual) {
        if (nodoActual.isEquals(objeto)) {
            return true;
        } else {
            if (nodoActual.getSubIzquierda() != null) {
                if (search(objeto, nodoActual.getSubIzquierda())) {
                    return true;
                }
            } if (nodoActual.getSubDerecha() != null) {
                if (search(objeto, nodoActual.getSubDerecha())) {
                    return true;
                }
            }
        }
        return false;
    }

    public ListaEnlazada<T> ordenPrefijo() {
        ListaEnlazada<T> listaPrefijo = new ListaEnlazada<>();
        return ordenPrefijo(raiz, listaPrefijo);
    }

    public ListaEnlazada<T> ordenPrefijo(NodoArbolBinario<T> nodoActual, ListaEnlazada<T> listaActual) {
        if (nodoActual != null) {
            listaActual.add(nodoActual.getObject());
            listaActual = ordenPrefijo(nodoActual.getSubIzquierda(), listaActual);
            listaActual = ordenPrefijo(nodoActual.getSubDerecha(), listaActual);
        }
        return listaActual;
    }

    public ListaEnlazada<T> ordenInfijo() {
        ListaEnlazada<T> listaInfijo = new ListaEnlazada<>();
        return ordenInfijo(raiz, listaInfijo);
    }

    public ListaEnlazada<T> ordenInfijo(NodoArbolBinario<T> nodoActual, ListaEnlazada<T> listaActual) {
        if (nodoActual != null) {
            listaActual = ordenInfijo(nodoActual.getSubIzquierda(), listaActual);
            listaActual.add(nodoActual.getObject());
            listaActual = ordenInfijo(nodoActual.getSubDerecha(), listaActual);
        }
        return listaActual;
    }

    public ListaEnlazada<T> ordenPosfijo() {
        ListaEnlazada<T> listaPosfijo = new ListaEnlazada<>();
        return ordenPosfijo(raiz, listaPosfijo);
    }

    public ListaEnlazada<T> ordenPosfijo(NodoArbolBinario<T> nodoActual, ListaEnlazada<T> listaActual) {
        if (nodoActual != null) {
            listaActual = ordenPosfijo(nodoActual.getSubIzquierda(), listaActual);
            listaActual = ordenPosfijo(nodoActual.getSubDerecha(), listaActual);
            listaActual.add(nodoActual.getObject());
        }
        return listaActual;
    }

    public ListaEnlazada<T> ordenNivel() {
        ListaEnlazada<T> listaNivel = new ListaEnlazada<>();
        Cola<NodoArbolBinario<T>> colaRestante = new Cola<>();
        return ordenNivel(raiz, listaNivel, colaRestante);
    }

    public ListaEnlazada<T> ordenNivel(NodoArbolBinario<T> nodoActual, ListaEnlazada<T> listaActual, Cola<NodoArbolBinario<T>> colaActual) {
        if (nodoActual != null) {
            listaActual.add(nodoActual.getObject());
            if (nodoActual.getSubIzquierda() != null) {
                colaActual.insert(nodoActual.getSubIzquierda());
            } 
            if (nodoActual.getSubDerecha() != null) {
                colaActual.insert(nodoActual.getSubDerecha());
            }
            if (!colaActual.isEmpty()) {
                listaActual = ordenNivel(colaActual.extract(), listaActual, colaActual);
            }
        }
        return listaActual;
    }
}
