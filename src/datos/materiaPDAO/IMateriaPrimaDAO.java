/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package datos.materiaPDAO;

import java.util.List;
import java.util.Optional;
import modelo.producto.Insumo;
import modelo.producto.MateriaPrima;

/**
 *
 * @author jose
 */
public interface IMateriaPrimaDAO
{
    public void guardarMateriaP(MateriaPrima materiaP) throws Exception;
    public boolean modificarMateriaPrima(MateriaPrima materiaP)throws Exception;
    public void eliminarMateriaPrima(MateriaPrima materiaP)throws Exception;
    public Optional<Insumo> buscarMateriaPrimaPorId(int id)throws Exception;
    public List<MateriaPrima>ListarMateriaPrima()throws Exception;
}
