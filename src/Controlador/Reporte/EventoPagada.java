/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Reporte;

import Ficheros.DocumentosOrden;
import Logica.OrdenTrabajo;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author Mar7g
 */
public class EventoPagada {

    List<OrdenTrabajo> listaOrden = new ArrayList();
    DocumentosOrden docu = new DocumentosOrden();

    private double totalPagada(JTable tablaPagadas, JTextField fTotalPagada) {
        int num = tablaPagadas.getRowCount();
        double sumTotal = 0;

        for (int i = 0; i < num; i++) {
            sumTotal += Double.parseDouble((String) tablaPagadas.getValueAt(i, 1));

        }

        fTotalPagada.setText("" + sumTotal);

        return sumTotal;

    }

    private void mostrarPagadas(JTable tablaPagadas) throws IOException {
        listaOrden = docu.ConsultarOrden("PAGADO");

        String matriz[][] = new String[listaOrden.size()][2];
        for (int i = 0; i < listaOrden.size(); i++) {
            matriz[i][0] = listaOrden.get(i).getNumeroOrden();
            matriz[i][1] = Double.toString(listaOrden.get(i).getTotalOrden());

        }

        tablaPagadas.setModel(new javax.swing.table.DefaultTableModel(
                matriz,
                new String[]{
                    "NÚMERO DE ORDEN ", "VALOR PAGADO"
                }
        ));

    }

    public void buscar(JTable tablaPagadas, JTextField fTotalPagada) {
        try {

            this.mostrarPagadas(tablaPagadas);
            this.totalPagada(tablaPagadas, fTotalPagada);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ninguna");
        }
    }

    public void guardar(JTable tablaPagadas) {
        try {

            String sucursalesCSVFile = "reportes/Pagadas.FCI";
            BufferedWriter bfw = new BufferedWriter(new FileWriter(sucursalesCSVFile));

            for (int i = 0; i < tablaPagadas.getRowCount(); i++) {
                for (int j = 0; j < tablaPagadas.getColumnCount(); j++) {
                    bfw.write((String) (tablaPagadas.getValueAt(i, j)));
                    if (j < tablaPagadas.getColumnCount() - 1) {
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
