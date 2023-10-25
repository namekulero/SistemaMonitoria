package Cliente;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import Clases.Estudiante;

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

    public boolean deployDatosJSON() {
        try {
            System.setProperty("java.rmi.server.hostname", ip);
            InterfazRemota service = (InterfazRemota) new Servicio();
            LocateRegistry.createRegistry(Integer.parseInt(port));
            Naming.rebind(uri, service);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Estudiante readStudentUser(String id, String password) {
        try {
            service = (InterfazRemota) Naming.lookup(uri);
            ByteArrayInputStream bs = new ByteArrayInputStream(service.readStudentUser(id, password));
            ObjectInputStream is = new ObjectInputStream(bs);
            Estudiante estudiante = (Estudiante) is.readObject();
            is.close();
            return estudiante;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new Estudiante(password, id, id, 0, false);
    }

    public JSONArray readAppointments(String id) throws RemoteException, IOException, ParseException {
        try {
            service = (InterfazRemota) Naming.lookup(uri);
            ByteArrayInputStream bs = new ByteArrayInputStream(service.readAppointments(id));
            ObjectInputStream is = new ObjectInputStream(bs);
            JSONArray citasEstudiante = (JSONArray) is.readObject();
            is.close();
            return citasEstudiante;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
