/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.modelo.interfacesLogicas;

import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DTO.InsumoDto;
import java.util.List;
import java.util.Optional;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.Insumo;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.MateriaPrima;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.Receta;
import conexion.Exepciones.PersistenciaException;

/**
 *
 * @author jose
 */
public interface IInsumoDAO
{
    public void guardarMateriaPrima(MateriaPrima materiaP) throws PersistenciaException;
    public void guardarReceta(Receta receta) throws PersistenciaException;
    public boolean modificarInsumo(Insumo materiaP) throws PersistenciaException;
    public void eliminarInsumo(int id)throws PersistenciaException;
    public Optional<Insumo> buscarInsumoPorID(int id)throws PersistenciaException;
    public List<InsumoDto>listarDtoInsumos()throws PersistenciaException;
    public List<Insumo>ListarInsumosDeProductoPorID(int id)throws PersistenciaException;
    
}
