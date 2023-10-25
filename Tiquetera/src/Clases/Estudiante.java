package Clases;

import java.io.Serializable;

public class Estudiante implements Serializable {
    private static final long serialVersionUID = 100L;
    String nombres, apellidos;
    String id;
    int semestre;
    boolean hasDiscapacidad;

    public Estudiante(String nombres, String apellidos, String id, int semestre, boolean hasDiscapacidad) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.id = id;
        this.semestre = semestre;
        this.hasDiscapacidad = hasDiscapacidad;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getId() {
        return id;
    }

    public int getSemestre() {
        return semestre;
    }

    public boolean hasDiscapacidad() {
        return hasDiscapacidad;
    }

}
