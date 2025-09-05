/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.vista.interfacesLogicas;

import java.util.List;
import java.util.Optional;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.Insumo;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.Producto;

/**
 *
 * @author jose
 */
public interface IVistaProductos
{
    public void mostrarProductos(List<Producto> productos);
    public Optional<Producto> getProductoSeleccionado();
    public void mostrarMensaje(String mensaje);
    public void mostrarMensajeError(String mensajeError);
    public int getIDProductoSeleccionado();
}
