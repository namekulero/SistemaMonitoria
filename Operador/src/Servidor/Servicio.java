package Servidor;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.json.simple.parser.ParseException;

import Clases.Cita;
import Clases.Estudiante;

public class Servicio extends UnicastRemoteObject  implements InterfazRemota {

    public Servicio() throws RemoteException {
        super();
        //TODO Auto-generated constructor stub
    }

    @Override
    public byte[] readStudentUser(String id, String password) throws RemoteException, IOException, ParseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readStudentUser'");
    }

    @Override
    public void writeStudentUser(Estudiante estudiante, String password)
            throws RemoteException, IOException, ParseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'writeStudentUser'");
    }

    @Override
    public byte[] readAppointments(String id) throws RemoteException, IOException, ParseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readAppointments'");
    }

    @Override
    public void writeAppointment(String appointmentId, String studentId, String dateTime)
            throws RemoteException, IOException, ParseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'writeAppointment'");
    }

    @Override
    public void eraseAppointment(String appointmentId, String studentId)
            throws RemoteException, IOException, ParseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eraseAppointment'");
    }

    @Override
    public void receiveAppointment(Cita cita, int prioridad) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'receiveAppointment'");
    }

    @Override
    public int dequeueAppointment(String idModulo) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dequeueAppointment'");
    }

    @Override
    public String getNewId() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNewId'");
    }
    
}
