package Vistas;

import Clases.Cita;
import Clases.Estudiante;
import Clases.Estructuras.interfaces.node.NodeInterface;
import Clases.Estructuras.linkedlist.ListaEnlazada;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Iterator;
import java.util.Properties;
import org.apache.commons.codec.digest.DigestUtils;
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

import Servidor.Cliente;

public class VentanaOperador extends JFrame{
    
    Cliente client;
    String idActual;
    int tipoOperacion = 0;

    public VentanaOperador(){
        try {
            initClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Operador");
        setSize(1200, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        initComponents();
        setVisible(true);
        
    }

    public void initClient() throws IOException{
        Properties config = new Properties();

        File archivo = new File("config.properties");
        String dir = archivo.getCanonicalPath();

        try (FileInputStream fin = new FileInputStream(new File(dir))) {
            config.load(fin);
            client = new Cliente((String) config.get("IP"), (String) config.get("PORT"), (String) config.get("SERVICENAME"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
        hover = new java.awt.Color(236,99,105);
        cursorClickeable = new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR);
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
                tipoOperacion = 0;
                fieldID1.setText("Id..");
            }
        });
        
        buttonCancelarCita = new JButton("Cancelar cita");
        panelInicial.add(buttonCancelarCita);
        buttonCancelarCita.setForeground(Color.WHITE);
        buttonCancelarCita.setFont(fontButtons);
        buttonCancelarCita.setSize(250, 80);
        buttonCancelarCita.setBackground(upbColor);
        buttonCancelarCita.setLocation(650, 250);
        buttonCancelarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabbedPaneGlobal.setSelectedIndex(1);
                tipoOperacion = 1;
                fieldID1.setText("Id..");
            }
        });

        //Panel 1, para ingresar Id del usuario/estudiante
        panelIngresarUsuario = new JPanel(null);
        panelIngresarUsuario.setSize(1200, 600);
        tabbedPaneGlobal.addTab("1", panelIngresarUsuario);
        //Panel 2, para registrar el usuario en caso de no existir
        panelRegistrarUsuario = new JPanel(null);
        panelRegistrarUsuario.setSize(1200, 600);
        tabbedPaneGlobal.add("2", panelRegistrarUsuario);
        //Panel 3, para definir fecha y hora de la cita
        panelRegistrarCita = new JPanel(null);
        panelRegistrarCita.setSize(1200, 600);
        tabbedPaneGlobal.addTab("3", panelRegistrarCita);
        initRegistrarCita();

        //Panel 4, para canelar cita
        panelCancelarCita = new JPanel(null);
        panelCancelarCita.setSize(1200, 600);
        tabbedPaneGlobal.addTab("4", panelCancelarCita);


        setListenersFields();
    }

    /**
     * Iniciar componentes para seccion de cancelacion
     */
    public void initCancelarCita(){
        ListaEnlazada<Cita> listaCitas = leerCitasUsuario();

        if (listaCitas != null){
            initTable(listaCitas);
        }
        
        panelCancelarCita.add(buttonBack);
        buttonBack.setLocation(15, 30);
        /*
         * 
         */

    }

    public ListaEnlazada<Cita> leerCitasUsuario(){
        return client.leerCitasUsuario(idActual);
    }

    public void initTable(ListaEnlazada<Cita> listaCitas){
        Font fontHead = new Font("Roboto", 0, 30);
        int y = 10;
        Iterator<NodeInterface<Cita>> iterador = listaCitas.iterator();
        JPanel panelTable = new JPanel(null);
        JPanel panelHead = new JPanel(null);
        JLabel labelIds = new JLabel("Cita id");
        labelIds.setSize(100, 30);
        labelIds.setFont(fontHead);
        panelHead.setSize(1050, 50);
        panelHead.add(labelIds);
        labelIds.setLocation(20, 10);
        panelCancelarCita.add(panelHead);
        panelHead.setLocation(50, 150);

        JLabel labelFechas = new JLabel("Fecha y hora");
        labelFechas.setFont(fontHead);
        labelFechas.setSize(200, 30);
        labelFechas.setLocation(500, 10);
        panelHead.add(labelFechas);
        panelTable.setSize(1050, 298);
        panelCancelarCita.add(panelTable);
        panelTable.setLocation(50, 200);
        panelTable.setBackground(Color.WHITE);
        

        for (int i=0; i < listaCitas.size(); i++){
            Cita citaActual = iterador.next().getObject();

            JPanel panelRow = new JPanel(null);
            panelRow.setSize(1050, 60);
            panelRow.setBackground(Color.WHITE);
            panelTable.add(panelRow);
            int a = i;
            panelRow.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    /**
                     * Confirmar cancelacion de la cita
                     */
                    if (showPasswordInputDialog()){
                        client.cancelarCita(citaActual.getCitaId(), citaActual.getEstudianteId());
                        panelRow.removeAll();
                    }
                }
                // Hover
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    panelRow.setBackground(hover);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    panelRow.setBackground(Color.WHITE);
                }
            });
            panelRow.setCursor(cursorClickeable);
            panelRow.setLocation(0, y);
            JLabel labelId = new JLabel(citaActual.getCitaId());
            labelId.setSize(400, 30);
            labelId.setFont(fontTable);
            labelId.setHorizontalTextPosition(SwingConstants.LEFT);

            panelRow.add(labelId);
            labelId.setLocation(20, 5);
            
            JLabel labelFecha = new JLabel(citaActual.getDateTime());
            labelFecha.setSize(200, 30);
            labelFecha.setFont(fontTable);
            
            panelRow.add(labelFecha);
            labelFecha.setLocation(500, 5);
            
            JLabel aux = new JLabel("(Seleccione para eliminar)"); aux.setVisible(true);
            aux.setSize(200, 20);
            aux.setForeground(java.awt.SystemColor.textInactiveText);
            
            panelRow.add(aux);
            aux.setLocation(20, 28);

            y += 70;
        }
        panelTable.setBorder(new javax.swing.border.LineBorder(Color.BLACK));
        panelCancelarCita.revalidate();
        panelCancelarCita.repaint();
    }

    public boolean showPasswordInputDialog() {
        JTextField field = new JTextField();
        int option = JOptionPane.showConfirmDialog(null, field, "Escriba 'Cancelar' para confirmar.", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            //Confirmar input
            return field.getText().equals("Cancelar");
        } else {
            return false;
        }
    }
    JButton button;
    /**
     * Iniciar componentes para seccion de registro
     */
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
        button = new JButton("Confirmar");
        button.setSize(200, 40);
        button.setLocation(500, 320);
        panelIngresarUsuario.add(button);
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idActual = fieldID1.getText();
                if (!fieldID1.getText().isBlank()){
                    if (tipoOperacion == 0){
                        if (client.isDataBase(idActual)){
                            tabbedPaneGlobal.setSelectedIndex(3);
                            
                        } else {
                            tabbedPaneGlobal.setSelectedIndex(2);
                        }
                    } else if (tipoOperacion == 1){
                        tabbedPaneGlobal.setSelectedIndex(4);
                        initCancelarCita();
                    }
                    
                } else {
                    generarAlerta();
                }
            }
        });

        volver = new JButton("Volver");
        volver.setSize(150, 50);
        volver.setBackground(upbColor);
        volver.setForeground(Color.WHITE);
        volver.setFont(fontButtons);
        panelIngresarUsuario.add(volver);
        volver.setLocation(15, 30);
        volver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabbedPaneGlobal.setSelectedIndex(0);               
            }
        });

        //Añadir componentes al panel
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
        panelRegistrarUsuario.add(buttonBack);
        buttonBack.setLocation(15, 30);

    }
    
    /**
     * Iniciar componentes principales
     */
    public void initFields(){
        buttonBack = new JButton("Volver");
        buttonBack.setSize(150, 50);
        buttonBack.setForeground(Color.WHITE);
        buttonBack.setBackground(upbColor);

        fieldID1 = new JTextField("Ingresar id");
        fieldID1.setSize(200, 40);
        fieldID1.setForeground(Color.BLACK);
        fieldID1.setBackground(Color.WHITE);
        fieldID1.setFont(fontButtons);
        fieldID1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fieldID1.setText("");
                fieldID1.setForeground(Color.BLACK);
            }
        });

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

    public void setListenersFields(){
        fieldNombres.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fieldNombres.setText("");
                fieldNombres.setForeground(Color.BLACK);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (fieldNombres.getText().isBlank()){
                    fieldNombres.setText("Ingresar nombres..");
                }
            }
        });
        fieldApellidos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fieldApellidos.setText("");
                fieldApellidos.setForeground(Color.BLACK);
            }
        });
        fieldDocumento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fieldDocumento.setText("");
                fieldDocumento.setForeground(Color.BLACK);
            }
        });
        

        buttonBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                /**
                 * Confirmar cancelacion de la cita
                 */
                tabbedPaneGlobal.setSelectedIndex(0);
            }
        });
    }

    /**
     * Iniciar componentes para leer fecha para cita
     */
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
                            comboBoxHora.addItem(i + ":00");
                        }
                        
                    }
                }
            }
        });

        comboBoxDia.setFont(fontButtons);
        comboBoxHora.setFont(fontButtons);
        comboBoxMes.setFont(fontButtons);


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
        JLabel labelMes = new JLabel("Mes");
        JLabel labelDia = new JLabel("Dia");
        JLabel labelHora = new JLabel("Hora");
        labelMes.setFont(fontButtons);
        labelDia.setFont(fontButtons);
        labelHora.setFont(fontButtons);

        labelDia.setSize(200, 30);
        labelHora.setSize(200, 30);
        labelMes.setSize(200, 30);        
        //Botones
        panelRegistrarCita.add(comboBoxMes);
        comboBoxMes.setLocation(100, 100);
        panelRegistrarCita.add(comboBoxDia);
        comboBoxDia.setLocation(340, 100);;
        panelRegistrarCita.add(comboBoxHora);
        comboBoxHora.setLocation(100, 220);
        panelRegistrarCita.add(buttonCrearCita);
        buttonCrearCita.setLocation(340, 300);
        //Labels para acompañar
        panelRegistrarCita.add(labelMes);
        labelMes.setLocation(comboBoxMes.getX(), comboBoxMes.getY()-40);
        panelRegistrarCita.add(labelDia);
        labelDia.setLocation(comboBoxDia.getX(), comboBoxDia.getY()-40);
        panelRegistrarCita.add(labelHora);
        labelHora.setLocation(comboBoxHora.getX(), comboBoxHora.getY()-40);
    }
    /**
     * Crear usuario para estudiante y eviar al server
     */
    public void crearUsuario(){
        String nombres = fieldNombres.getText();
        String apellidos = fieldApellidos.getText();
        int semestre = (int) comboBoxSemestre.getSelectedItem();
        String documento = fieldDocumento.getText();
        boolean discapacitado = buttonIsDis.isEnabled();
        Estudiante newUser = new Estudiante(nombres, apellidos, idActual, semestre, discapacitado);
        client.crearUsuario(newUser, documento);
    }

    /**
     * Crear cita y enviar al server
     */
    public void crearCita(){
        //Leer componentes para crear LocalDateTIme
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
        LocalDateTime dateTimeCita = LocalDateTime.of(LocalDateTime.now().getYear(), numeroMes, (int)comboBoxDia.getSelectedItem(), 
        numHora, 0, 1);
        //
        Cita nuevaCita = new Cita(idCita, idActual, dateTimeCita.toString());
        client.crearCita(nuevaCita);
        //Aviso
        JOptionPane.showMessageDialog(null, "Cita registra exitosamente!", "Anuncio", JOptionPane.PLAIN_MESSAGE);
        limpiarRegistrarCita();
    }

    /**
     * Reiniciar componentes al confirmar cita
     */
    public void limpiarRegistrarCita(){
        comboBoxDia.setSelectedItem(0);
        comboBoxHora.setSelectedItem(0);
        comboBoxMes.setSelectedItem(0);
        fieldNombres.setText("Ingresar nombres..");
        fieldApellidos.setText("Ingresar apellidos..");
        fieldDocumento.setText("Docuemnto..");
        fieldID1.setText("Ingresa id");
        idActual = "";
        tabbedPaneGlobal.setSelectedIndex(0);
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
    JPanel panelCancelarCita;
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
    Cursor cursorClickeable;
    Color hover;
    Font fontTable = new java.awt.Font("Bahnschrift", 0, 18);
    String[] meses = {"Enero", "Febrero", "Marzo", "Abril",  "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
    JButton volver;
    

}
