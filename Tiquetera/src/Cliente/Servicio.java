package Cliente;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.json.simple.parser.ParseException;

import Clases.Estudiante;

public class Servicio extends UnicastRemoteObject implements InterfazRemota {

    protected Servicio() throws RemoteException {
        super();
    }

    @Override
    public byte[] readAppointments(String id) throws RemoteException, IOException, ParseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readAppointments'");
    }

    @Override
    public byte[] readStudentUser(String id, String password) throws RemoteException, IOException, ParseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readStudentUser'");
    }

}
