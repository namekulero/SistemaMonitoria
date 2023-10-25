import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;

import Cliente.Cliente;

public class Registro extends JFrame {
    Cliente servicio;

    public Registro() throws IOException {
        InitComponents();
    }

    public void InitComponents() throws IOException {

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
                servicio.readStudentUser(id.getText(), password.getText());
            }

        });

    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Registro().setVisible(true);
                } catch (IOException e) {
                }
            }
        });
    }
}
