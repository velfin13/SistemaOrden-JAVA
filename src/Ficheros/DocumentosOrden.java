/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ficheros;

import Logica.OrdenTrabajo;
import Logica.Servicio;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
public class DocumentosOrden {

    File archivo = new File("archivos/Orden.fci");
    File archivoProducto = new File("archivos/Productos.fci");
    BufferedWriter escritor;
    List<OrdenTrabajo> orden = new ArrayList<>();
    Servicio existente;
    Servicio nuevo;

    public DocumentosOrden() {

        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException ex) {
                System.err.println(ex.toString());
            }
        }

    }

    public void Agregars(Servicio s) {
        try {
            escritor = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivoProducto, true), "utf-8"));
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            System.err.println(ex.toString());
        }
        try {

            escritor.write(s.getId() + "\t" + s.getNombre() + "\t" + s.getCantidad() + "\t" + s.getPrecio() + "\t" + s.getTotal());
            escritor.newLine();
            escritor.close();

        } catch (IOException ex) {
            System.err.println(ex.toString());

        }
    }

    public void Agregar(OrdenTrabajo o) {
        try {
            escritor = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivo, true), "utf-8"));
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            System.err.println(ex.toString());
        }
        try {

            escritor.write(o.getNombreEmpresa() + "\t" + o.getNombreCliente() + "\t" + o.getNumeroOrden() + "\t"
                    + o.getEstadoOrden() + "\t" + o.getSaldo() + "\t" + o.getTotalOrden());
            escritor.newLine();
            escritor.close();

        } catch (IOException ex) {
            System.err.println(ex.toString());

        }
    }

    public List<Servicio> ConsultarReportes(String buscado) throws IOException {
        Scanner entrada = new Scanner(new File("archivos/Productos.fci"));
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
                int cantidad = Integer.parseInt(resultado.substring(0, resultado.indexOf("\t")));
                s.setCantidad(cantidad);

                resultado = resultado.substring(resultado.indexOf("\t") + 1);
                double precio = Double.parseDouble(resultado.substring(0, resultado.indexOf("\t")));
                s.setPrecio(precio);

                resultado = resultado.substring(resultado.indexOf("\t") + 1);
                double total = Double.parseDouble(resultado.substring(0));
                s.setTotal(total);

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

    public List<OrdenTrabajo> ConsultarOrden(String buscado) throws IOException {
        Scanner entrada = new Scanner(new File("archivos/Orden.fci"));
        OrdenTrabajo o;
        List<OrdenTrabajo> encontrados = new ArrayList<>();
        String resultado;
        try {

            encontrados.clear();
            while (entrada.hasNext()) {
                resultado = entrada.nextLine();

                o = new OrdenTrabajo();
                 System.out.println("maaf");
                resultado.substring(0, resultado.indexOf("\t"));
               
                o.setNombreEmpresa(resultado.substring(0, resultado.indexOf("\t")));
               
                resultado = resultado.substring(resultado.indexOf("\t") + 1);
                o.setNombreCliente(resultado.substring(0, resultado.indexOf("\t")));

                resultado = resultado.substring(resultado.indexOf("\t") + 1);
                o.setNumeroOrden(resultado.substring(0, resultado.indexOf("\t")));

                resultado = resultado.substring(resultado.indexOf("\t") + 1);
                o.setEstadoOrden(resultado.substring(0, resultado.indexOf("\t")));;

                resultado = resultado.substring(resultado.indexOf("\t") + 1);
                double saldo = Double.parseDouble(resultado.substring(0, resultado.indexOf("\t")));
                o.setSaldo(saldo);

                resultado = resultado.substring(resultado.indexOf("\t") + 1);
                double total = Double.parseDouble(resultado.substring(0));
                o.setTotalOrden(total);

                if (o.getNombreCliente().toUpperCase().contains(buscado.toUpperCase().trim()) || o.getEstadoOrden().toUpperCase().contains(buscado.toUpperCase().trim())) {
                    encontrados.add(o);
                }

            }
            entrada.close();
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "NO EXISTE EL DOCUMENTO");
        }

        return encontrados;
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

    private boolean Comparar(OrdenTrabajo e, OrdenTrabajo n) {

        if (!e.getNombreCliente().equals(n.getNombreCliente())) {
            return false;
        }

        if (!e.getNumeroOrden().equals(n.getNumeroOrden())) {
            return false;
        }

        return true;
    }

    public void Modificar(OrdenTrabajo existente, OrdenTrabajo nuevo) throws IOException {
        List<OrdenTrabajo> modificables = ConsultarOrden("");
        BorrarArchivo();
        archivo.createNewFile();
        if (archivo.exists()) {
            for (OrdenTrabajo i : modificables) {
                if (Comparar(i, existente)) {
                    Agregar(nuevo);

                } else {
                    Agregar(i);

                }

            }
        } else {
            JOptionPane.showMessageDialog(null, "El archivo no existe", "Archivo no encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }
}
