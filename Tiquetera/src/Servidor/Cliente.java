package Servidor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import org.json.simple.parser.ParseException;

import Clases.Cita;
import Clases.Estudiante;
import Clases.Estructuras.linkedlist.ListaEnlazada;

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
            System.out.println(estudiante.getId());
            is.close();
            return estudiante;

        } catch (Exception e) {
        }
        return null;
    }

    public ListaEnlazada<Cita> readAppointments(String id) throws RemoteException, IOException, ParseException {
        try {
            service = (InterfazRemota) Naming.lookup(uri);
            ByteArrayInputStream bs = new ByteArrayInputStream(service.readAppointments(id));
            ObjectInputStream is = new ObjectInputStream(bs);
            ListaEnlazada<Cita> listaCitas = (ListaEnlazada<Cita>) is.readObject();
            is.close();
            return listaCitas;

        } catch (Exception e) {
        }
        return null;
    }

    public int receiveAppointment(Cita citaCercana, int prioridad) {
        try {
            service = (InterfazRemota) Naming.lookup(uri);
            int turno = service.receiveAppointment(citaCercana, prioridad);
            return turno;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
