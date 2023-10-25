package Servidor;

import java.rmi.RemoteException;
import java.io.IOException;
import org.json.simple.parser.ParseException;

import Clases.Cita;
import Clases.Estructuras.interfaces.node.NodeInterface;
import Clases.Estructuras.linkedlist.ListaEnlazada;

import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Servicio extends UnicastRemoteObject implements InterfazRemota {

    protected Servicio() throws RemoteException {
        super();
    }

    @Override
    public String[] readStudentUser(String id, String password) throws RemoteException, IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        password = DigestUtils.sha1Hex(password);

        File archivo = new File("estudiantes\\" + id + ".json");
        String dir = archivo.getCanonicalPath();

        try (FileReader reader = new FileReader(dir)) {
            Object obj = jsonParser.parse(reader);

            JSONObject usuario = (JSONObject) obj;
            String filePass = (String) usuario.get("password");
            if (filePass != null && filePass.equals(password)) {
                String[] arrayNombre = new String[2];
                arrayNombre[0] = (String) usuario.get("nombre");
                arrayNombre[1] = (String) usuario.get("apellidos");
                return arrayNombre;
            }
        }
        return new String[0];
    }

    @Override
    public void writeStudentUser(String id, String nombres, String apellidos, String password) throws IOException {
        password = DigestUtils.sha1Hex(password);

        File archivo = new File("estudiantes\\" + id + ".json");
        String dir = archivo.getCanonicalPath();

        JSONObject userObject = new JSONObject();
        userObject.put("nombres", nombres);
        userObject.put("apellidos", apellidos);
        userObject.put("password", password);

        try (FileWriter file = new FileWriter(dir)) {
            file.write(userObject.toJSONString());
            file.flush();
        }
    }

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
        }
    }

    public ListaEnlazada<Cita> readAppointmentsList(String id) throws RemoteException, IOException, ParseException {
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
        }
    }

    private Cita parseCitaObject(JSONObject ci, String id) {
        Cita nuevaCita = new Cita((String) ci.get("id"), id, (String) ci.get("fecha"));
        return nuevaCita;
    }

    @Override
    public void writeAppointment(String appointmentId, String studentId, String dateTime) throws RemoteException, IOException, ParseException {
        ListaEnlazada<Cita> listaCitas = readAppointmentsList(studentId);
        Cita nuevaCita = new Cita(appointmentId, studentId, dateTime);
        listaCitas.add(nuevaCita);

        JSONArray arrayCitas = new JSONArray();
        Iterator<NodeInterface<Cita>> iterador = listaCitas.iterator();
        while (iterador.hasNext()) {
            Cita citaActual = iterador.next().getObject();
            JSONObject objetoCita = new JSONObject();
            objetoCita.put("id", citaActual.getCitaId());
            objetoCita.put("fecha", citaActual.getDateTime());
            arrayCitas.add(objetoCita);
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

    public void eraseAppointment(String appointmentId, String studentId) throws RemoteException, IOException, ParseException {
        ListaEnlazada<Cita> listaCitas = readAppointmentsList(studentId);
        JSONArray arrayCitas = new JSONArray();
        Iterator<NodeInterface<Cita>> iterador = listaCitas.iterator();
        while (iterador.hasNext()) {
            Cita citaActual = iterador.next().getObject();
            JSONObject objetoCita = new JSONObject();
            if (!citaActual.getCitaId().equals(appointmentId)) {
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

}
