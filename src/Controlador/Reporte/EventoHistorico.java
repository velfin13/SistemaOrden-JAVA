/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Reporte;

import Ficheros.DocumentosServicio;
import Logica.Servicio;
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
public class EventoHistorico {

    DocumentosServicio docuServi = new DocumentosServicio();
    List<Servicio> lista = new ArrayList();

    public void mostrarHistorico(JTable tablaHistorico) throws IOException {
        lista = docuServi.Consultar("");

        String matriz[][] = new String[lista.size()][4];
        for (int i = 0; i < lista.size(); i++) {

            matriz[i][0] = lista.get(i).getId();
            matriz[i][1] = lista.get(i).getNombre();
            matriz[i][2] = Double.toString(lista.get(i).getPrecio());
            matriz[i][3] = lista.get(i).getEstado();

        }

        tablaHistorico.setModel(new javax.swing.table.DefaultTableModel(
                matriz,
                new String[]{
                    "CÓDIGO", "SERVICIO", "PRECIO", "ESTADO"
                }
        ));
        tablaHistorico.getColumnModel().getColumn(1).setPreferredWidth(200);
    }

    public void buscar(JTable tablaHistorico) {

        try {
           this.mostrarHistorico(tablaHistorico);
        } catch (IOException ex) {
            Logger.getLogger(PanelReportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void guardar(JTable tablaHistorico){
       try {
            
            String sucursalesCSVFile = "reportes/ReporteHistorico.fci";
            BufferedWriter bfw = new BufferedWriter(new FileWriter(sucursalesCSVFile));
            
            for (int i = 0; i < tablaHistorico.getRowCount(); i++) {
                for (int j = 0; j < tablaHistorico.getColumnCount(); j++) {                    
                    bfw.write((String) (tablaHistorico.getValueAt(i, j)));
                    if (j < tablaHistorico.getColumnCount() - 1) {
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

