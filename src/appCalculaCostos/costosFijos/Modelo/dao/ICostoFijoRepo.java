/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package appCalculaCostos.costosFijos.Modelo.dao;

import appCalculaCostos.costosFijos.Modelo.Entidades.CostoFijo;
import appCalculaCostos.costosFijos.Modelo.Servicio.Ponderacion;
import conexion.Exepciones.PersistenciaException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author jose
 */
public interface ICostoFijoRepo
{
    public void guardarCostoFijo(CostoFijo costo)throws PersistenciaException;
    public void actualizarCostoFijo(CostoFijo actualizado)throws PersistenciaException;
    public void eliminarrCostoFijo(int id)throws PersistenciaException;
    public List<CostoFijo> listarCostosFijos()throws PersistenciaException;
    public CostoFijo buscarCostoFijoPorID(int id)throws PersistenciaException;
    public void guardarPonderacion(Ponderacion ponderacion)throws PersistenciaException;
    public void actualizarPonderacion(Ponderacion ponderacion)throws PersistenciaException;
    public Optional<Ponderacion> cargarPonderacion(int idProducto) throws PersistenciaException;
    public List<Integer> obtenerIdsProductosFinales() throws PersistenciaException;
    public List<Integer> obtenerIdsProductosConPonderacion() throws PersistenciaException;
    public List<Ponderacion> listarPonderaciones() throws PersistenciaException;
    public double calcularTotalCostosFijos()throws PersistenciaException;
    public void guardarCostoFijoAsignado(int idProducto, double costoAsignado)throws PersistenciaException;
    
}
