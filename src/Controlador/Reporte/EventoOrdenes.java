/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Reporte;

import Ficheros.DocumentosOrden;
import Logica.OrdenTrabajo;
import java.awt.HeadlessException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.*;

/**
 *
 * @author Mar7g
 */
public class EventoOrdenes {

    OrdenTrabajo nuevo;
    OrdenTrabajo existente;
    DefaultListModel modelo;
    List<OrdenTrabajo> encontrado;
    DocumentosOrden docu = new DocumentosOrden();

    public void modificar(JTextField fBuscar, JList jListEncontrados, JTextField fAbono, JTextField fTotal, JTextField fNumOrden, JTextField fNombreEmpresa, JTextField fNombre, JComboBox cEstadoOrden) {
        double saldo = Double.parseDouble(fAbono.getText());
        double total = Double.parseDouble(fTotal.getText());
        try {

            OrdenTrabajo o = new OrdenTrabajo();
            o.setNumeroOrden(fNumOrden.getText());
            o.setNombreEmpresa(fNombreEmpresa.getText());
            o.setNombreCliente(fNombre.getText());

            if (saldo == total) {
                cEstadoOrden.setSelectedItem("PAGADO");
            }
            if (saldo < total) {
                cEstadoOrden.setSelectedItem("ABONADO");
            }
            if (saldo == 0) {
                cEstadoOrden.setSelectedItem("NO PAGADO");
            }
            o.setEstadoOrden((String) cEstadoOrden.getSelectedItem());
            o.setSaldo(Double.parseDouble(fAbono.getText()));
            o.setTotalOrden(Double.parseDouble(fTotal.getText()));

            nuevo = o;

        } catch (NumberFormatException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Datos no validos");
        }

        try {

            if (!fAbono.getText().isEmpty()) {
                if (saldo <= total) {
                    docu.Modificar(existente, nuevo);
                    this.ActualizarLista(fBuscar, jListEncontrados);
                    JOptionPane.showMessageDialog(null, "Se modificó con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "El abono es mayor que el total");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Existen campos vacios");
            }
        } catch (IOException ex) {
            System.err.println(ex.toString());
        }
    }

    public void encontrados(JList jListEncontrados, JTextField fNombreEmpresa, JTextField fNombre, JTextField fNumOrden, JComboBox cEstadoOrden, JTextField fAbono, JTextField fTotal) {
        try {
            OrdenTrabajo o = (OrdenTrabajo) modelo.elementAt(jListEncontrados.getSelectedIndex());
            existente = o;
            fNombreEmpresa.setText(o.getNombreEmpresa());
            fNombre.setText(o.getNombreCliente());
            fNumOrden.setText(o.getNumeroOrden());
            cEstadoOrden.setSelectedItem(String.valueOf(o.getEstadoOrden()));
            fAbono.setText(Double.toString(o.getSaldo()));
            fTotal.setText(Double.toString(o.getTotalOrden()));
        } catch (Exception e) {

        }
    }

    public void ActualizarLista(JTextField fBuscar, JList jListEncontrados)
   {

        modelo = new DefaultListModel();

        encontrado = new ArrayList();

        try {

            encontrado = docu.ConsultarOrden(fBuscar.getText());
            for (OrdenTrabajo se : encontrado) {

                {
                    modelo.addElement(se);
                }
            }

        } catch (IOException ex) {
            System.err.println(ex.toString());
        }

        if (encontrado.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontró el nombre del cliente", "No encontrado", JOptionPane.WARNING_MESSAGE);
        }

        jListEncontrados.setModel(modelo);
    }
}


