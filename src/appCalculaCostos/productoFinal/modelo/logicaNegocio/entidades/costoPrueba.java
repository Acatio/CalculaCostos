/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades;

import appCalculaCostos.productoFinal.modelo.interfacesLogicas.DetalleCostoMP;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.IDetalleCosto;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.IRepoCosto;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.ServicioCosto;
import conexion.Exepciones.PersistenciaException;
import java.sql.Connection;

/**
 *
 * @author jose
 */
public class costoPrueba extends ServicioCosto
{
 
    public costoPrueba(int id, String nombre, IRepoCosto repoCosto, int idProductoAsociado)
    {
        super(id, nombre, repoCosto, idProductoAsociado);
    }

    public costoPrueba(String nombre, IRepoCosto repoCosto, int idProductoAsociado)
    {
        super(nombre, repoCosto, idProductoAsociado);
    }

    @Override
    public void guardarCostos(int id,Connection con) throws PersistenciaException
    {
        super.repoCosto.guardarDetalles(id, con,super.detallesCosto);
    }

    @Override
    public void agregarDetalle(IDetalleCosto detalle)
    {
        if (detallesCosto == null)
        {
            throw new IllegalStateException("No se ha inicializado la lista de costos");
        }
        if (detalle == null)
        {
            throw new IllegalStateException("El detalle no es valido");
        }
        if (!(detalle instanceof DetalleCostoMP))
        {
            
            throw new IllegalArgumentException("Este modulo solo puede recibir detalles de materia prima");
        }
        detallesCosto.add(detalle);
    }

}
