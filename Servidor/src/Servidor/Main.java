package Servidor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException {
        Properties config = new Properties();

        File archivo = new File("config.properties");
        String dir = archivo.getCanonicalPath();

        try (FileInputStream fin = new FileInputStream(new File(dir))) {
            config.load(fin);
            Servidor server = new Servidor((String) config.get("IP"), (String) config.get("PORT"), (String) config.get("SERVICENAME"));
            server.deployDatosJSON();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}