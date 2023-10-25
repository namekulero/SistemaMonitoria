package Servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.IOException;
import org.json.simple.parser.ParseException;

public interface InterfazRemota extends Remote {

    public String[] readStudentUser(String id, String password) throws RemoteException, IOException, ParseException;

    public void writeStudentUser(String id, String nombres, String apellidos, String password) throws RemoteException, IOException, ParseException;

    public byte[] readAppointments(String id) throws RemoteException, IOException, ParseException;

    public void writeAppointment(String appointmentId, String studentId, String dateTime) throws RemoteException, IOException, ParseException;

}
