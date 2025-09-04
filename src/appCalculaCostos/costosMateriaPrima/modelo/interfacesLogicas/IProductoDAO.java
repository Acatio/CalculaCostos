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
public interface IProductoDAO
{
    public void guardarProducto(Insumo producto);
    public boolean modificarProducto(Insumo producto);
    public void eliminarProducto(int id);
    public Optional<Insumo> buscarProductoPorId(int id);
    public List<Insumo>ListarProductos();
    
}
