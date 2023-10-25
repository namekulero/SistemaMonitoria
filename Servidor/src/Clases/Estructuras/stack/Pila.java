package com.Clases.Estructuras.stack;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import com.Clases.Estructuras.interfaces.stack.StackInterface;
import com.Clases.Estructuras.linkedlist.ListaEnlazada;

public class Pila<T> implements StackInterface<T> {
    private ListaEnlazada<T> lista = new ListaEnlazada<>();

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
    public T pop() {
        return lista.pop();
    }

    @Override
    public boolean push(T object) {
        return lista.addOnHead(object);
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
            Pila<T> pila1 = new Pila<>();
            Pila<T> pila2 = new Pila<>();
            int tamano = size();

            for (int i = 0; i < tamano; i++) {
                T objetoActual = pop();
                while ((!pila1.isEmpty()) && (objectToByteArray(objetoActual).length > objectToByteArray(pila1.peek()).length)) {
                    pila2.push(pila1.pop());
                }
                pila1.push(objetoActual);
                while (!pila2.isEmpty()) {
                    pila1.push(pila2.pop());
                }
            } 
            while (!pila1.isEmpty()) {
                push(pila1.pop());
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
        Pila<T> nuevaPila = new Pila<>();
        while (!lista.isEmpty()) {
            nuevaPila.push(pop());
        }
        lista = nuevaPila.lista;
        return true;
    }
    
}
