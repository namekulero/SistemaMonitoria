package com.Clases.Estructuras.stack;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.Clases.Estructuras.interfaces.stack.StackInterface;

public class PilaArray<T> implements StackInterface<T> {
    private T[] arreglo;
    private int tamano;

    @Override
    public boolean clear() {
        arreglo = (T[]) new Object[0];
        tamano = 0;
        return true;
    }

    @Override
    public boolean isEmpty() {
        return tamano == 0;
    }

    @Override
    public T peek() {
        if (!isEmpty()) {
            return arreglo[0];
        }
        return null;
    }

    @Override
    public T pop() {
        if (!isEmpty()) {
            try {
                tamano--;
                T[] arrTemporal = (T[]) new Object[tamano];
                T objPos = arreglo[tamano];
                for (int i = 0; i < tamano; i++) {
                    arrTemporal[i] = arreglo[i];
                }
                arreglo = arrTemporal;
                return objPos;
            } catch (Exception e) {
            }
        }
        return null;
    }

    @Override
    public boolean push(T object) {
        try {
            T[] arrTemporal = (T[]) new Object[tamano + 1];
            arrTemporal[tamano] = object;
            for (int i = 0; i < tamano; i++) {
                arrTemporal[i] = arreglo[i];
            }
            arreglo = arrTemporal;
            tamano++;
            return true;
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, e.getMessage(), e);
        }
        return false;
    }

    @Override
    public int size() {
        return tamano;
    }

    @Override
    public boolean search(T object) {
        for (int i = 0; i < tamano; i++) {
            if (object.equals(arreglo[i])) {
                return true;
            }
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
        try {
            PilaArray<T> pila = new PilaArray<>();
            int tam = tamano;
            for (int i = 0; i < tam; i++) {
                pila.push(pop());
            }
            tamano = tam;
            arreglo = pila.arreglo;
            return true;
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, e.getMessage(), e);
        }
        return false;
    }

}
