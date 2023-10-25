package Pantallas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.ColorUIResource;

import org.json.simple.parser.ParseException;

import Clases.Cita;
import Clases.Estudiante;
import Clases.Estructuras.interfaces.node.NodeInterface;
import Clases.Estructuras.linkedlist.ListaEnlazada;
import Cliente.Cliente;

public class PantallaRegistro extends JFrame {
    Cliente servicio;

    public PantallaRegistro() throws IOException {
        Properties config = new Properties();

        File archivo = new File("config.properties");
        String dir = archivo.getCanonicalPath();

        try (FileInputStream fin = new FileInputStream(new File(dir))) {
            config.load(fin);
            servicio = new Cliente((String) config.get("IP"), (String) config.get("PORT"), (String) config.get("SERVICENAME"));
        } catch (Exception e) {
        }
        initComponents();
    }

    public void initComponents() throws IOException {
        Color fondos = new ColorUIResource(255, 255, 255);
        Color fondos2 = new ColorUIResource(220, 220, 220);

        this.setVisible(true);
        this.setBounds(EXIT_ON_CLOSE, ABORT, 800, 600);

        JPanel principal = new JPanel();
        principal.setLayout(null);
        principal.setBackground(fondos2);
        this.add(principal);

        JTextField id = new JTextField("ID: ");
        id.setLayout(null);
        id.setBounds(270, 240, 200, 50);
        principal.add(id);

        JTextField password = new JTextField(" ");
        password.setLayout(null);
        password.setBounds(270, 340, 200, 50);
        principal.add(password);

        JLabel password1 = new JLabel("PASSWORD.");
        password1.setVisible(true);
        password1.setBounds(270, 330, 100, 50);
        principal.add(password1);

        JLabel inst = new JLabel("INGRESE ID Y CONTRASEÑA PARA SU REGISTRO");
        inst.setBounds(230, 140, 400, 35);
        principal.add(inst);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 800, 100);
        panel.setLayout(null);
        panel.setBackground(fondos);
        principal.add(panel);

        JLabel imagenLogo = new JLabel();
        imagenLogo.setIcon(new ImageIcon(getClass().getResource("iconos/Logo (Custom).png")));
        imagenLogo.setLayout(null);
        imagenLogo.setVisible(true);
        imagenLogo.setBounds(0, 0, 200, 100);
        panel.add(imagenLogo);

        JButton registro = new JButton("TERMINAR");
        registro.setLayout(null);
        registro.setVisible(true);
        registro.setBackground(Color.red);
        registro.setForeground(Color.white);
        registro.setBounds(270, 430, 200, 50);
        principal.add(registro);
        registro.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Estudiante estudiante = servicio.readStudentUser(id.getText(), password.getText());
                if (estudiante != null) {
                    try {
                        ListaEnlazada<Cita> listaCitas = servicio.readAppointments(estudiante.getId());
                        Iterator<NodeInterface<Cita>> iterador = listaCitas.iterator();
                        long menorDiferencia = 0;
                        Cita citaCercana = null;
                        while (iterador.hasNext()) {
                            Cita citaActual = iterador.next().getObject();
                            String fechaCita = citaActual.getDateTime();
                            int year = Integer.valueOf(fechaCita.substring(0, 3));
                            int month = Integer.valueOf(fechaCita.substring(5, 6));
                            int day = Integer.valueOf(fechaCita.substring(8, 9));
                            int hour = Integer.valueOf(fechaCita.substring(11, 12));
                            int minute = Integer.valueOf(fechaCita.substring(14, 15));
                            int second = Integer.valueOf(fechaCita.substring(17, 18));
                            LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute, second);

                            long diferenciaEpoch = LocalDateTime.now().toEpochSecond(null) - dateTime.toEpochSecond(null);
                            if (diferenciaEpoch > 0 && citaCercana == null) {
                                menorDiferencia = diferenciaEpoch;
                                citaCercana = citaActual;
                            }
                            if (diferenciaEpoch > 0 && diferenciaEpoch < menorDiferencia) {
                                menorDiferencia = diferenciaEpoch;
                                citaCercana = citaActual;
                            }
                        }
                        int prioridad = 3;
                        if (estudiante.hasDiscapacidad() && estudiante.getSemestre() == 1) {
                            prioridad = 0;
                        } else if (estudiante.hasDiscapacidad()) {
                            prioridad = 1;
                        } else if (estudiante.getSemestre() == 1) {
                            prioridad = 2;
                        }
                        servicio.receiveAppointment(citaCercana, prioridad);
                    } catch (IOException | ParseException e1) {
                    }
                }
            }

        });

    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new PantallaRegistro().setVisible(true);
                } catch (IOException e) {
                }
            }
        });
    }
}
