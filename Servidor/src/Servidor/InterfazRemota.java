package Servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.IOException;
import org.json.simple.parser.ParseException;

import Clases.Cita;
import Clases.Estudiante;

public interface InterfazRemota extends Remote {

    public boolean isStudentRegistered(String id) throws RemoteException, IOException, ParseException;

    public byte[] readStudentUser(String id, String password) throws RemoteException, IOException, ParseException;

    public void writeStudentUser(Estudiante estudiante, String password) throws RemoteException, IOException, ParseException;

    public byte[] readAppointments(String id) throws RemoteException, IOException, ParseException;

    public void writeAppointment(String appointmentId, String studentId, String dateTime) throws RemoteException, IOException, ParseException;

    public void eraseAppointment(String appointmentId, String studentId) throws RemoteException, IOException, ParseException;
    
    public int receiveAppointment(Cita cita, int prioridad) throws RemoteException;

    public int dequeueAppointment(String idModulo) throws RemoteException;

    public String getNewId() throws RemoteException;

}
