/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package appCalculaCostos.productoFinal.vista.interfacesLogicas;


import java.util.List;
import java.util.Optional;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades.ProductoFinal;

/**
 *
 * @author jose
 */
public interface IVistaProductos
{
    public void iniciarVista();
    public void mostrarProductos(List<ProductoFinal> productos);
    public Optional<ProductoFinal> getProductoSeleccionado();
    public int getIDProductoSeleccionado();
    public void setEliminarListener(Runnable callback);
    public void mostrarMensaje(String mensaje);
    public void mostrarMensajeError(String mensajeError);
    public void mostrarMensajeExito(String mensajeExito);
    public void setActualizarListener(Runnable callback);
    public void setMostrarProductosListener(Runnable callback);
    public void actualizarVista();
    public void setCrearProductoInicialSinCostosListener(Runnable callback);
    public void setGuardarListener(Runnable callback);
    public String leerNombre();
    public double leerPorcentajeDeGanancia();
    public double leerPorecioVenta();
    
    
}
