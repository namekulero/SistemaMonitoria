package Clases.Estructuras.queue;

import Clases.Cita;
import Clases.Estructuras.interfaces.queue.QueueInterface;

public class ColaPrioridadCitas implements QueueInterface<Cita> {
    public int cantidad;
    public int tamano = 0;
    public ColaCitas[] colaPrioridad;
    
    public ColaPrioridadCitas(int cantidadPrioridad) {
        this.cantidad = cantidadPrioridad;
        colaPrioridad = new ColaCitas[cantidadPrioridad];
        for (int i = 0; i < cantidadPrioridad; i++) {
            colaPrioridad[i] = new ColaCitas();
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
    public Cita peek() {
        for (int i = cantidad-1; i >= 0; i++) {
            if (!colaPrioridad[i].isEmpty()) {
                return colaPrioridad[i].peek();
            }
        }
        return null;
    }

    @Override
    public Cita extract() {
        for (int i = 0; i < cantidad; i++) {
            if (!colaPrioridad[i].isEmpty()) {
                tamano--;
                return colaPrioridad[i].extract();
            }
        }
        return null;
    }

    @Override
    public boolean insert(Cita cita) {
        tamano++;
        return colaPrioridad[cantidad-1].insert(cita);
    }

    public boolean insert(Cita cita, int prioridad) {
        tamano++;
        if (prioridad >= 0 && prioridad < cantidad) {
            return colaPrioridad[prioridad].insert(cita);
        }
        return false;
    }

    @Override
    public int size() {
        return tamano;
    }

    @Override
    public boolean search(Cita cita) {
        for (int i = 0; i < cantidad; i++) {
            if (!colaPrioridad[i].isEmpty()) {
                if (colaPrioridad[i].search(cita)) {
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
