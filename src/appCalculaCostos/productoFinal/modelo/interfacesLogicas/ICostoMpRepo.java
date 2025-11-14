/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.interfacesLogicas;

import appCalculaCostos.costosMateriaPrima.modelo.interfacesLogicas.IInsumoDAO;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DetalleReceta;
import conexion.Exepciones.PersistenciaException;
import java.util.List;


/**
 *
 * @author jose
 */
public interface ICostoMpRepo 
{
    public void guardarCotosMP(int idProductoFinal, List<DetalleReceta> costosMp) throws PersistenciaException;
    public void actualizarCostosMP(int idProductoFinal, List<DetalleReceta> nuevosCostos, IRepositorioProductoFinal repoPf) throws PersistenciaException;
    public List<DetalleReceta> listarCostosMp(int idProducto, IInsumoDAO insumoDao) throws PersistenciaException;

}
