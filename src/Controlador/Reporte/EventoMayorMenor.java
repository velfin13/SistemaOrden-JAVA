/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Reporte;

import Controlador.Comparar;
import Controlador.OrdenMayor;
import Ficheros.DocumentosOrden;
import Logica.Servicio;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

/**
 *
 * @author Mar7g
 */
public class EventoMayorMenor {

    List<Servicio> lista = new ArrayList();
    DocumentosOrden docu = new DocumentosOrden();
    List<Servicio> listan = new ArrayList();

    public void validar() {

        List<Servicio> repetidos = new ArrayList();
        Servicio se, se2;
        try {
            lista = docu.ConsultarReportes("");

            for (int i = 0; i < lista.size(); i++) {
                se = lista.get(i);
                int sumaCantidad = 0;
                double sumaTotal = 0;
                for (int j = i + 1; j < lista.size() && !repetidos.contains(se.getId()); j++) {

                    se2 = lista.get(j);

                    if (se.getId().equals(se2.getId())) {
                        sumaCantidad = se.getCantidad() + se2.getCantidad();
                        sumaTotal = (sumaCantidad * se.getPrecio());
                        se.setCantidad(sumaCantidad);
                        se.setTotal(sumaTotal);

                    }

                }

            }

        } catch (IOException ex) {
            System.err.println(ex.toString());

        }

    }

    public void ordenar() {
        this.validar();
        Collections.sort(lista, new Comparar());
        Map<String, Servicio> productos = new HashMap<String, Servicio>(lista.size());

        for (Servicio s : lista) {
            productos.put(s.getId(), s);
        }

        for (Map.Entry<String, Servicio> s : productos.entrySet()) {
            listan.add(s.getValue());

        }
        Collections.sort(listan, new OrdenMayor());
    }

    public void mostrar(JTable tableOrden) {

        this.ordenar();
        String matriz[][] = new String[listan.size()][5];
        for (int i = 0; i < listan.size(); i++) {

            matriz[i][0] = listan.get(i).getId();
            matriz[i][1] = listan.get(i).getNombre();
            matriz[i][2] = Integer.toString(listan.get(i).getCantidad());
            matriz[i][3] = Double.toString(listan.get(i).getPrecio());
            matriz[i][4] = Double.toString(listan.get(i).getTotal());

        }
        tableOrden.setModel(new javax.swing.table.DefaultTableModel(
                matriz,
                new String[]{
                    "CÓDIGO", "SERVICIO", "CANTIDAD", "PRECIO", "TOTAL"
                }
        ));
        tableOrden.getColumnModel().getColumn(1).setPreferredWidth(200);
    }
    public void limpiar(){
    listan.clear();
    }
    public void guardar(JTable tableOrden){
    try {

            String sucursalesCSVFile = "reportes/ReporteMayorMenor.fci";
            BufferedWriter bfw = new BufferedWriter(new FileWriter(sucursalesCSVFile));

            for (int i = 0; i < tableOrden.getRowCount(); i++) {
                for (int j = 0; j < tableOrden.getColumnCount(); j++) {
                    bfw.write((String) (tableOrden.getValueAt(i, j)));
                    if (j < tableOrden.getColumnCount() - 1) {
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
