package com.Clases.Estructuras.stack;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import com.Clases.Estructuras.interfaces.stack.StackInterface;

public class PilaEstatica<T> implements StackInterface<T> {
    private int maximo;
    private int tamano = 0;
    private T[] arreglo = (T[]) new Object[maximo];

    public PilaEstatica(int maximo) {
        this.maximo = maximo;
        arreglo = (T[]) new Object[maximo];
    }

    @Override
    public boolean clear() {
        if (!isEmpty()) {
            tamano = 0;
            arreglo = (T[]) new Object[maximo];
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
            return arreglo[tamano - 1];
        }
        return null;
    }

    @Override
    public T pop() {
        if (!isEmpty()) {
            T objeto = arreglo[tamano - 1];
            arreglo[tamano - 1] = null;
            tamano--;
            return objeto;
        }
        return null;
    }

    @Override
    public boolean push(T object) {
        if (tamano != maximo) {
            arreglo[tamano - 1] = object;
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
        for (int i = 0; pos < tamano; i++) {
            if (arreglo[pos].equals(object)) {
                return true;
            }
            pos++;
        }
        return false;
    }

    @Override
    public boolean sort() {
        if (!isEmpty()) {
            Pila<T> pila1 = new Pila<>();
            Pila<T> pila2 = new Pila<>();
            for (int i = 0; i < tamano; i++) {
                T objetoActual = pop();
                while ((!pila1.isEmpty())
                        && (objectToByteArray(objetoActual).length > objectToByteArray(pila1.peek()).length)) {
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
        if (!isEmpty()) {
            int pos = 0;
            T[] nuevoArreglo = (T[]) new Object[maximo];
            for (int i = tamano - 1; pos >= 0; i--) {
                nuevoArreglo[pos] = arreglo[i];
                pos++;
            }
            arreglo = nuevoArreglo;
            return true;
        }
        return false;
    }
}
