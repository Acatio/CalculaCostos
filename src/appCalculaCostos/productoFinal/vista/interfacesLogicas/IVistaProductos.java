/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package appCalculaCostos.productoFinal.vista.interfacesLogicas;

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
}
