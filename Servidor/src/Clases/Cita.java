package Clases;

import java.io.Serializable;

public class Cita implements Serializable {
    private static final long serialVersionUID = 101L;
    String citaId;
    String estudianteId;
    String dateTime;

    public Cita(String citaId, String estudianteId, String dateTime) {
        this.citaId = citaId;
        this.estudianteId = estudianteId;
        this.dateTime = dateTime;
    }

    public String getCitaId() {
        return citaId;
    }

    public String getEstudianteId() {
        return estudianteId;
    }

    public String getDateTime() {
        return dateTime;
    }
}
