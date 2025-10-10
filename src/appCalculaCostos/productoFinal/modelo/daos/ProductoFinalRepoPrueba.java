/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.daos;

import appCalculaCostos.productoFinal.modelo.interfacesLogicas.IRepositorioProductoFinal;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.ServicioCosto;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades.ProductoFinal;
import conexion.Exepciones.PersistenciaException;
import java.util.List;
import java.util.Optional;
import java.sql.Connection;

/**
 *
 * @author jose
 */
public class ProductoFinalRepoPrueba implements IRepositorioProductoFinal
{

    @Override
    public void guardarProductoFinalYsusCostos(ProductoFinal productoFinal) throws PersistenciaException
    {
        System.out.println("Guardando producto: " + productoFinal.getNombre());
        guardarCostos(productoFinal,null);

    }

    public void guardarCostos(ProductoFinal productoFinal,Connection conn) throws PersistenciaException
    {
        for (ServicioCosto s : productoFinal.getCostos())
        {
            s.guardarCostos(productoFinal.getId(), null);
        }
    }

    @Override
    public void guardarProductoFinalSinCostos(ProductoFinal productoFinal) throws PersistenciaException
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean modificarProductoFinal(ProductoFinal productoFinal) throws PersistenciaException
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminarProductoFinal(int id) throws PersistenciaException
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<ProductoFinal> buscarProductoFinalPorId(int id) throws PersistenciaException
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ProductoFinal> ListarProductosFinales() throws PersistenciaException
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
