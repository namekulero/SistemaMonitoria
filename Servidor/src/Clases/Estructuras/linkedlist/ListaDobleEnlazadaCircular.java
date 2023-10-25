package com.Clases.Estructuras.linkedlist;

import com.Clases.Estructuras.interfaces.linkedlist.LinkedListInterface;
import com.Clases.Estructuras.interfaces.node.NodeInterface;
import com.Clases.Estructuras.node.NodoDobleEnlazado;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListaDobleEnlazadaCircular<T> implements LinkedListInterface<T>, Serializable {
    private NodoDobleEnlazado<T> cabeza;
    private NodoDobleEnlazado<T> cola;
    private NodoDobleEnlazado<T> inode;
    private int tamano;

    @Override
    public boolean add(T object) { // CONFIRMADO
        if (object != null) {
            try {
                if (isEmpty()) {
                    cabeza = cola = new NodoDobleEnlazado<T>(object);
                } else {
                    cola.setNext(new NodoDobleEnlazado<T>(object));
                    cola.getNext().setPrevious(cola);
                    cola = cola.getNext();
                }
                cabeza.setPrevious(cola);
                cola.setNext(cabeza);
                tamano++;
                return true;
            } catch (Exception e) {
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING, e.getMessage(), e);
            }
        }
        return false;
    }

    @Override
    public boolean add(NodeInterface<T> node, T object) { // CONFIRMADO
        if (object != null) {
            try {
                if (isEmpty()) {
                    return false;
                }
                if (cola.isEquals(node.getObject())) {
                    add(object);
                } else {
                    NodoDobleEnlazado nodeTemp;
                    Iterator iterator = iterator();
                    while (iterator.hasNext()) {
                        nodeTemp = (NodoDobleEnlazado) iterator.next();
                        if (nodeTemp.isEquals(node.getObject())) {
                            NodoDobleEnlazado nodeToAdd = new NodoDobleEnlazado<T>(object, nodeTemp.getNext(),
                                    nodeTemp);
                            nodeTemp.setNext(nodeToAdd);
                            nodeToAdd.getNext().setPrevious(nodeToAdd);
                        }
                    }
                    tamano++;
                }
                return true;
            } catch (Exception e) {
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING, e.getMessage(), e);
            }
        }
        return false;
    }

    @Override
    public boolean add(NodeInterface<T> node, NodeInterface<T> next) {
        return add(node, next.getObject());
    }

    @Override
    public boolean add(T[] objects) {
        if (objects.length > 0) {
            try {
                for (T object : objects) {
                    add(object);
                }
                return true;
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return false;
    }

    @Override
    public boolean add(NodeInterface<T> node, T[] objects) {
        if (objects.length > 0) {
            try {
                for (T object : objects) {
                    add(node, object);
                }
                return true;
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return false;
    }

    @Override
    public boolean addOnHead(T object) {
        if (object != null) {
            try {
                if (isEmpty()) {
                    add(object);
                } else {
                    NodoDobleEnlazado newNode = new NodoDobleEnlazado(object, cabeza);
                    cabeza.setPrevious(newNode);
                    cabeza = newNode;
                    cabeza.setPrevious(cola);
                    cola.setNext(cabeza);
                    tamano++;
                }
                return true;
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return false;
    }

    @Override
    public boolean addOnHead(T[] objects) {
        for (T object : objects) {
            if (object != null) {
                addOnHead(object);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean clear() {
        cabeza = cola = null;
        tamano = 0;
        return true;
    }

    @Override
    public ListaDobleEnlazadaCircular<T> cloneList() {
        try {
            if (!isEmpty()) {
                return (ListaDobleEnlazadaCircular<T>) this.clone();
            } else {
                return new ListaDobleEnlazadaCircular<>();
            }
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public boolean contains(T object) {
        if (!isEmpty()) {
            if (object != null) {
                Iterator iter = iterator();
                while (iter.hasNext()) {
                    NodoDobleEnlazado nextNode = (NodoDobleEnlazado) iter.next();
                    if (nextNode.isEquals(object)) {
                        return true;
                    }
                }
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
        if (object != null) {
            try {
                Iterator iter = iterator();
                while (iter.hasNext()) {
                    NodoDobleEnlazado nextNode = (NodoDobleEnlazado) iter.next();
                    if (nextNode.isEquals(object)) {
                        return nextNode;
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return new NodoDobleEnlazado<>(null);
    }

    @Override
    public boolean isEmpty() {
        return tamano == 0;
    }

    @Override
    public T get() {
        if (!isEmpty()) {
            return cabeza.getObject();
        }
        return null;
    }

    @Override
    public T[] get(int n) {
        T[] arrReturn = (T[]) new Object[n];
        if (!isEmpty()) {
            if (size() >= n) {
                Iterator iter = iterator();
                for (int i = 0; i < n; i++) {
                    NodoDobleEnlazado<T> nodeArr = (NodoDobleEnlazado<T>) iter.next();
                    arrReturn[i] = nodeArr.getObject();
                }
            }
        }

        return arrReturn;
    }

    @Override
    public T getPrevious(NodeInterface<T> node) {
        try {
            if (!isEmpty()) {
                node = (NodeInterface<T>) nodeOf(node.getObject());
                return ((NodoDobleEnlazado<T>) node).getPrevious().getObject();
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public T getFromEnd() {
        if (!isEmpty()) {
            return cola.getObject();
        }
        return null;
    }

    @Override
    public T[] getFromEnd(int n) {
        T[] arrTail = (T[]) new Object[n];
        NodoDobleEnlazado<T> nodeTemp = cola;
        if (size() >= n) {
            for (int i = n - 1; i <= 0; i--) {
                arrTail[i] = nodeTemp.getObject();
                nodeTemp = nodeTemp.getPrevious();
            }
        }
        return arrTail;
    }

    @Override
    public T pop() {
        if (!isEmpty()) {
            NodoDobleEnlazado<T> nodeTemp = cabeza;
            cabeza = cabeza.getNext();
            cabeza.setPrevious(cola);
            tamano--;
            return nodeTemp.getObject();
        }
        return null;
    }

    @Override
    public boolean remove(T object) {
        NodoDobleEnlazado<T> nodeTemp;
        if (object != null && !isEmpty() && (contains(object))) {
            try {
                if (cabeza.isEquals(object)) {
                    pop();
                } else if (cola.isEquals(object)) {
                    cola = cola.getPrevious();
                    cola.setNext(cabeza);
                    tamano--;
                }
                Iterator iter = iterator();
                while (iter.hasNext()) {
                    nodeTemp = (NodoDobleEnlazado<T>) iter.next();
                    if (nodeTemp.isEquals(object)) {
                        nodeTemp.getPrevious().setNext(nodeTemp.getNext());
                        nodeTemp.getNext().setPrevious(nodeTemp.getPrevious());
                        tamano--;
                    }
                }
                return true;
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING, ex.getMessage(), ex);
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
        if (!isEmpty() && contains(objects)) {
            try {
                for (T object : objects) {
                    if (object != null) {
                        remove(object);
                    }
                }
                return true;
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING, ex.getMessage(), ex);
            }
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
    public ListaDobleEnlazadaCircular<T> subList(NodeInterface<T> from, NodeInterface<T> to) {
        ListaDobleEnlazadaCircular subList = new ListaDobleEnlazadaCircular();
        if (!from.isEquals(null) && !to.isEquals(null) && validSubList(from, to)) { // Verifica que la sublista sea
                                                                                    // valida
            Iterator iterador = iterator();
            while (iterador.hasNext()) {
                NodoDobleEnlazado nodo = (NodoDobleEnlazado) iterador.next();
                if (nodo.isEquals(from.getObject())) { // "Si el nodo actual es el nodo 'from',
                    while (!nodo.isEquals(to.getObject())) { // mientras que el nodo actual no sea el nodo 'to',
                        subList.add(nodo.getObject()); // se añaden los nodos desde 'from' hasta antes del nodo 'to'"
                        nodo = (NodoDobleEnlazado) iterador.next();
                    }
                    subList.add(nodo.getObject()); // Por último, se añade el nodo 'to'
                }
            }
        }
        return subList;
    }

    private boolean validSubList(NodeInterface<T> from, NodeInterface<T> to) {
        Iterator iterador = iterator();
        boolean firstFrom = false; // Verifica que el nodo from esté antes del nodo to
        boolean toFound = false; // Verifica que el nodo to esté después del nodo from
        while (iterador.hasNext()) {
            NodoDobleEnlazado nodo = (NodoDobleEnlazado) iterador.next();
            if (nodo.isEquals(from.getObject()) && !toFound) { // "Si el nodo actual es el nodo 'from' y aún no se ha
                                                               // encontrado el nodo 'to',
                firstFrom = true; // "el nodo 'from' está antes que el nodo 'to'"
            }
            if (nodo.isEquals(to.getObject()) && firstFrom) { // "Si el nodo 'from' está antes que el nodo 'to' y el
                                                              // nodo actual es el nodo 'to',
                return true; // retorna true, verificando que la sublista es valida"
            }
        }
        return false;
    }

    @Override
    public T[] toArray() {
        NodoDobleEnlazado nodo = cabeza;
        T[] arreglo = (T[]) new Object[tamano];
        for (int i = 0; i < tamano; i++) {
            arreglo[i] = (T) nodo.getObject();
            nodo = nodo.getNext();
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
                for (j = i; j >= gap
                        && objectToByteArray(objects[j - gap]).length > objectToByteArray(actual).length; j -= gap) {
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
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                NodeInterface<T> node = inode;
                inode = inode.getNext();
                i++;
                return node;
            }
        };
    }

    public Iterator<NodeInterface<T>> iteratorFromBack() {
        inode = cola;

        return new Iterator<NodeInterface<T>>() {
            int i = 0;
            @Override
            public boolean hasNext() {
                return i < size();
            }

            @Override
            public NodeInterface<T> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                NodeInterface<T> node = inode;
                inode = inode.getPrevious();
                i++;
                return node;
            }
        };
    }
}
