/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package appCalculaCostos.CostoManoObra.Modelo.dao;

import appCalculaCostos.CostoManoObra.Modelo.Entidades.ManoObra;
import appCalculaCostos.CostoManoObra.Modelo.Entidades.ManoObraDeProducto;
import conexion.Exepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author jose
 */
public interface ICostoManoObraRepo
{
    public void guardarCostosManoObra(List<ManoObraDeProducto> manoObra)throws PersistenciaException;
    public void actualizarCostosManoObra(List<ManoObraDeProducto> manoObra)throws PersistenciaException;
    public List<ManoObraDeProducto> listarCostosManoObra(int idProducto)throws PersistenciaException;
 
}
