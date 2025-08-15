/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package vista;

import java.util.List;
import java.util.Optional;
import modelo.Producto;

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
}
