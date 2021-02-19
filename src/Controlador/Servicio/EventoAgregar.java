/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Servicio;

import Ficheros.DocumentosServicio;
import Logica.Servicio;
import PaginaPrincipal.PanelServicios;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Mar7g
 */
public class EventoAgregar {

    ArrayList<Servicio> lista = new ArrayList<Servicio>();
    DocumentosServicio docu = new DocumentosServicio();

    public boolean validarVacio(JTextField fId, JTextField fNombreServicioA, JTextField fPrecioA, JTextField fEstado) {
        if (!fId.getText().isEmpty() && !fNombreServicioA.getText().isEmpty() && !fPrecioA.getText().isEmpty()) {
            return true;

        } else {
            return false;
        }

    }

    private void limpiar(JTextField fNombreServicioA, JTextField fPrecioA, JTextField fId) {
        fNombreServicioA.setText("");
        fPrecioA.setText("");
        fId.setText("");

    }

    public void botonagregar(JTextField fId, JTextField fNombreServicioA, JTextField fPrecioA, JTextField fEstado) {
        int conta = 0;
        Servicio servicio;
        try {
            lista = (ArrayList<Servicio>) docu.Consultar("");
        } catch (IOException ex) {
            Logger.getLogger(PanelServicios.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {

            Servicio s = new Servicio(fId.getText(), fNombreServicioA.getText(), Double.parseDouble(fPrecioA.getText()), fEstado.getText());
            if ((validarVacio(fId, fNombreServicioA, fPrecioA, fEstado)) == true) {

                for (int x = 0; x < lista.size(); x++) {
                    System.out.println(conta);
                    servicio = lista.get(x);
                    if (servicio.getId().equals(fId.getText())) {
                        JOptionPane.showMessageDialog(null, "El id ya existe");
                        this.limpiar(fNombreServicioA, fPrecioA, fId);
                        conta++;
                    }

                }

                if (conta == 0) {
                    docu.Agregar(s);
                    this.limpiar(fNombreServicioA, fPrecioA, fId);
                    JOptionPane.showMessageDialog(null, "Se agrego con éxito");
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Existen campos vacios");

        }
    }

    public void generar(JTextField fId) {
        try {
            lista = (ArrayList<Servicio>) docu.Consultar("");
            Formatter fmt = new Formatter();

            if (lista.isEmpty()) {
                fId.setText("0001");
            } else {
                int id = Integer.parseInt(lista.get(lista.size() - 1).getId()) + 1;
                Formatter ids = fmt.format("%04d", id);

                fId.setText("" + ids);
            }
        } catch (IOException ex) {
            Logger.getLogger(EventoAgregar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
