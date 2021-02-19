/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import javax.swing.JOptionPane;

/**
 *
 * @author Mar7g
 */
public class Servicio  {

    private String id;
    private String nombre;
    private double precio;
    private String estado;
    private int cantidad;
    private double total;

    public Servicio() {

    }

    public void setId(String id) {
        this.id = id;
    }

    public Servicio(String id, String nombre, int cantidad, double precio, double total) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.total = total;
    }

    public int getCantidad() {

        return cantidad;
    }

    public void setCantidad(int cantidad) {

        this.cantidad = cantidad;

    }

    public double getTotal() {
        return cantidad * precio;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Servicio(String id, String nombre, double precio, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.estado = estado;

    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return  nombre ;
    }

    

}
