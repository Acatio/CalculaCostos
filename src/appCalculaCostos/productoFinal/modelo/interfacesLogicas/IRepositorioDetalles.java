/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.interfacesLogicas;

import java.sql.Connection;
import conexion.Exepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author jose
 */
public interface IRepositorioDetalles
{
    // Método clave para borrar todos los detalles viejos de un producto.
    public void eliminarDetallesPorProducto(int idProducto, Connection conn) throws PersistenciaException;

    // Método clave para insertar la nueva lista completa de detalles.
    public void insertarDetalles(int idProducto, List<IDetalleCosto> detalles, Connection conn) throws PersistenciaException;
}
