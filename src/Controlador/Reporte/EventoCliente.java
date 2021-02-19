/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Reporte;

import Ficheros.DocumentosOrden;
import Logica.OrdenTrabajo;
import PaginaPrincipal.PanelReportes;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Mar7g
 */
public class EventoCliente {

    List<OrdenTrabajo> listaOrden = new ArrayList();
    DocumentosOrden docu = new DocumentosOrden();

    public void mostrarCliente(JTextField fBuscarCliente, JTable tablaCliente, JLabel lNombre) throws IOException {
        listaOrden = docu.ConsultarOrden(fBuscarCliente.getText());
        lNombre.setText(fBuscarCliente.getText());
        String matriz[][] = new String[listaOrden.size()][2];
        for (int i = 0; i < listaOrden.size(); i++) {
            matriz[i][0] = listaOrden.get(i).getNumeroOrden();
            matriz[i][1] = Double.toString(listaOrden.get(i).getTotalOrden());

        }

        tablaCliente.setModel(new javax.swing.table.DefaultTableModel(
                matriz,
                new String[]{
                    "NÚMERO DE ORDEN ", "VALOR"
                }
        ));

    }

    private double totalCliente(JTable tablaCliente, JTextField fTotalCliente) {
        int num = tablaCliente.getRowCount();
        double sumTotal = 0;

        for (int i = 0; i < num; i++) {
            sumTotal += Double.parseDouble((String) tablaCliente.getValueAt(i, 1));

        }

        fTotalCliente.setText("" + sumTotal);

        return sumTotal;

    }

    public void buscar(JTextField fBuscarCliente, JTable tablaCliente, JLabel lNombre, JTextField fTotalCliente) {
        try {

            this.mostrarCliente(fBuscarCliente, tablaCliente, lNombre);
            this.totalCliente(tablaCliente, fTotalCliente);
        } catch (IOException ex) {
            Logger.getLogger(PanelReportes.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void guardar(JTable tablaCliente,JLabel lNombre){
    try {

            String sucursalesCSVFile = "reportes/Clientes/" + lNombre.getText() + ".fci";
            BufferedWriter bfw = new BufferedWriter(new FileWriter(sucursalesCSVFile));

            for (int i = 0; i < tablaCliente.getRowCount(); i++) {
                for (int j = 0; j < tablaCliente.getColumnCount(); j++) {
                    bfw.write((String) (tablaCliente.getValueAt(i, j)));
                    if (j < tablaCliente.getColumnCount() - 1) {
                        bfw.write("\t");
                    }
                }
                bfw.newLine();
            }

            bfw.close();
            JOptionPane.showMessageDialog(null, "Se guardó correctamente");
        } catch (IOException e) {
            System.out.println("ERROR: Ocurrio un problema al salvar el archivo!" + e.getMessage());
        }
    
    }
}
