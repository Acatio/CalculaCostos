/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.interfacesLogicas;

import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.TipoCosto;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.DTO.ProductoFinalDatosDto;
import conexion.Exepciones.PersistenciaException;
import java.util.List;
import java.util.Optional;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades.ProductoFinal;
import java.sql.Connection;

/**
 *
 * @author jose
 */
public interface IRepositorioProductoFinal
{
    public void guardarProductoFinalSinCostos(ProductoFinal productoFinal)throws PersistenciaException;
    public boolean modificarProductoFinal(ProductoFinal productoFinal)throws PersistenciaException;
    public void eliminarProductoFinal(int id)throws PersistenciaException;
    public Optional<ProductoFinal> buscarProductoFinalPorId(int id)throws PersistenciaException;
    public List<ProductoFinal>ListarProductosFinales()throws PersistenciaException;
    public  void actualizarCostoProductoFinalCalculado(Connection conn, int idProductoFinal) throws PersistenciaException;    
    public void borrarCostotosDeProductoPorTipo(int idProductoFinal,TipoCosto tipoCosto, Connection conn) throws PersistenciaException;
    public void actualizarDatosProductoFinal(ProductoFinal productoActualizado) throws PersistenciaException;
    public void borrarProductoFinal(int idProducto) throws PersistenciaException;
 
}
