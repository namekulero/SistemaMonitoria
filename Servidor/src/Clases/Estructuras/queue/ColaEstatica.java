package com.Clases.Estructuras.queue;

import com.Clases.Estructuras.interfaces.queue.QueueInterface;

public class ColaEstatica<T> implements QueueInterface<T> {
    private int maximo = 0;
    private int tamano = 0;
    private int cabeza = 0;
    private int cola = 0;
    private T[] arreglo = (T[]) new Object[maximo];

    public ColaEstatica(int maximo) {
        this.maximo = maximo;
        arreglo = (T[]) new Object[maximo];
    }

    @Override
    public boolean clear() {
        if (!isEmpty()) {
            cabeza = cola = tamano = 0;
            for (int i = 0; i < maximo; i++) {
                arreglo[i] = null;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return tamano == 0;
    }

    @Override
    public T peek() {
        if (!isEmpty()) {
            return arreglo[cola];
        }
        return null;
    }

    @Override
    public T extract() {
        if (!isEmpty()) {
            T objeto = arreglo[cabeza];
            arreglo[cabeza] = null;
            tamano--;
            cabeza++;
            if (cabeza == maximo) {
                cabeza = 0;
            }
            if (tamano == 0) {
                cabeza = cola = tamano;
            }
            return objeto;
        }
        return null;
    }

    @Override
    public boolean insert(T object) {
        if (tamano != maximo) {
            if (isEmpty()) {
                arreglo[cabeza] = object;
                tamano++;
                return true;
            }
            cola++;
            if (cola == maximo) {
                cola = 0;
            }
            arreglo[cola] = object;
            tamano++;
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return tamano;
    }

    @Override
    public boolean search(T object) {
        int pos = 0;
        for (int i = cabeza; pos < tamano; i++) {
            if (arreglo[pos].equals(object)) {
                return true;
            }
            pos++;
        }
        return false;
    }

    @Override
    public boolean sort() {
        int pos = 0;
        T[] nuevoArreglo = (T[]) new Object[maximo];
        if (!isEmpty()) {
            for (int i = cabeza; pos < tamano; i++) {
                if (i == maximo) { i = 0; }
                nuevoArreglo[pos] = arreglo[i];
                pos++;
            }
            arreglo = nuevoArreglo;
            cabeza = 0; 
            cola = tamano-1;
            return true;
        }
        return false;
    }

    @Override
    public boolean reverse() {
        int pos = 0;
        T[] nuevoArreglo = (T[]) new Object[maximo];
        if (!isEmpty()) {
            for (int i = cola; pos < tamano; i--) {
                if (i == -1) { i = tamano-1; }
                nuevoArreglo[pos] = arreglo[i];
                pos++;
            }
            arreglo = nuevoArreglo;
            cabeza = 0;
            cola = tamano-1;
            return true;
        }
        return false;
    }

}
