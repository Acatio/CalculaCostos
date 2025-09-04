/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.modelo.interfacesLogicas;

import java.util.List;
import java.util.Optional;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.Insumo;

/**
 *
 * @author jose
 */
public interface IInsumoDAO
{
    public void guardarInsumo(Insumo insumo) throws Exception;
    public boolean modificarInsumo(Insumo materiaP)throws Exception;
    public void eliminarInsumo(int id)throws Exception;
    public Optional<Insumo> buscarInsumoPorID(int id)throws Exception;
    public List<Insumo>ListarInsumos()throws Exception;
}
