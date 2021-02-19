/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Servicio;

import Ficheros.DocumentosServicio;
import Logica.Servicio;
import java.awt.HeadlessException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.*;
import rsbuttom.RSButtonMetro;

/**
 *
 * @author Mar7g
 */
public class EventoModificar {

    DefaultListModel modelo;
    List<Servicio> encontrado;
    DocumentosServicio docu = new DocumentosServicio();
    Servicio existente;
    Servicio nuevo;

    public void listaEncontradoCambiar(RSButtonMetro JModificar, JComboBox cEstado, JList jListEncontrados, JTextField FIdM, JTextField fNombreServicioM, JTextField fPrecioM) {

        JModificar.setEnabled(true);
        cEstado.setEnabled(true);
        try {

            Servicio s = (Servicio) modelo.elementAt(jListEncontrados.getSelectedIndex());

            existente = s;
            FIdM.setText(s.getId());
            fNombreServicioM.setText(s.getNombre());
            fPrecioM.setText(Double.toString(s.getPrecio()));
            cEstado.getModel().setSelectedItem(String.valueOf(s.getEstado()));

        } catch (Exception e) {

        }
    }

    public void botonBuscar(JTextField fModificar, JList jListEncontrados) {
        modelo = new DefaultListModel();
        encontrado = new ArrayList();

        try {

            encontrado = docu.Consultar(fModificar.getText());
            for (Servicio se : encontrado) {
                {
                    modelo.addElement(se);
                }
            }

        } catch (IOException ex) {
            System.err.println(ex.toString());
        }

        if (encontrado.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontró el servicio", "No encontrado", JOptionPane.WARNING_MESSAGE);
        }

        jListEncontrados.setModel(modelo);
    }

    public void modificar(JTextField FIdM, JTextField fNombreServicioM, JTextField fPrecioM, JComboBox cEstado, JList jListEncontrados, JTextField fModificar) {
        try {

            Servicio servi = new Servicio();
            servi.setId(FIdM.getText());
            servi.setNombre(fNombreServicioM.getText());
            servi.setPrecio(Double.parseDouble(fPrecioM.getText()));
            servi.setEstado((String) cEstado.getSelectedItem());
            nuevo = servi;

        } catch (NumberFormatException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Datos no validos");
        }

        try {
            if (!fNombreServicioM.getText().isEmpty() && !fPrecioM.getText().isEmpty()) {
                docu.Modificar(existente, nuevo);
                this.botonBuscar(fModificar, jListEncontrados);

            } else {
                JOptionPane.showMessageDialog(null, "Existen campos vacios");
            }
        } catch (IOException ex) {
            System.err.println(ex.toString());
        }

    }
}
