package Servidor;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

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

    public void crearCita(Cita nuevaCita){
        try {
            service = (InterfazRemota) Naming.lookup(uri);
            service.writeAppointment(nuevaCita.getCitaId(), nuevaCita.getEstudianteId(), nuevaCita.getDateTime());
        } catch (Exception e) {
        }
    }

    public void crearUsuario(Estudiante newUser, String password){
        try {
            service = (InterfazRemota) Naming.lookup(uri);
            service.writeStudentUser(newUser, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean isDataBase(String id){
        try {
            service = (InterfazRemota) Naming.lookup(uri);
            return service.isStudentRegistered(id);
        } catch (Exception e) {
        }
        return false;
    }

    public ListaEnlazada<Cita> leerCitasUsuario(String id){
        try {
            service = (InterfazRemota) Naming.lookup(uri);
            byte[] array = service.readAppointments(id);
            ByteArrayInputStream baInput = new ByteArrayInputStream(array);
            ObjectInputStream oInput = new ObjectInputStream(baInput);
            return (ListaEnlazada<Cita>) oInput.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        
    }

    public void cancelarCita(String citaId, String studentId){
        try {
            service = (InterfazRemota) Naming.lookup(uri);
            service.eraseAppointment(citaId, studentId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
