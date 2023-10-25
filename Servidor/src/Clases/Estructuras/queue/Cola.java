package com.Clases.Estructuras.queue;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import com.Clases.Estructuras.interfaces.queue.QueueInterface;
import com.Clases.Estructuras.linkedlist.ListaEnlazada;

public class Cola<T> implements QueueInterface<T> {
    public ListaEnlazada<T> lista = new ListaEnlazada<>();

    @Override
    public boolean clear() {
        return lista.clear();
    }

    @Override
    public boolean isEmpty() {
        return lista.isEmpty();
    }

    @Override
    public T peek() {
        return lista.get();
    }

    @Override
    public T extract() {
        return lista.pop();
    }

    @Override
    public boolean insert(T object) {
        return lista.add(object);
    }

    @Override
    public int size() {
        return lista.size();
    }

    @Override
    public boolean search(T object) {
        return lista.contains(object);
    }

    @Override
    public boolean sort() {
        if (!isEmpty()) {
            Cola<T> cola1 = new Cola<>();
            Cola<T> cola2 = new Cola<>();
            int tamano = size();

            for (int i = 0; i < tamano; i++) {
                T objetoActual = extract();
                while ((!cola1.isEmpty()) && (objectToByteArray(objetoActual).length > objectToByteArray(cola1.peek()).length)) {
                    cola2.insert(cola1.extract());
                }
                cola1.insert(objetoActual);
                while (!cola2.isEmpty()) {
                    cola1.insert(cola2.extract());
                }
            } 
            while (!cola1.isEmpty()) {
                insert(cola1.extract());
            }

            return true;
        }
        return false;
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
    public boolean reverse() {
        Cola<T> nuevaCola = new Cola<>();
        while (!lista.isEmpty()) {
            nuevaCola.insert(lista.getFromEnd());
            lista.remove(lista.getFromEnd());
        }
        lista = nuevaCola.lista;
        return true;
    }

}
