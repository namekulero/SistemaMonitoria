package Pantallas;

import javax.swing.plaf.ColorUIResource;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PantallaTurno extends JFrame {
    int turno1;

    public PantallaTurno(int turno1) throws IOException {
        this.turno1 = turno1;
        iniciarComponentes();
    }

    public void iniciarComponentes() throws IOException {

        Color fondos = new ColorUIResource(255, 255, 255);
        Color fondos2 = new ColorUIResource(220, 220, 220);

        this.setBounds(100, 100, 800, 600);
        this.setVisible(true);
        this.setLayout(null);

        JPanel principal = new JPanel();
        principal.setBounds(0, 0, 800, 600);
        principal.setBackground(fondos2);
        principal.setLayout(null);
        principal.setVisible(true);
        this.add(principal);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 800, 100);
        panel.setVisible(true);
        panel.setLayout(null);
        panel.setBackground(fondos);
        principal.add(panel);

        JLabel instruccion = new JLabel("SU TURNO ES: ");
        instruccion.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        instruccion.setBounds(340, 150, 200, 100);
        instruccion.setVisible(true);
        principal.add(instruccion);

        JLabel turno = new JLabel(String.valueOf(turno1));
        turno.setFont(new Font("Times New Roman", Font.BOLD, 18));
        turno.setBounds(380, 200, 200, 200);
        turno.setVisible(true);
        principal.add(turno);

        JLabel imagenLogo = new JLabel();
        imagenLogo.setIcon(new ImageIcon(getClass().getResource("/iconos/Logo (Custom).png")));
        imagenLogo.setLayout(null);
        imagenLogo.setVisible(true);
        imagenLogo.setBounds(0, 0, 200, 100);
        panel.add(imagenLogo);

        JButton finalizar = new JButton("FINALIZAR");
        finalizar.setBounds(295, 400, 200, 50);
        finalizar.setBackground(Color.red);
        finalizar.setForeground(fondos2);
        principal.add(finalizar);
        finalizar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new PantallaTiquetera();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                dispose();
            }

        });
    }

}
