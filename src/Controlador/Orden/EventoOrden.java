/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Orden;

import Ficheros.DocumentosOrden;
import Ficheros.DocumentosServicio;
import Logica.OrdenTrabajo;
import Logica.Servicio;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mar7g
 */
public class EventoOrden {

    int conta;
    ArrayList<Servicio> lista = new ArrayList<Servicio>();
    List<OrdenTrabajo> listaOrden = new ArrayList<>();
    DocumentosServicio docu = new DocumentosServicio();
    DocumentosOrden docuOrden = new DocumentosOrden();
    DefaultListModel modelo;
    List<Servicio> encontrado;
    Servicio nuevo;
    Servicio servicio;

    //AGREGAR SERVICIO A LA TABLA
    public void ActualizarLista(JTextField jBuscar, JList jListEncontrados) {
        modelo = new DefaultListModel();
        encontrado = new ArrayList();
        try {
            encontrado = docu.Consultar(jBuscar.getText());
            Iterator<Servicio> it = encontrado.iterator();

            while (it.hasNext()) {
                Servicio se = it.next();
                if (se.getEstado().equals("ACTIVO")) {
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

    public void listaEncontrados(JTextField fId, JTextField fServicio, JTextField fPrecio, JList jListEncontrados) {

        try {
            Servicio s = (Servicio) modelo.elementAt(jListEncontrados.getSelectedIndex());
            fId.setText(s.getId());
            fServicio.setText(s.getNombre());
            fPrecio.setText(Double.toString(s.getPrecio()));

        } catch (Exception e) {

        }
    }

    public void accionCombobox(JComboBox cEstadoOrden, JTextField fSaldo) {

        if (cEstadoOrden.getSelectedItem() == "ABONADO") {
            fSaldo.setEnabled(true);

        } else {

            fSaldo.setEnabled(false);
            fSaldo.setText("");
        }
    }

    public static boolean isEmpty(javax.swing.JTable tableOrden) {
        if (tableOrden != null && tableOrden.getModel() != null) {
            return tableOrden.getModel().getRowCount() <= 0 ? true : false;
        }
        return false;
    }

    public void limpiarOrden(JTable tableOrden, JTextField fSubTotal, JTextField fTotal, JTextField fCantidad, JTextField fPrecio, JTextField fServicio, JTextField fNombreApellido, JTextField fNombreEmpresa, JTextField fNumOrden, JComboBox cEstadoOrden, JTextField fSaldo) {
        fNombreEmpresa.setText("");
        fNombreApellido.setText("");
        fNumOrden.setText("");
        cEstadoOrden.setSelectedIndex(0);
        fSaldo.setText("");
        fCantidad.setText("");
        fPrecio.setText("");
        fServicio.setText("");
        fSubTotal.setText("");
        fTotal.setText("");
        String tabla[][] = new String[0][0];
        String col[] = {"CÓDIGO", "SERVICIO", "CANTIDAD", "PRECIO UNITARIO", "PRECIO TOTAL"};
        tableOrden.setModel(new DefaultTableModel(tabla, col));
        tableOrden.getColumnModel().getColumn(1).setPreferredWidth(200);
        lista.clear();
    }

    public void limpiarServicio(JTextField fCantidad, JTextField fPrecio, JTextField fId, JTextField fServicio) {
        fCantidad.setText("");
        fPrecio.setText("");
        fServicio.setText("");
    }

    private boolean Igual(JTable tableOrden, JTextField fId, JTextField fCantidad) {
        int num = tableOrden.getRowCount();
        int sumCantidad = 0;
        double sumTotal = 0;
        boolean v = false;

        for (int i = 0; i < num; i++) {
            String id = (String) tableOrden.getValueAt(i, 0);
            if (fId.getText().equals(id)) {
                if (conta < 1) {

                    sumCantidad = (Integer.parseInt((String) tableOrden.getValueAt(i, 2))) + Integer.parseInt(fCantidad.getText());
                    String cantidad = Integer.toString(sumCantidad);

                    tableOrden.setValueAt(cantidad, i, 2);
                    sumTotal = (Integer.parseInt((String) tableOrden.getValueAt(i, 2))) * (Double.parseDouble((String) tableOrden.getValueAt(i, 3)));
                    String total = Double.toString(sumTotal);

                    tableOrden.setValueAt(total, i, 4);
                    for (int x = 0; x < lista.size(); x++) {
                        servicio = lista.get(x);
                        if (servicio.getId().equals(fId.getText())) {
                            servicio.setCantidad(sumCantidad);
                            servicio.setTotal(sumTotal);

                        }

                    }
                    conta++;
                } else {
                    conta = 0;

                }

                v = true;
            }

        }

        return v;

    }

    public void mostrar(JTable tableOrden) {
        String matriz[][] = new String[lista.size()][5];

        for (int i = 0; i < lista.size(); i++) {

            matriz[i][0] = lista.get(i).getId();
            matriz[i][1] = lista.get(i).getNombre();
            matriz[i][2] = Integer.toString(lista.get(i).getCantidad());
            matriz[i][3] = Double.toString(lista.get(i).getPrecio());
            matriz[i][4] = Double.toString(lista.get(i).getTotal());

        }
        tableOrden.setModel(new DefaultTableModel(
                matriz,
                new String[]{
                    "CÓDIGO", "SERVICIO", "CANTIDAD", "PRECIO UNITARIO", "PRECIO TOTAL"
                }
        ));

    }

    private double Subtotal(JTable tableOrden, JTextField fSubTotal, JTextField fIva0) {
        int num = tableOrden.getRowCount();
        double sumSubTotal = 0;

        for (int i = 0; i < num; i++) {
            sumSubTotal += Double.parseDouble((String) tableOrden.getValueAt(i, 4));

        }

        fSubTotal.setText("" + sumSubTotal);
        fIva0.setText("" + 0);

        return sumSubTotal;

    }

    private double Iva(JTextField fIva12) {
        /**
         * double iva = Math.round((Subtotal() * 0.12) * 100) / 100d;
         * this.fIva12.setText("" + iva); return iva;
         */
        fIva12.setText("" + 0);
        return 0;
    }

    private double Total(JTextField fTotal, JTable tableOrden, JTextField fSubTotal, JTextField fIva0) {

        fTotal.setText("" + this.Subtotal(tableOrden, fSubTotal, fIva0));

        return this.Subtotal(tableOrden, fSubTotal, fIva0);

    }

    public void AgregarOrden(JTextField fTotal, JTextField fSubTotal, JTextField fIva0, JTextField fIva12, JTextField fCantidad, JTextField fPrecio, JTextField fId, JTextField fServicio, JTable tableOrden, JTextField fNombreApellido, JTextField fNombreEmpresa, JTextField fNumOrden, JComboBox cEstadoOrden, JTextField fSaldo) {
        double precioTotal = Integer.parseInt(fCantidad.getText()) * Double.parseDouble(fPrecio.getText());
        servicio = new Servicio(fId.getText(), fServicio.getText(), Integer.parseInt(fCantidad.getText()), Double.parseDouble(fPrecio.getText()), precioTotal);
        if (Integer.parseInt(fCantidad.getText()) > 0) {
            if (this.Igual(tableOrden, fId, fCantidad) == false) {
                lista.add(servicio);
                mostrar(tableOrden);
                this.limpiarServicio(fCantidad, fPrecio, fId, fServicio);
            } else {
                this.Igual(tableOrden, fId, fCantidad);
                this.limpiarServicio(fCantidad, fPrecio, fId, fServicio);
            }

            this.Subtotal(tableOrden, fSubTotal, fIva0);
            this.Iva(fIva12);
            this.Total(fTotal, tableOrden, fSubTotal, fIva0);
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese una cantidad mayor de 0");
        }

    }

    public void agregar(JTextField fTotal, JTextField fSubTotal, JTextField fIva0, JTextField fIva12, JTextField fCantidad, JTextField fServicio, JTextField fPrecio, JTextField fId, javax.swing.JTable tableOrden, JTextField fNombreApellido, JTextField fNombreEmpresa, JTextField fNumOrden, JComboBox cEstadoOrden, JTextField fSaldo) {
        if (!fCantidad.getText().isEmpty() && !fServicio.getText().isEmpty() && !fPrecio.getText().isEmpty()) {
            this.AgregarOrden(fTotal, fSubTotal, fIva0, fIva12, fCantidad, fPrecio, fId, fServicio, tableOrden, fNombreApellido, fNombreEmpresa, fNumOrden, cEstadoOrden, fSaldo);

        } else {
            JOptionPane.showMessageDialog(null, "Existen campos vacios");
        }
    }
      //FIN AGREGAR SERVICIO A LA TABLA

    //ELIMINAR SERVICIO A LA TABLA
    public void Eliminar(JTable tableOrden) {
        DefaultTableModel dtm = (DefaultTableModel) tableOrden.getModel();
        int indFil = tableOrden.getSelectedRow();
        if (indFil >= 0) {
            dtm.removeRow(indFil);
            lista.remove(indFil);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila ");
        }
    }
     //FIN ELIMINAR SERVICIO A LA TABLA

    //GUARDAR SERVICIO A LA TABLA
    public void Guardar(JComboBox cEstadoOrden, JTextField fSubTotal, JTextField fSaldo, JTextField fTotal, JTextField fNombreEmpresa, JTextField fNombreApellido, JTextField fFechaOrden, JTextField fNumOrden, JTable tableOrden) {
        if (cEstadoOrden.getSelectedItem() == "ABONADO") {
            double saldo = Double.parseDouble(fSaldo.getText());
            double total = Double.parseDouble(fTotal.getText());
            fSaldo.getText();
            if (saldo < total && saldo > 0) {
                fSaldo.getText();
            } else {
                JOptionPane.showMessageDialog(null, "El Abono es superior al total o Menor a 0");
                fSaldo.setText("");
            }
        }

        if (cEstadoOrden.getSelectedItem() == "PAGADO") {
            fSaldo.setText(fTotal.getText());
        }
        if (cEstadoOrden.getSelectedItem() == "NO PAGADO") {
            fSaldo.setText("0");
        }

        if (!fNombreEmpresa.getText().isEmpty() && !fNombreApellido.getText().isEmpty() && !fFechaOrden.getText().isEmpty() && !fNumOrden.getText().isEmpty() && !isEmpty(tableOrden) && !fSaldo.getText().isEmpty()) {

            OrdenTrabajo or = new OrdenTrabajo(fNombreEmpresa.getText(), fNombreApellido.getText(), fNumOrden.getText(), (String) cEstadoOrden.getSelectedItem(), Double.parseDouble(fSaldo.getText()), Double.parseDouble(fTotal.getText()));
            for (int i = 0; i < tableOrden.getRowCount(); i++) {

                Servicio se = new Servicio((String) (tableOrden.getValueAt(i, 0)), (String) (tableOrden.getValueAt(i, 1)), Integer.parseInt((String) tableOrden.getValueAt(i, 2)), Double.parseDouble((String) tableOrden.getValueAt(i, 3)), Double.parseDouble((String) tableOrden.getValueAt(i, 4)));
                docuOrden.Agregars(se);

            }
            docuOrden.Agregar(or);
            JOptionPane.showMessageDialog(null, "Se guardo con exito");

            this.limpiarOrden(tableOrden, fSubTotal, fTotal, fTotal, fSaldo, fSaldo, fNombreApellido, fNombreEmpresa, fNumOrden, cEstadoOrden, fSaldo);

        } else {
            JOptionPane.showMessageDialog(null, "No se pudo guardar. Verifique que todo sea correcto");

        }

    }
//FIN GUARDAR SERVICIO A LA TABLA

    public void generarNumero(JTextField fNumOrden) {
        try {
          
           listaOrden =  docuOrden.ConsultarOrden("+");
             System.out.println(listaOrden);
            Formatter fmt = new Formatter();
            
            if (listaOrden.isEmpty()) {

                fNumOrden.setText("0001");
            } else {
                int id = Integer.parseInt(listaOrden.get(listaOrden.size() - 1).getNumeroOrden()) + 1;
                Formatter ids = fmt.format("%04d", id);

                fNumOrden.setText("" + ids);

            }

        } catch (IOException ex) {
            
            Logger.getLogger(EventoOrden.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }
}
