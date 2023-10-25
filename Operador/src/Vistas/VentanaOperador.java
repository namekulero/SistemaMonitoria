package Vistas;

import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicArrowButton;

public class VentanaOperador extends JFrame{
    

    public VentanaOperador(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Operador");
        setSize(1200, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        initComponents();

        
    }

    public void initComponents(){
        panelPrincipal = new JPanel(null);
        getContentPane().add(panelPrincipal);
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setSize(1200, 700);
        
        navBar = new JPanel(null);
        navBar.setSize(1200, 100);
        panelPrincipal.add(navBar);
        navBar.setBackground(Color.WHITE);

        logoUpb = new JLabel();
        logoUpb.setIcon(new ImageIcon(getClass().getResource("/Vistas/Icons/logoUPB.png")));
        logoUpb.setSize(246,100);
        logoUpb.setLocation(10, 10);
        navBar.add(logoUpb);

        /* Propiedades
         */
        fontButtons = new Font("Roboto", 0, 20);
        backColor = new Color(245, 245, 245);
        //

        tabbedPaneGlobal = new JTabbedPane();
        tabbedPaneGlobal.setSize(1200, 600);
        tabbedPaneGlobal.setLocation(0, 60);

        panelInicial = new JPanel(null);
        panelInicial.setSize(1200, 600);
        panelPrincipal.add(tabbedPaneGlobal);
        panelInicial.setLocation(0, 0);
        panelInicial.setBackground(backColor);
        tabbedPaneGlobal.addTab("start", panelInicial);

        buttonRegistrarCita = new JButton("Registrar cita");
        buttonRegistrarCita.setIcon(iconButton);
        panelInicial.add(buttonRegistrarCita);
        buttonRegistrarCita.setForeground(Color.WHITE);
        buttonRegistrarCita.setContentAreaFilled(false);
        buttonRegistrarCita.setFont(fontButtons);
        buttonRegistrarCita.setSize(250, 80);
        buttonRegistrarCita.setText("getName()");
        //buttonRegistrarCita.setBackground(Color.RED);
        buttonRegistrarCita.setLocation(300, 250);
        buttonRegistrarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabbedPaneGlobal.setSelectedIndex(1);
            }
        });


        buttonCancelarCita = new JButton("Cancelar cita");
        panelInicial.add(buttonCancelarCita);
        buttonCancelarCita.setForeground(Color.WHITE);
        buttonCancelarCita.setFont(fontButtons);
        buttonCancelarCita.setSize(250, 80);
        buttonCancelarCita.setBackground(Color.RED);
        buttonCancelarCita.setLocation(650, 250);

        panelIngresarUsuario = new JPanel(null);
        panelIngresarUsuario.setSize(1200, 600);
        tabbedPaneGlobal.addTab("1", panelIngresarUsuario);
        
        panelRegistrarUsuario = new JPanel(null);
        panelRegistrarUsuario.setSize(1200, 600);
        tabbedPaneGlobal.add("2", panelRegistrarUsuario);

        panelRegistrarCita = new JPanel(null);
        panelRegistrarCita.setSize(1200, 600);
        tabbedPaneGlobal.addTab("3", panelRegistrarCita);
        initRegistrarCita();
    }

    public void initRegistrarCita(){
 
        initFields();

        JLabel label = new JLabel("Consultar usuario en la base de datos");
        label.setFont(fontButtons);
        label.setForeground(Color.BLACK);
        label.setLocation(0, 220);
        label.setSize(1200, 30);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panelIngresarUsuario.add(label);
        panelIngresarUsuario.add(fieldID1);
        fieldID1.setLocation(500, 260);
        fieldID1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabbedPaneGlobal.setSelectedIndex(2);
                
            }
        });
        JButton button = new JButton("Confirmar");
        button.setSize(200, 40);
        button.setLocation(500, 320);
        panelIngresarUsuario.add(button);
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabbedPaneGlobal.setSelectedIndex(2);
                
            }
        });

        panelRegistrarUsuario.add(fieldNombres);
        fieldNombres.setLocation(100, 100);
        panelRegistrarUsuario.add(fieldApellidos);
        fieldApellidos.setLocation(100, 170);
        panelRegistrarUsuario.add(buttonIsDis);
        buttonIsDis.setLocation(100, 250);
        panelRegistrarUsuario.add(fieldDocumento);
        fieldDocumento.setLocation(500, 100);
        panelRegistrarUsuario.add(comboBoxSemestre);
        comboBoxSemestre.setLocation(500, 170);
        panelRegistrarUsuario.add(buttonRegistrarUsuario);
        buttonRegistrarUsuario.setLocation(500, 300);

        panelRegistrarCita.add(comboBoxMes);
        comboBoxMes.setLocation(100, 100);
        panelRegistrarCita.add(comboBoxDia);
        comboBoxDia.setLocation(340, 100);;
        panelRegistrarCita.add(comboBoxHora);
        comboBoxHora.setLocation(100, 200);
        panelRegistrarCita.add(buttonCrearCita);
        buttonCrearCita.setLocation(300, 300);

    }
    
    public void initFields(){
        fieldID1 = new JTextField("Ingresar id");
        fieldID1.setSize(200, 40);
        fieldID1.setForeground(Color.BLACK);
        fieldID1.setBackground(Color.WHITE);
        fieldID1.setFont(fontButtons);

        fieldNombres = new JTextField("Ingresar nombres..");
        fieldApellidos = new JTextField("Ingresar apellidos..");
        fieldDocumento = new JTextField("Documento..");
        fieldID2 = new JTextField("Ingresar ID..");
        buttonIsDis = new JRadioButton("Persona discapacitada");
        comboBoxSemestre = new JComboBox<>();
        comboBoxSemestre.addItem(null);
        for (int i = 1; i<= 10; i++){
            comboBoxSemestre.addItem(i);
        }

        fieldNombres.setSize(250, 40);
        fieldApellidos.setSize(250, 40);
        fieldDocumento.setSize(250, 40);
        fieldID2.setSize(250, 40);
        buttonIsDis.setSize(250, 100);
        comboBoxSemestre.setSize(250, 40);

        buttonIsDis.setFont(fontButtons);
        fieldNombres.setFont(fontButtons);
        fieldApellidos.setFont(fontButtons);
        fieldDocumento.setFont(fontButtons);
        fieldID2.setFont(fontButtons);
        comboBoxSemestre.setFont(fontButtons);

        fieldNombres.setForeground(Color.GRAY);
        fieldApellidos.setForeground(Color.GRAY);
        fieldDocumento.setForeground(Color.GRAY);
        fieldID2.setForeground(Color.GRAY);
        comboBoxSemestre.setBackground(Color.WHITE);
        
        buttonRegistrarUsuario = new JButton("Registrar");
        buttonRegistrarUsuario.setSize(250, 50);
        buttonRegistrarUsuario.setForeground(Color.WHITE);
        buttonRegistrarUsuario.setBackground(Color.RED);
        buttonRegistrarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabbedPaneGlobal.setSelectedIndex(3);
                /**
                 * 
                 * Registrar usuario
                 */
                crearUsuario();
            }
        });

        initDateButtons();
    }

    public void initDateButtons(){
        LocalDateTime fechaActual = LocalDateTime.now();
        comboBoxMes = new JComboBox<>();
        comboBoxMes.setSize(200, 40);
        
        comboBoxMes.addItem(null);
        for (int i = fechaActual.getMonthValue() - 1; i < meses.length; i++){
            comboBoxMes.addItem(meses[i]);
        }

        comboBoxMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxDia.removeAllItems();
                if (comboBoxMes.getSelectedItem() != null){
                    YearMonth mes = YearMonth.of(fechaActual.getYear(), comboBoxMes.getSelectedIndex());
                    int inicio = (comboBoxMes.getSelectedItem() != meses[fechaActual.getMonthValue()-1]) ? 1 : fechaActual.getDayOfMonth();
                    comboBoxDia.addItem(null);
                    for (int i = inicio; i <= mes.lengthOfMonth(); i++){
                        comboBoxDia.addItem(i + "");
                    }
                }              
            }
        });

        comboBoxDia = new JComboBox<>();
        comboBoxDia.setSize(200, 40);
        comboBoxDia.addItem(null);
        
        comboBoxHora = new JComboBox<>();
        comboBoxHora.setSize(200, 40);
        comboBoxHora.addItem(null);

        for (int i = fechaActual.getHour() + 1; i <= 24; i++){
            comboBoxHora.addItem(i + ":00");
        
        
        }


        comboBoxDia.setFont(fontButtons);
        comboBoxHora.setFont(fontButtons);
        comboBoxMes.setFont(fontButtons);

        buttonCrearCita = new JButton("Crear cita");
        buttonCrearCita.setFont(fontButtons);
        buttonCrearCita.setForeground(Color.WHITE);
        buttonCrearCita.setSize(200, 40);
        buttonCrearCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crearCita();          
            }
        });


    }

    public void crearUsuario(){
        String nombres = fieldNombres.getText();
        String apellidos = fieldApellidos.getText();
        // int semestre = (int) comboBoxSemestre.getSelectedItem();
        String documento = fieldDocumento.getText();
        boolean discapacitado = buttonIsDis.isEnabled();
    }

    public void crearCita(){
        String mes = (String) comboBoxMes.getSelectedItem();
        int numeroMes = 0;
        for (int i = 0; i < this.meses.length; i++){
            if (mes.equals(meses[i])){
                numeroMes = i + 1;
            }

        }
    }


    //Inicializacion de componentes
    JPanel navBar;
    JPanel panelPrincipal;
    JPanel panelRegistrarCita;
    JPanel panelInicial;
    JPanel panelIngresarUsuario;
    JPanel panelRegistrarUsuario;
    JButton buttonRegistrarCita;
    JButton buttonCancelarCita;
    JLabel logoUpb;
    Font fontButtons;
    JTabbedPane tabbedPaneGlobal;
    JTabbedPane tabbedPaneRegistrar;
    Color backColor;
    JTextField fieldNombres;
    JTextField fieldApellidos;
    JTextField fieldID2;
    JTextField fieldDocumento;
    JRadioButton buttonIsDis;
    JComboBox<Integer> comboBoxSemestre;
    JTextField fieldID1;
    JButton buttonRegistrarUsuario;
    JComboBox<String> comboBoxMes;
    JComboBox<String> comboBoxDia;
    JComboBox<String> comboBoxHora;
    JButton buttonCrearCita;
    ImageIcon iconButton = new ImageIcon(getClass().getResource("/Vistas/Icons/Buttons/ButtonMain.png"));
    String[] meses = {"Enero", "Febrero", "Marzo", "Abril",  "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
}
