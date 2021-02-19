/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Logica.Servicio;
import java.util.Comparator;

/**
 *
 * @author Mar7g
 */
public class Comparar implements Comparator<Servicio>{

    @Override
    public int compare(Servicio s1, Servicio s2) {
         if(s1.getCantidad()<s2.getCantidad()){
            return -1;
        }else if(s1.getCantidad()<s2.getCantidad()){
            return 0;
        }else{
            return 1;
        }
    }
    
}
