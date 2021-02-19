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
public class EventoAbonado {

    List<OrdenTrabajo> listaOrden = new ArrayList();
    DocumentosOrden docu = new DocumentosOrden();

    public void mostrarAbonada(JTable tablaAbonada) throws IOException {
        listaOrden = docu.ConsultarOrden("ABONADO");
        String matriz[][] = new String[listaOrden.size()][3];
        for (int i = 0; i < listaOrden.size(); i++) {
            matriz[i][0] = listaOrden.get(i).getNumeroOrden();
            matriz[i][1] = Double.toString(listaOrden.get(i).getSaldo());
            matriz[i][2] = Double.toString(listaOrden.get(i).getTotalOrden() - listaOrden.get(i).getSaldo());

        }

        tablaAbonada.setModel(new javax.swing.table.DefaultTableModel(
                matriz,
                new String[]{
                    "NÚMERO DE ORDEN ", "VALOR ABONADO", "SALDO PENDIENTE"
                }
        ));

    }

    private void totalAbonada(JTable tablaAbonada, JTextField fTotalAbono, JTextField fTotalPendiente) {
        int num = tablaAbonada.getRowCount();
        double sumAbono = 0;
        double sumPendiente = 0;

        for (int i = 0; i < num; i++) {
            sumAbono += Double.parseDouble((String) tablaAbonada.getValueAt(i, 1));
            sumPendiente += Double.parseDouble((String) tablaAbonada.getValueAt(i, 2));

        }

        fTotalAbono.setText("" + sumAbono);
        fTotalPendiente.setText("" + sumPendiente);

    }

    public void buscar(JTable tablaAbonada, JTextField fTotalAbono, JTextField fTotalPendiente) {
        try {
            this.mostrarAbonada(tablaAbonada);
            this.totalAbonada(tablaAbonada, fTotalAbono, fTotalPendiente);
        } catch (IOException ex) {
            Logger.getLogger(PanelReportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void guardar(JTable tablaAbonada) {
        try {
            String sucursalesCSVFile = "reportes/Abonadas.FCI";
            BufferedWriter bfw = new BufferedWriter(new FileWriter(sucursalesCSVFile));

            for (int i = 0; i < tablaAbonada.getRowCount(); i++) {
                for (int j = 0; j < tablaAbonada.getColumnCount(); j++) {
                    bfw.write((String) (tablaAbonada.getValueAt(i, j)));
                    if (j < tablaAbonada.getColumnCount() - 1) {
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
