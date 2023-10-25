package Pantallas;

import javax.swing.*;

import Cliente.Cliente;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class PantallaModulo extends JFrame {
    String idModulo;
    Cliente cliente;
    
    public PantallaModulo() throws IOException {
        Properties config = new Properties();

        File archivo = new File("config.properties");
        String dir = archivo.getCanonicalPath();

        try (FileInputStream fin = new FileInputStream(new File(dir))) {
            config.load(fin);
            cliente = new Cliente((String) config.get("IP"), (String) config.get("PORT"), (String) config.get("SERVICENAME"));
        } catch (Exception e) {
        }
        idModulo = "M" + String.valueOf(cliente.getNewId());
        iniciarComponentes();
        setTitle("Banco del módulo");
        setLocationRelativeTo(null);
        setResizable(false);
        this.setMaximizedBounds(getBounds());
    }

    public void iniciarComponentes() {
        JPanel panelFondo = new JPanel();
        panelFondo.setBackground(new Color(0, 151, 157));
        panelFondo.setVisible(true);
        panelFondo.setSize(500, 500);
        panelFondo.setLayout(null);

        JLabel labelNombre = new JLabel("Módulo:");
        labelNombre.setBounds(15, 10, 100, 40);
        panelFondo.add(labelNombre);
        JLabel labelNombre2 = new JLabel(idModulo);
        labelNombre2.setBounds(115, 10, 50, 40);
        panelFondo.add(labelNombre2);

        JButton buttonDesencolar = new JButton("Desencolar");
        buttonDesencolar.setBounds(120, 80, 110, 40);
        panelFondo.add(buttonDesencolar);

        JLabel labelTurno = new JLabel("Turno:");
        labelTurno.setBounds(15, 140, 75, 40);
        panelFondo.add(labelTurno);
        JLabel labelTurno2 = new JLabel();

        buttonDesencolar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    labelTurno2.setText(String.valueOf(cliente.dequeueAppointment(idModulo)));
                } catch (RemoteException e1) {
                }
            }
        });

        labelTurno2.setBounds(90, 140, 50, 40);
        panelFondo.add(labelTurno2);

        add(panelFondo);
        setVisible(true);
        setSize(350, 200);
    }
}
