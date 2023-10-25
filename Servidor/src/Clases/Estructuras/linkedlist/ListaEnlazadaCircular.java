package com.Clases.Estructuras.linkedlist;

import com.Clases.Estructuras.interfaces.linkedlist.LinkedListInterface;
import com.Clases.Estructuras.interfaces.node.NodeInterface;
import com.Clases.Estructuras.node.NodoListaEnlazada;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListaEnlazadaCircular<T> implements LinkedListInterface<T>, Cloneable, Serializable {

    NodoListaEnlazada<T> cabeza;
    NodoListaEnlazada<T> cola;
    NodoListaEnlazada<T> inode;
    int tamano = 0;

    @Override
    public boolean add(T object) {
        if (object != null) {
            try {
                if (isEmpty()) {
                    cabeza = cola = new NodoListaEnlazada(object);
                    cola.setSiguiente(cabeza);
                } else {
                    cola.setSiguiente(new NodoListaEnlazada(object, cabeza));
                    cola = cola.getSiguiente();
                }
                tamano++;
                return true;
            } catch (Exception e) {

            }
        }
        return false;
    }

    @Override
    public boolean add(NodeInterface<T> node, T object) {
        if (object != null && !isEmpty()) {
            try {
                Iterator iterador = this.iterator();
                while (iterador.hasNext()) {
                    NodoListaEnlazada<T> nodo = (NodoListaEnlazada<T>) iterador.next();
                    if (nodo.isEquals(node.getObject())) {
                        NodoListaEnlazada<T> nuevoNodo = new NodoListaEnlazada(object, nodo.getSiguiente());
                        if (nodo.isEquals(cola.getObject())) { cola = nuevoNodo; }
                        nodo.setSiguiente(nuevoNodo);
                        tamano++;
                        return true;
                    }
                }
            } catch (Exception e) { }
        }
        return false;
    }

    @Override
    public boolean add(NodeInterface<T> node, NodeInterface<T> next) {
        try {
            if (!isEmpty()) {
                Iterator iterador = this.iterator();
                while (iterador.hasNext()) {
                    NodoListaEnlazada<T> nodo = (NodoListaEnlazada<T>) iterador.next();
                    if (nodo.isEquals(node.getObject())) {
                        NodoListaEnlazada<T> nuevoNodo = (NodoListaEnlazada<T>) next;
                        nuevoNodo.setSiguiente(nodo.getSiguiente());
                        if (nodo.isEquals(cola.getObject())) { cola = nuevoNodo; }
                        nodo.setSiguiente(nuevoNodo);
                        tamano++;
                        return true;
                    }
                }
            }
        } catch (Exception e) { }
        return false;
    }

    @Override
    public boolean add(T[] objects) {
        if (objects.length > 0) {
            for (T object : objects) {
                add(object);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean add(NodeInterface<T> node, T[] objects) {
        if (objects.length > 0) {
            for (T object : objects) {
                add(node, new NodoListaEnlazada(object));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean addOnHead(T object) {
        if (object != null) {
            if (!isEmpty()) {
                cabeza = new NodoListaEnlazada(object, cabeza);
                cola.setSiguiente(cabeza);
                tamano++;
            } else {
                add(object);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean addOnHead(T[] objects) {
        if (objects.length > 0) {
            for (T object : objects) {
                addOnHead(object);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean clear() {
        try {
            cabeza = cola = null;
            tamano = 0;
        } catch (Exception e) {

        }
        return false;
    }

    @Override
    public ListaEnlazadaCircular<T> cloneList() {
        if (!isEmpty()) {
            try {
                return (ListaEnlazadaCircular<T>) this.clone();
            } catch (CloneNotSupportedException ex) { }
        }
        return null;
    }

    @Override
    public boolean contains(T object) {
        Iterator iterador = this.iterator();
        while (iterador.hasNext()) {
            NodoListaEnlazada nodo = (NodoListaEnlazada) iterador.next();
            if (nodo.isEquals(object)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(T[] objects) {
        for (T object : objects) {
            if (!contains(object)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public NodeInterface<T> nodeOf(T object) {
        Iterator iterador = this.iterator();
        while (iterador.hasNext()) {
            NodoListaEnlazada nodo = (NodoListaEnlazada) iterador.next();
            if (nodo.isEquals(object)) {
                return nodo;
            }
        }
        return new NodoListaEnlazada(null);
    }

    @Override
    public boolean isEmpty() {
        return (cabeza == null);
    }

    @Override
    public T get() {
        if (cabeza != null) {
            return cabeza.getObject();
        }
        return null;
    }

    @Override
    public T[] get(int n) {
        T[] arrRetorno = (T[]) new Object[n];
        if (size() >= n) {
            Iterator iterador = this.iterator();
            for (int i = 0; i < n; i++) { // Se recorre cada posición del arreglo, y solo se iteran las primeras n posiciones
                NodoListaEnlazada<T> nodo = (NodoListaEnlazada<T>) iterador.next();
                arrRetorno[i] = nodo.getObject();
            }
        }
        return arrRetorno;
    }

    @Override
    public T getPrevious(NodeInterface<T> node) {
        if (cabeza != null) {
            NodoListaEnlazada<T> nodo = cola;
            Iterator iterador = iterator();
            while (iterador.hasNext()) {
                NodoListaEnlazada<T> nodoPrevio = nodo;
                nodo = (NodoListaEnlazada<T>) iterador.next();
                if (nodo.isEquals(node.getObject())) {
                    return nodoPrevio.getObject();
                }
            }
        }
        return null;
    }

    @Override
    public T getFromEnd() {
        if (cabeza != null) {
            return cola.getObject();
        }
        return null;
    }

    @Override
    public T[] getFromEnd(int n) {
        Object[] endArray = new Object[n];
        NodoListaEnlazada nodo = cola; // Se comienza a recorrer desde la cola
        if (size() >= n) { // Solo ejecuta la función si la lista es de igual o mayor tamaño al n ingresado
            for (int i = (n - 1); i >= 0; i--) { // Se llena el arreglo desde la última posición hasta el inicio, con el fin de mantener el orden de los elementos
                endArray[i] = nodo.getObject(); // Se asigna el objeto del nodo a la posición i del arreglo
                nodo = new NodoListaEnlazada(getPrevious(nodo)); // Se crea un nuevo nodo con el objeto del nodo previo
            }
        }
        return (T[]) endArray;
    }

    @Override
    public T pop() {
        NodoListaEnlazada<T> nodoCabeza = cabeza; // se guarda el nodo de cabeza
        cabeza = cabeza.getSiguiente(); // la cabeza se convierte en el siguiente
        cola.setSiguiente(cabeza);
        tamano--;
        return nodoCabeza.getObject(); // se elimina el nodo anterior
    }

    @Override
    public boolean remove(T object) {
        if (object != null) {
            try {
                if (isEmpty()) {
                    return false;
                } else {
                    Iterator iterador = this.iterator();
                    NodoListaEnlazada<T> nodo = null;
                    while (iterador.hasNext()) {
                        NodoListaEnlazada<T> nodoAnterior = nodo;
                        nodo = (NodoListaEnlazada<T>) iterador.next();
                        if (nodo.isEquals(object)) {
                            if (nodoAnterior == null) {
                                cabeza = nodo.getSiguiente();
                                cola.setSiguiente(cabeza);
                            } else {
                                if (nodo.getSiguiente().isEquals(cabeza.getObject())) {
                                    cola = nodoAnterior;
                                }
                                nodoAnterior.setSiguiente(nodo.getSiguiente());
                            }
                            tamano--;
                            return true;
                        }
                    }
                }
            } catch (Exception e) {

            }
        }
        return false;
    }

    @Override
    public boolean remove(NodeInterface<T> node) {
        return remove(node.getObject());
    }

    @Override
    public boolean removeAll(T[] objects) {
        if (objects.length > 0 && contains(objects)) {
            for (T object : objects) {
                remove(object);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean retainAll(T[] objects) {
        if (objects.length > 0) {
            try {
                T[] arrFinal = (T[]) new Object[objects.length];
                int pos = 0;
                for (T object : objects) {
                    if (contains(object)) {
                        arrFinal[pos] = object;
                        pos++;
                    }
                }
                clear();
                add(arrFinal);
                return true;
            } catch (Exception e) {

            }
        }
        return false;
    }

    @Override
    public int size() {
        return tamano;
    }

    @Override
    public ListaEnlazadaCircular<T> subList(NodeInterface<T> from, NodeInterface<T> to) {
        ListaEnlazadaCircular subList = new ListaEnlazadaCircular();
        if (!from.isEquals(null) && !to.isEquals(null) && validSubList(from, to)) { // Verifica que la sublista sea valida
            Iterator iterador = iterator();
            while (iterador.hasNext()) {
                NodoListaEnlazada nodo = (NodoListaEnlazada) iterador.next();
                if (nodo.isEquals(from.getObject())) { // "Si el nodo actual es el nodo 'from',
                    while (!nodo.isEquals(to.getObject())) { // mientras que el nodo actual no sea el nodo 'to',
                        subList.add(nodo.getObject()); // se añaden los nodos desde 'from' hasta antes del nodo 'to'"
                        nodo = (NodoListaEnlazada) iterador.next();
                    }
                    subList.add(nodo.getObject()); // Por último, se añade el nodo 'to'
                }
            }
        }
        return subList;
    }

    public boolean validSubList(NodeInterface<T> from, NodeInterface<T> to) {
        Iterator iterador = iterator();
        boolean firstFrom = false; // Verifica que el nodo from esté antes del nodo to
        boolean toFound = false;   // Verifica que el nodo to esté después del nodo from
        while (iterador.hasNext()) {
            NodoListaEnlazada nodo = (NodoListaEnlazada) iterador.next();
            if (nodo.isEquals(from.getObject()) && !toFound) { // "Si el nodo actual es el nodo 'from' y aún no se ha encontrado el nodo 'to',
                firstFrom = true; // "el nodo 'from' está antes que el nodo 'to'"
                System.out.println("From encontrado");
            }
            if (nodo.isEquals(to.getObject()) && firstFrom) { // "Si el nodo 'from' está antes que el nodo 'to' y el nodo actual es el nodo 'to',
                System.out.println("To encontrado");
                return true; // retorna true, verificando que la sublista es valida"
            }
        }
        return false;
    }

    @Override
    public T[] toArray() {
        NodoListaEnlazada nodo = cabeza;
        T[] arreglo = (T[]) new Object[tamano];
        for (int i = 0; i < tamano; i++) {
            arreglo[i] = (T) nodo.getObject();
            nodo = nodo.getSiguiente();
        }
        return arreglo;
    }

    @Override
    public boolean sortObjectsBySize() {
        if (size() == 1) {
            return true;
        }
        if (size() == 0) {
            return false;
        }
        T[] objects = toArray();
        for (int gap = objects.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < objects.length; i++) {
                T actual = objects[i];
                int j;
                for (j = i; j >= gap && objectToByteArray(objects[j - gap]).length > objectToByteArray(actual).length; j -= gap) {
                    objects[j] = objects[j - gap];
                }
                objects[j] = actual;
            }
        }
        clear();
        add(objects);
        return true;
    }

    private byte[] objectToByteArray(Object object) {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        if (object != null) {
            try (ObjectOutputStream ois = new ObjectOutputStream(boas)) {
                ois.writeObject(object);
                return boas.toByteArray();
            } catch (Exception e) {

            }
        }
        return new byte[0];
    }

    @Override
    public Iterator<NodeInterface<T>> iterator() {
        inode = cabeza;

        return new Iterator<NodeInterface<T>>() {
            int i = 0;
            @Override
            public boolean hasNext() {
                return i < size();
            }

            @Override
            public NodeInterface<T> next() {
                if (!hasNext()) { throw new NoSuchElementException(); }
                NodeInterface<T> node = inode;
                inode = inode.getSiguiente();
                i++;
                return node;
            }
        };
    }
}
