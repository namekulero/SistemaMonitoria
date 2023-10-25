package Clases;

public class Estudiante {
    String nombres, apellidos;
    String id;

    public Estudiante(String nombres, String apellidos, String id) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.id = id;
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
}
