
package Controlador;

import PaginaPrincipal.PanelOrden;
import PaginaPrincipal.PanelReportes;
import PaginaPrincipal.PanelServicios;
import javax.swing.*;


public class EventoPaginaPrincipal {

    public void botonIncio(JPanel PanelCambio) {
        PanelOrden porden = new PanelOrden();
        porden.setVisible(true);
        porden.setSize(1000, 589);
        porden.setLocation(5, 5);
        PanelCambio.removeAll();
        PanelCambio.add(porden);
        PanelCambio.revalidate();
        PanelCambio.repaint();

    }

    public void botonServicio(JPanel PanelCambio) {
        PanelServicios po = new PanelServicios();
        po.setVisible(true);
        po.setSize(1000, 589);
        po.setLocation(5, 5);
        PanelCambio.removeAll();
        PanelCambio.add(po);
        PanelCambio.revalidate();
        PanelCambio.repaint();

    }

    public void botonReportes(JPanel PanelCambio) {
        PanelReportes pr = new PanelReportes();
        pr.setVisible(true);
        pr.setSize(1000, 589);
        pr.setLocation(5, 5);
        PanelCambio.removeAll();
        PanelCambio.add(pr);
        PanelCambio.revalidate();
        PanelCambio.repaint();

    }
}
