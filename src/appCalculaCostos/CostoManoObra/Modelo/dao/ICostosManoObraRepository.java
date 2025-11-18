/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.CostoManoObra.Modelo.dao;

import appCalculaCostos.CostoManoObra.Modelo.Entidades.ManoObra;
import conexion.Exepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author jose
 */
public interface ICostosManoObraRepository
{

    public void guardarManoObraDeProducto(int idProducto, List<ManoObra> manoObra) throws PersistenciaException;
    public List<ManoObra> obtenerManoObraDeProducto(int idProducto) throws PersistenciaException;
    void eliminarManoObraDeProducto(int idProducto) throws PersistenciaException;


}
