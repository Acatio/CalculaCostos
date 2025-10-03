/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.interfacesLogicas;

import conexion.Exepciones.PersistenciaException;
import java.util.List;
import java.util.Optional;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades.ProductoFinal;

/**
 *
 * @author jose
 */
public interface IProductoFinalDao
{
    public void guardarProductoFinalYsusCostos(ProductoFinal productoFinal)throws PersistenciaException;
    public boolean modificarProductoFinal(ProductoFinal productoFinal)throws PersistenciaException;
    public void eliminarProductoFinal(int id)throws PersistenciaException;
    public Optional<ProductoFinal> buscarProductoFinalPorId(int id)throws PersistenciaException;
    public List<ProductoFinal>ListarProductosFinales()throws PersistenciaException;
    
}
