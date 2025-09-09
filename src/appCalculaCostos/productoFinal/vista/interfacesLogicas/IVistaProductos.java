/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package appCalculaCostos.productoFinal.vista.interfacesLogicas;

import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.CostoMateriaPrima;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.Insumo;
import java.util.List;
import java.util.Optional;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.ProductoFinal;

/**
 *
 * @author jose
 */
public interface IVistaProductos
{
    public void mostrarProductos(List<ProductoFinal> productos);
    public Optional<ProductoFinal> getProductoSeleccionado();
    public void mostrarMensaje(String mensaje);
    public void mostrarMensajeError(String mensajeError);
    public int getIDProductoSeleccionado();
    public String leerNombre();
    public double leerPorcentajeDeGanancia();
    public void mostrarMensajeExito(String mensajeExito);
    public void iniciarVista();
    public void setGuardarListener(Runnable callback);
    public void setEliminarListener(Runnable callback);
    public void setActualizarListener(Runnable callback);
    public void setMostrarProductosListener(Runnable callback);
    public void actualizarVista();
    
}
