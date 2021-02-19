/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Servicio;

import Ficheros.DocumentosServicio;
import Logica.Servicio;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author Mar7g
 */
public class EventoBuscar {

    DocumentosServicio docu = new DocumentosServicio();
    List<Servicio> encontrado;

    public void botonBuscar(JTextField fConsultar, JLabel nuevo_doc) {
        List<Servicio> encontrado = new ArrayList();
        String texto = "<html>";
        try {
            encontrado = docu.Consultar(fConsultar.getText());

            for (Servicio sec : encontrado) {
                Servicio s = (Servicio) sec;
                String l_texto;
                l_texto = String.format("<h3>Servicio</h3>ID: %s<br>NOMBRE: %s<br>PRECIO:  %s<br>ESTADO: %s", s.getId(), s.getNombre(), s.getPrecio(), s.getEstado());
                texto = texto + l_texto;

            }
            texto += "</html>";
            nuevo_doc.setText(texto);
        } catch (IOException ex) {
            System.err.println(ex.toString());
        }
        if (encontrado.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontró el servicio", "No encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }

}
