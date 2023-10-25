package Servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfazRemota extends Remote {

    public int dequeueAppointment(String idModulo) throws RemoteException;

    public String getNewId() throws RemoteException;

}
