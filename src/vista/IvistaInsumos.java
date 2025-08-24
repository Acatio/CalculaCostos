/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.util.List;
import modelo.producto.Insumo;


/**
 *
 * @author jose
 */
public interface IvistaInsumos
{
    public void mostrarMenu();
    public int leerOpcion();
    public void mostrarInsumos(List<Insumo> insumos);
    public int getIDInsumoSeleccionado();
    public String leerNombre();
    public double leerCosto();
    public double leerCantidad();
    public int seleccionarIDUnidadDeMedida();
    public void mostrarMensaje(String mensaje);
    public void mostrarMensajeError(String mensajeError);

}
