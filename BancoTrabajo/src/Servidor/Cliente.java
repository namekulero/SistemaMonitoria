package Servidor;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import java.rmi.RemoteException;

public class Cliente {
    private InterfazRemota service;
    private String ip;
    private String port;
    private String serviceName;
    private String uri;

    public Cliente(String ip, String port, String serviceName) {
        this.ip = ip;
        this.port = port;
        this.serviceName = serviceName;
        this.uri = "//" + this.ip + ":" + this.port + "/" + this.serviceName;
    }

    public boolean deploy() {
        try {
            System.setProperty("java.rmi.server.hostname", ip);
            InterfazRemota service = new Servicio();
            LocateRegistry.createRegistry(Integer.parseInt(port));
            Naming.rebind(uri, service);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int dequeueAppointment(String idModulo) throws RemoteException {
        try {
            service = (InterfazRemota) Naming.lookup(uri);
            return service.dequeueAppointment(idModulo);
        } catch (Exception e) {
        }
        return 0;
    }

    public String getNewId() {
        try {
            service = (InterfazRemota) Naming.lookup(uri);
            return service.getNewId();
        } catch (Exception e) {
        }
        return "";
    }
}
