/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.modelo.interfacesLogicas;

import java.util.List;
import java.util.Optional;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.Insumo;
import conexion.Exepciones.PersistenciaException;

/**
 *
 * @author jose
 */
public interface IInsumoDAO
{
    public void guardarInsumo(Insumo insumo) throws PersistenciaException;
    public boolean modificarInsumo(Insumo materiaP) throws PersistenciaException;
    public void eliminarInsumo(int id)throws PersistenciaException;
    public Optional<Insumo> buscarInsumoPorID(int id)throws PersistenciaException;
    public List<Insumo>ListarInsumos()throws PersistenciaException;
    public List<Insumo>ListarInsumosDeProductoPorID(int id)throws PersistenciaException;
    
}
