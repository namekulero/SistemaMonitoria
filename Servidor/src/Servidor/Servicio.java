package Servidor;

import java.rmi.RemoteException;
import java.io.IOException;
import org.json.simple.parser.ParseException;

import Clases.Cita;
import Clases.Estudiante;
import Clases.Estructuras.interfaces.node.NodeInterface;
import Clases.Estructuras.linkedlist.ListaEnlazada;
import Clases.Estructuras.queue.ColaPrioridadCitas;
import Pantallas.PantallaCola;

import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Servicio extends UnicastRemoteObject implements InterfazRemota {
    private ColaPrioridadCitas colaCitas = new ColaPrioridadCitas(4); // Cola con los turnos de las siguientes citas
    private int turnoActual = 0; // Valor del último turno dado
    private int modulosActivos = 0; // Cantidad de BancoTrabajos conectados al RMI
    private PantallaCola pantallaCola; // Pantalla con la cola de citas

    protected Servicio() throws RemoteException {
        super();
        pantallaCola = new PantallaCola(); // Inicializa la pantalla con la cola
    }

    // Método para buscar si existe un archivo con el estudiante registrado
    @Override
    public boolean isStudentRegistered(String id) throws RemoteException, IOException, ParseException {
        File archivo = new File("estudiantes\\e" + id + ".json");
        String dir = archivo.getCanonicalPath();

        try (FileReader reader = new FileReader(dir)) {
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    // Método para leer los datos almacenados sobre un estudiante y retornar un objeto Estudiante con tales datos
    @Override
    public byte[] readStudentUser(String id, String password) throws RemoteException, IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        password = DigestUtils.sha1Hex(password);

        File archivo = new File("estudiantes\\e" + id + ".json");
        String dir = archivo.getCanonicalPath();

        try (FileReader reader = new FileReader(dir)) {
            Object obj = jsonParser.parse(reader);

            JSONObject usuario = (JSONObject) obj;
            String filePass = (String) usuario.get("password");
            if (filePass != null && filePass.equals(password)) {
                Estudiante estudiante = new Estudiante((String) usuario.get("nombres"), (String) usuario.get("apellidos"), id,
                        Integer.parseInt((String) usuario.get("semestre")), Boolean.parseBoolean((String) usuario.get("hasDiscapacidad")));
                ByteArrayOutputStream boStream = new ByteArrayOutputStream();
                ObjectOutputStream ooStream = new ObjectOutputStream(boStream);
                ooStream.writeObject(estudiante);
                ooStream.close();
                return boStream.toByteArray();
            }
        }
        return null;
    }

    // Método para escribir los datos de un estudiante
    @Override
    public void writeStudentUser(Estudiante estudiante, String password) throws IOException {
        password = DigestUtils.sha1Hex(password);

        File archivo = new File("estudiantes\\e" + estudiante.getId() + ".json");
        String dir = archivo.getCanonicalPath();

        JSONObject userObject = new JSONObject();
        userObject.put("nombres", estudiante.getNombres());
        userObject.put("apellidos", estudiante.getApellidos());
        userObject.put("semestre", Integer.toString(estudiante.getSemestre()));
        userObject.put("hasDiscapacidad", Boolean.toString(estudiante.hasDiscapacidad()));
        userObject.put("password", password);
        userObject.put("citas", new JSONArray());
        try (FileWriter file = new FileWriter(dir)) {
            file.write(userObject.toJSONString());
            file.flush();
        }
    }

    // Método para leer las citas programadas de un estudiante (retorna un arreglo de bytes serializado)
    @Override
    public byte[] readAppointments(String id) throws RemoteException, IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        File archivo = new File("estudiantes\\e" + id + ".json");
        String dir = archivo.getCanonicalPath();
        try (FileReader reader = new FileReader(dir)) {
            Object obj = jsonParser.parse(reader);
            JSONObject objetoEstudiante = (JSONObject) obj;
            JSONArray arrayCitas = (JSONArray) objetoEstudiante.get("citas");
            ListaEnlazada<Cita> listaCitas = new ListaEnlazada<>();
            arrayCitas.forEach(ci -> listaCitas.add(parseCitaObject((JSONObject) ci, id)));

            ByteArrayOutputStream boStream = new ByteArrayOutputStream();
            ObjectOutputStream ooStream = new ObjectOutputStream(boStream);
            ooStream.writeObject(listaCitas);
            ooStream.close();
            return boStream.toByteArray();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Método para leer las citas programadas de un cliente (retorna la ListaEnlazada directamente)
    private ListaEnlazada<Cita> readAppointmentsList(String id) throws RemoteException, IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        File archivo = new File("estudiantes\\e" + id + ".json");
        String dir = archivo.getCanonicalPath();

        try (FileReader reader = new FileReader(dir)) {
            Object obj = jsonParser.parse(reader);
            JSONObject objetoEstudiante = (JSONObject) obj;
            JSONArray arrayCitas = (JSONArray) objetoEstudiante.get("citas");

            ListaEnlazada<Cita> listaCitas = new ListaEnlazada<>();
            arrayCitas.forEach(ci -> listaCitas.add(parseCitaObject((JSONObject) ci, id)));
            return listaCitas;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Método para convertir un objeto JSON en un objeto Cita
    private Cita parseCitaObject(JSONObject ci, String id) {
        Cita nuevaCita = new Cita((String) ci.get("id"), id, (String) ci.get("fecha"));
        return nuevaCita;
    }

    // Método para escribir una nueva cita para un estudiante
    @Override
    public void writeAppointment(String appointmentId, String studentId, String dateTime) throws RemoteException, IOException, ParseException {
        ListaEnlazada<Cita> listaCitas = readAppointmentsList(studentId);
        Cita nuevaCita = new Cita(appointmentId, studentId, dateTime);
        listaCitas.add(nuevaCita);

        JSONArray arrayCitas = new JSONArray();
        Iterator<NodeInterface<Cita>> iterador = listaCitas.iterator();
        if (listaCitas != null) {
            while (iterador.hasNext()) {
                Cita citaActual = iterador.next().getObject();
                JSONObject objetoCita = new JSONObject();
                objetoCita.put("id", citaActual.getCitaId());
                objetoCita.put("fecha", citaActual.getDateTime());
                arrayCitas.add(objetoCita);
            }
        }

        File archivo = new File("estudiantes\\e" + studentId + ".json");
        String dir = archivo.getCanonicalPath();

        JSONParser jsonParser = new JSONParser();
        JSONObject usuario;
        try (FileReader reader = new FileReader(dir)) {
            Object obj = jsonParser.parse(reader);

            usuario = (JSONObject) obj;
            usuario.put("citas", arrayCitas);
        }

        try (FileWriter file = new FileWriter(dir)) {
            file.write(usuario.toJSONString());
            file.flush();
        }
    }

    // Método para borrar una cita asignada a un estudiante
    @Override
    public void eraseAppointment(String appointmentId, String studentId) throws RemoteException, IOException, ParseException {
        ListaEnlazada<Cita> listaCitas = readAppointmentsList(studentId);
        JSONArray arrayCitas = new JSONArray();
        Iterator<NodeInterface<Cita>> iterador = listaCitas.iterator();
        while (iterador.hasNext()) {
            Cita citaActual = iterador.next().getObject();
            if (!citaActual.getCitaId().equals(appointmentId)) {
                JSONObject objetoCita = new JSONObject();
                objetoCita.put("id", citaActual.getCitaId());
                objetoCita.put("fecha", citaActual.getDateTime());
                arrayCitas.add(objetoCita);
            }
        }
        File archivo = new File("estudiantes\\e" + studentId + ".json");
        String dir = archivo.getCanonicalPath();

        JSONParser jsonParser = new JSONParser();
        JSONObject usuario;
        try (FileReader reader = new FileReader(dir)) {
            Object obj = jsonParser.parse(reader);
            usuario = (JSONObject) obj;
            usuario.put("citas", arrayCitas);
        }

        try (FileWriter file = new FileWriter(dir)) {
            file.write(usuario.toJSONString());
            file.flush();
        }
    }

    // Método para encolar a un estudiante
    @Override
    public int receiveAppointment(Cita cita, int prioridad) {
        turnoActual++;
        cita.setTurno(turnoActual);
        colaCitas.insert(cita, prioridad);
        pantallaCola.updateQueueSize(colaCitas.size());
        // colaCitas.sort();
        return cita.getTurno();
    }

    @Override
    public int dequeueAppointment(String idModulo) {
        int turno = colaCitas.extract().getTurno();
        pantallaCola.updateQueueSize(colaCitas.size());
        Object[] fila = new Object[2];
        fila[0] = turno;
        fila[1] = idModulo;
        pantallaCola.receiveFila(fila);
        return turno;
    }

    @Override
    public String getNewId() throws RemoteException {
        modulosActivos++;
        System.out.println(modulosActivos);
        return String.valueOf(modulosActivos);
    }

}
