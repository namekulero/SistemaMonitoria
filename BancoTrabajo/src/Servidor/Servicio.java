package Servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Servicio extends UnicastRemoteObject implements InterfazRemota {

    protected Servicio() throws RemoteException {
        super();
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
