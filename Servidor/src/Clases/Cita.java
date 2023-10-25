package Clases;

public class Cita {
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
