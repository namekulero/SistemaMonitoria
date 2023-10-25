package com.Clases.Estructuras.queue;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import com.Clases.Estructuras.interfaces.queue.QueueInterface;

public class ColaArray<T> implements QueueInterface<T> {
    private int tamano = 0;
    private T[] arreglo = (T[]) new Object[tamano];

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
    public T extract() {
        if (!isEmpty()) {
            try {
                T[] arrTemporal = (T[]) new Object[tamano - 1];
                T objPos = arreglo[0];
                for (int i = 0; i < tamano - 1; i++) {
                    arrTemporal[i] = arreglo[i + 1];
                }
                tamano--;
                arreglo = arrTemporal;
                return objPos;
            } catch (Exception e) {
            }
        }
        return null;
    }

    @Override
    public boolean insert(T object) {
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
            ColaArray<T> cola1 = new ColaArray<>();
            ColaArray<T> cola2 = new ColaArray<>();
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
        ColaArray<T> nuevaCola = new ColaArray<>();
        for (int i = tamano-1; i >= 0; i++) {
            nuevaCola.insert(arreglo[i]);
        }
        arreglo = nuevaCola.arreglo;
        return true;
    }
    
}
