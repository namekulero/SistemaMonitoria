package com.Clases.Estructuras.queue;

import com.Clases.Estructuras.interfaces.queue.QueueInterface;

public class ColaPrioridad<T> implements QueueInterface<T> {
    public int cantidad;
    public int tamano = 0;
    public Cola<T>[] colaPrioridad;

    public ColaPrioridad(int cantidadPrioridad) {
        this.cantidad = cantidadPrioridad;
        colaPrioridad = new Cola[cantidadPrioridad];
        for (int i = 0; i < cantidadPrioridad; i++) {
            colaPrioridad[i] = new Cola<T>();
        }
    }

    @Override
    public boolean clear() {
        for (int i = 0; i < cantidad; i++) {
            if (!colaPrioridad[i].isEmpty()) {
                colaPrioridad[i].clear();
            }
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < cantidad; i++) {
            if (!colaPrioridad[i].isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public T peek() {
        for (int i = cantidad-1; i >= 0; i++) {
            if (!colaPrioridad[i].isEmpty()) {
                return colaPrioridad[i].peek();
            }
        }
        return null;
    }

    @Override
    public T extract() {
        for (int i = 0; i < cantidad; i++) {
            if (!colaPrioridad[i].isEmpty()) {
                tamano--;
                return colaPrioridad[i].extract();
            }
        }
        return null;
    }

    @Override
    public boolean insert(T object) {
        tamano++;
        return colaPrioridad[cantidad-1].insert(object);
    }

    public boolean insert(T object, int prioridad) {
        tamano++;
        if (prioridad >= 0 && prioridad < cantidad) {
            return colaPrioridad[prioridad].insert(object);
        }
        return false;
    }

    @Override
    public int size() {
        return tamano;
    }

    @Override
    public boolean search(T object) {
        for (int i = 0; i < cantidad; i++) {
            if (!colaPrioridad[i].isEmpty()) {
                if (colaPrioridad[i].search(object)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean sort() {
        for (int i = 0; i < cantidad; i++) {
            if (!colaPrioridad[i].isEmpty()) {
                colaPrioridad[i].sort();
            }
        }
        return false;
    }

    @Override
    public boolean reverse() {
        for (int i = 0; i < cantidad; i++) {
            if (!colaPrioridad[i].isEmpty()) {
                colaPrioridad[i].reverse();
            }
        }
        return false;
    }
}
