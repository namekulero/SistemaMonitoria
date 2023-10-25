package Pantallas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.ColorUIResource;

public class PantallaTiquetera extends JFrame {

    public PantallaTiquetera() throws IOException {
        iniciarcomponentes();
    }

    public void iniciarcomponentes() throws IOException {

        Color fondos = new ColorUIResource(255, 255, 255);
        Color fondos2 = new ColorUIResource(220, 220, 220);
        this.setBounds(EXIT_ON_CLOSE, ABORT, 800, 600);
        this.setVisible(true);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setVisible(true);
        panelPrincipal.setLayout(null);
        panelPrincipal.setBounds(EXIT_ON_CLOSE, ABORT, 800, 600);
        panelPrincipal.setBackground(fondos2);
        this.add(panelPrincipal);

        JButton registro = new JButton("Registro");
        registro.setLayout(null);
        registro.setVisible(true);
        registro.setBackground(Color.red);
        registro.setForeground(Color.white);
        registro.setBounds(285, 300, 200, 50);
        registro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new PantallaRegistro();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                dispose();
            }
        });
        panelPrincipal.add(registro);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 800, 100);
        panel.setBackground(fondos);
        panel.setLayout(null);
        panelPrincipal.add(panel);

        JLabel imagenLogo = new JLabel();
        imagenLogo.setIcon(new ImageIcon(getClass().getResource("/iconos/Logo (Custom).png")));
        imagenLogo.setBounds(0, 0, 200, 100);
        panel.add(imagenLogo);

        JLabel instruccion = new JLabel();
        instruccion.setText("Registrese para tomar su turno.");
        instruccion.setBounds(290, 220, 400, 50);
        panelPrincipal.add(instruccion);

    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new PantallaTiquetera().setVisible(true);
                } catch (IOException e) {
                }
            }
        });
    }

}
