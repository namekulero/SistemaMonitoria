package Servidor;

import java.rmi.RemoteException;
import java.io.IOException;
import org.json.simple.parser.ParseException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.commons.codec.digest.DigestUtils;
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
    public String readStudentUser(String id, String password) throws RemoteException, IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        password = DigestUtils.sha1Hex(password);

        File archivo = new File("estudiantes\\" + id + ".json");
        String dir = archivo.getCanonicalPath();

        try (FileReader reader = new FileReader(dir)) {
            Object obj = jsonParser.parse(reader);

            JSONObject usuario = (JSONObject) obj;
            String filePass = (String) usuario.get("password");
            if (filePass != null && filePass.equals(password)) {
                return (String) usuario.get("nombre");
            }
        }
        return "";
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

}
