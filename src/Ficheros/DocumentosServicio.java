/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ficheros;

import Logica.Servicio;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Mar7g
 */
public class DocumentosServicio {

    File archivo = new File("archivos/Servicio.fci");
    BufferedWriter escritor;
    List<Servicio> servicio = new ArrayList<>();

    public DocumentosServicio() {

        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException ex) {
                System.err.println(ex.toString());
            }
        }

    }

    public void Agregar(Servicio s) {
        try {
            escritor = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivo, true), "utf-8"));
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            System.err.println(ex.toString());
        }
        try {

            escritor.write(s.getId() + "\t" + s.getNombre() + "\t" + s.getPrecio() + "\t" + s.getEstado()+  "\t" + s.getCantidad());
            escritor.newLine();
            escritor.close();
           
        } catch (IOException ex) {
            System.err.println(ex.toString());
            
        }
    }

    private void BorrarArchivo() {
        try {
            if (archivo.exists()) {
                archivo.delete();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Servicio> Consultar(String buscado) throws IOException {
        Scanner entrada = new Scanner(new File("archivos/Servicio.fci"));
        Servicio s;

        List<Servicio> encontrados = new ArrayList<>();
        String resultado;
        try {

            encontrados.clear();
            while (entrada.hasNext()) {
                resultado = entrada.nextLine();

                s = new Servicio();
                resultado.substring(0, resultado.indexOf("\t"));
                s.setId(resultado.substring(0, resultado.indexOf("\t")));

                resultado = resultado.substring(resultado.indexOf("\t") + 1);
                s.setNombre(resultado.substring(0, resultado.indexOf("\t")));

                resultado = resultado.substring(resultado.indexOf("\t") + 1);
                double precio = Double.parseDouble(resultado.substring(0, resultado.indexOf("\t")));
                s.setPrecio(precio);
                
                resultado = resultado.substring(resultado.indexOf("\t") + 1);
                s.setEstado(resultado.substring(0, resultado.indexOf("\t")));
                
                resultado = resultado.substring(resultado.indexOf("\t") + 1);
                int cantidad = Integer.parseInt(resultado.substring(0));
                s.setCantidad(cantidad);
                

                if (s.getNombre().toUpperCase().contains(buscado.toUpperCase().trim())) {
                    encontrados.add(s);
                }

            }
            entrada.close();
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "NO EXISTE EL DOCUMENTO");
        }

        return encontrados;
    }

    private boolean Comparar(Servicio e, Servicio n) {
        if (!e.getNombre().equals(n.getNombre())) {
            return false;
        }
        if (!e.getId().equals(n.getId())) {
            return false;
        }
       
        return true;
    }

    public void Modificar(Servicio existente, Servicio nuevo) throws IOException {
        List<Servicio> modificables = Consultar("");

        BorrarArchivo(); 

        archivo.createNewFile();
        if (archivo.exists()) {
            for (Servicio i : modificables) 
            {
                if (Comparar(i, existente)) { 
                    System.out.println("e");
                    Agregar(nuevo);
                    JOptionPane.showMessageDialog(null, "Se modificó con éxito", "Documento Modificado", JOptionPane.PLAIN_MESSAGE);
                } else {  
                    Agregar(i);
                   
                   
                }
               
            }
        } else {
            JOptionPane.showMessageDialog(null, "El archivo no existe", "Archivo no encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }
}
