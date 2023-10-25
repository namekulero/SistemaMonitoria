import javax.swing.plaf.ColorUIResource;
import javax.swing.*;
import java.awt.Color;
import java.io.IOException;

public class Turno extends JFrame {

    public Turno() throws IOException {
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
        instruccion.setBounds(350, 150, 200, 100);
        instruccion.setVisible(true);
        principal.add(instruccion);

        JLabel turno = new JLabel("HOLA");
        turno.setBounds(380, 200, 200, 200);
        turno.setVisible(true);
        principal.add(turno);

        JLabel imagenLogo = new JLabel();
        imagenLogo.setIcon(new ImageIcon(getClass().getResource("iconos/Logo (Custom).png")));
        imagenLogo.setLayout(null);
        imagenLogo.setVisible(true);
        imagenLogo.setBounds(0, 0, 200, 100);
        panel.add(imagenLogo);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Turno().setVisible(true);
                } catch (IOException e) {
                }
            }
        });
    }
}
