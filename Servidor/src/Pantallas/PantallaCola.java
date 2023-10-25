package Pantallas;

import java.awt.Color;
import java.awt.Font;
import java.rmi.RemoteException;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;

public class PantallaCola extends JFrame {
    JLabel labelCantidad;
    static DefaultTableModel modelo;

    public PantallaCola() throws RemoteException {
        iniciarComponentes();
        setTitle("Cola de turnos actual");
        setLocationRelativeTo(null);
        setResizable(false);
        this.setMaximizedBounds(getBounds());
    }

    public void iniciarComponentes() throws RemoteException {
        JPanel panelBackground = new JPanel();
        panelBackground.setBackground(new ColorUIResource(153, 204, 255));
        panelBackground.setVisible(true);
        panelBackground.setSize(getPreferredSize());
        panelBackground.setLayout(null);

        modelo = new DefaultTableModel();
        JTable tablaPedidos = new JTable(modelo) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelo.addColumn("Turno actual");
        modelo.addColumn("Módulo");

        tablaPedidos.setSelectionMode(0);
        JScrollPane panelBusqueda = new JScrollPane(tablaPedidos);
        panelBusqueda.setBounds(105, 80, 560, 300);
        panelBusqueda.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panelBackground.add(panelBusqueda);

        labelCantidad = new JLabel("Cantidad en cola: 0");
        labelCantidad.setBounds(105, 385, 350, 30);
        panelBackground.add(labelCantidad);

        JLabel labelTitulo = new JLabel("COLA DE ATENCIÓN");
        labelTitulo.setBounds(275, 30, 350, 30);
        labelTitulo.setFont(labelTitulo.getFont().deriveFont(Font.PLAIN, 24f));
        panelBackground.add(labelTitulo);

        setVisible(true);
        add(panelBackground);
        setVisible(true);
        setSize(800, 500);
    }

    public void receiveFila(Object[] fila) {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            if (((String) fila[1]).equals((String) modelo.getValueAt(i, 1))) {
                modelo.setValueAt(fila[0], i, 0);
                return;
            }
        }
        modelo.addRow(fila);
    }

    public void updateQueueSize(int size) {
        labelCantidad.setText("Cantidad en cola: " + size);
    }
    
}
