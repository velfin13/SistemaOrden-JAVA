/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Mar7g
 */
public class OrdenTrabajo {

    private String nombreEmpresa;
    private String nombreCliente;
    private String numeroOrden;
    private String estadoOrden;
    private double totalOrden;
    private double saldo;

    public OrdenTrabajo() {
    }

    public OrdenTrabajo(String nombreEmpresa, String nombreCliente, String numeroOrden, String estadoOrden, double saldo, double totalOrden) {
        this.nombreEmpresa = nombreEmpresa;
        this.nombreCliente = nombreCliente;
        this.numeroOrden = numeroOrden;
        this.estadoOrden = estadoOrden;
        this.totalOrden = totalOrden;
        this.saldo = saldo;
       
    }

  

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setTotalOrden(double totalOrden) {
        this.totalOrden = totalOrden;
    }

    public double getTotalOrden() {
        return totalOrden;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(String numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public String getEstadoOrden() {
        return estadoOrden;
    }

    public void setEstadoOrden(String estadoOrden) {
        this.estadoOrden = estadoOrden;
    }
 public String toString() {
        return  nombreCliente ;
    }
}
