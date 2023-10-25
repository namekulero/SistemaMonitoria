package Cliente;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.IOException;
import org.json.simple.parser.ParseException;

import Clases.Cita;

public interface InterfazRemota extends Remote {

    public byte[] readStudentUser(String id, String password) throws RemoteException, IOException, ParseException;

    public byte[] readAppointments(String id) throws RemoteException, IOException, ParseException;

    public void receiveAppointment(Cita cita, int prioridad) throws RemoteException;

}
