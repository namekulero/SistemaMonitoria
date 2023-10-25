package Vistas;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Properties;
import org.apache.commons.codec.digest.DigestUtils;

import Clases.Cita;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicArrowButton;

import Servidor.Cliente;
import Servidor.Servicio;

public class VentanaOperador extends JFrame{
    
    Cliente client;
    String idActual;

    public VentanaOperador(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Operador");
        setSize(1200, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        initComponents();


        try {
            initClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initClient() throws IOException{
        Properties config = new Properties();

        File archivo = new File("config.properties");
        String dir = archivo.getCanonicalPath();

        try (FileInputStream fin = new FileInputStream(new File(dir))) {
            config.load(fin);
            client = new Cliente((String) config.get("IP"), (String) config.get("PORT"), (String) config.get("SERVICENAME"));
        } catch (Exception e) {
        }
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
        upbColor = new Color(238, 28, 37);
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
        panelInicial.add(buttonRegistrarCita);
        buttonRegistrarCita.setForeground(Color.WHITE);
        buttonRegistrarCita.setFont(fontButtons);
        buttonRegistrarCita.setSize(250, 80);
        buttonRegistrarCita.setBackground(upbColor);
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
        buttonCancelarCita.setBackground(upbColor);
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
                if (!fieldID1.getText().isBlank()){
                    tabbedPaneGlobal.setSelectedIndex(2);
                    idActual = fieldID1.getText();
                } else {
                    generarAlerta();
                }
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

    }
    
    public void initFields(){
        buttonBack = new JButton("Volver");
        buttonBack.setSize(250, 50);
        buttonBack.setForeground(Color.WHITE);
        buttonBack.setBackground(upbColor);

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
        buttonRegistrarUsuario.setBackground(upbColor);
        buttonRegistrarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (fieldNombres.getText().isBlank() || fieldApellidos.getText().isBlank() || comboBoxSemestre.getSelectedItem() == null ||
                    fieldDocumento.getText().isBlank()){
                    generarAlerta();
                } else {
                    tabbedPaneGlobal.setSelectedIndex(3);
                    crearUsuario();
                }
                
            }
        });

        initDateButtons();
    }

    public void initDateButtons(){
        //Seleccionar Mes
        LocalDateTime fechaActual = LocalDateTime.now();
        comboBoxMes = new JComboBox<>();
        comboBoxMes.setSize(200, 40);
        comboBoxMes.addItem(null);
        //Seleccionar Dia
        comboBoxDia = new JComboBox<>();
        comboBoxDia.setSize(200, 40);
        comboBoxDia.addItem(null);
        //Seleccionar Hora
        comboBoxHora = new JComboBox<>();
        comboBoxHora.setSize(200, 40);
        comboBoxHora.addItem(null);
        //Llenar meses
        for (int i = fechaActual.getMonthValue() - 1; i < meses.length; i++){
            comboBoxMes.addItem(meses[i]);
        }
        comboBoxMes.addActionListener(new java.awt.event.ActionListener() {
            //Llenar demas campos acorde al mes
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxDia.removeAllItems();
                comboBoxHora.removeAllItems();
                if (comboBoxMes.getSelectedItem() != null){
                    YearMonth mes = YearMonth.of(fechaActual.getYear(), comboBoxMes.getSelectedIndex());
                    int inicio = (comboBoxMes.getSelectedItem() != meses[fechaActual.getMonthValue()-1]) ? 1 : fechaActual.getDayOfMonth();
                    comboBoxDia.addItem(null);
                    for (int i = inicio; i <= mes.lengthOfMonth(); i++){
                        comboBoxDia.addItem(i);
                    }
                    

                    comboBoxHora.addItem(null);
                    for (int i = 8; i < 20; i++){
                        if (i < 10){
                            comboBoxHora.addItem("0" + i);
                        } else {
                            comboBoxHora.addItem(i + "");
                        }
                        
                    }

                }              
            }
        });

        for (int i = fechaActual.getHour() + 1; i <= 20; i++){
            comboBoxHora.addItem(i + ":00");
        }

        comboBoxDia.setFont(fontButtons);
        comboBoxHora.setFont(fontButtons);
        comboBoxMes.setFont(fontButtons);

        panelRegistrarCita.add(comboBoxMes);
        comboBoxMes.setLocation(100, 100);
        panelRegistrarCita.add(comboBoxDia);
        comboBoxDia.setLocation(340, 100);;
        panelRegistrarCita.add(comboBoxHora);
        comboBoxHora.setLocation(100, 200);
        panelRegistrarCita.add(buttonCrearCita);
        buttonCrearCita.setLocation(340, 300);


        buttonCrearCita = new JButton("Crear cita");
        buttonCrearCita.setFont(fontButtons);
        buttonCrearCita.setForeground(Color.WHITE);
        buttonCrearCita.setBackground(upbColor);
        buttonCrearCita.setSize(200, 40);
        buttonCrearCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (comboBoxDia.getSelectedItem() != null && comboBoxMes.getSelectedItem() != null && comboBoxHora.getSelectedItem() != null){
                    crearCita();
                } else {
                    generarAlerta();
                }
                
            }
        });
    }

    public void crearUsuario(){
        String nombres = fieldNombres.getText();
        String apellidos = fieldApellidos.getText();
        int semestre = (int) comboBoxSemestre.getSelectedItem();
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
        String idCita = DigestUtils.sha1Hex(LocalDateTime.now().toString());
        String hora = ((String) comboBoxHora.getSelectedItem()).substring(0, 1);
        int numHora = Integer.parseInt(hora);
        LocalDateTime dateTimeCita = LocalDateTime.of(LocalDateTime.now().getYear(), numeroMes, (int)comboBoxDia.getSelectedItem(), numHora, 0);
        Cita nuevaCita = new Cita(idCita, idActual, dateTimeCita.toString());
        
    }

    public void generarAlerta(){
        JOptionPane.showMessageDialog(null, "Debe llenar todos los campos.", "Error", JOptionPane.PLAIN_MESSAGE);
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
    Color backColor;
    Color upbColor;
    JTabbedPane tabbedPaneGlobal;
    JTabbedPane tabbedPaneRegistrar;
    JTextField fieldNombres;
    JTextField fieldApellidos;
    JTextField fieldID2;
    JTextField fieldDocumento;
    JRadioButton buttonIsDis;
    JComboBox<Integer> comboBoxSemestre;
    JTextField fieldID1;
    JButton buttonRegistrarUsuario;
    JComboBox<String> comboBoxMes;
    JComboBox<Integer> comboBoxDia;
    JComboBox<String> comboBoxHora;
    JButton buttonCrearCita;
    JButton buttonBack;
    String[] meses = {"Enero", "Febrero", "Marzo", "Abril",  "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

}
