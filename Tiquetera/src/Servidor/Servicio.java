package Servidor;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.json.simple.parser.ParseException;

import Clases.Cita;
import Clases.Estructuras.queue.ColaPrioridadCitas;

public class Servicio extends UnicastRemoteObject implements InterfazRemota {
    private ColaPrioridadCitas colaCitas = new ColaPrioridadCitas(4);
    private int turnoActual = 1;
    private int modulosActivos = 0;

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

    @Override
    public int receiveAppointment(Cita cita, int prioridad) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'receiveAppointment'");
    }

}
