/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package datos;

import java.util.List;
import java.util.Optional;
import modelo.Producto;

/**
 *
 * @author jose
 */
public interface IProductoDAO
{
    public void guardarProducto(Producto producto);
    public boolean modificarProducto(Producto producto);
    public void eliminarProducto(int id);
    public Optional<Producto> buscarProductoPorId(int id);
    public List<Producto>ListarProductos();
    
}
