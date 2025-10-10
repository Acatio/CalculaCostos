/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.logicaNegocio.servicios;

import appCalculaCostos.productoFinal.modelo.exepciones.ProductoFinalException;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades.ProductoFinal;
import appCalculaCostos.productoFinal.modelo.validaciones.ValidadorProductoFinal;
import conexion.Exepciones.PersistenciaException;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.IRepositorioProductoFinal;
import java.util.List;

/**
 *
 * @author jose
 */
public class ServicioProductoFinal {

    private final IRepositorioProductoFinal repo;

    public ServicioProductoFinal(IRepositorioProductoFinal repo) {
        this.repo = repo;
    }

    public void guardarProductoFinalYsusCostos(ProductoFinal producto) throws ProductoFinalException {
        try {
            ValidadorProductoFinal.validar(producto); // reglas de negocio
            repo.guardarProductoFinalYsusCostos(producto);
        } catch (PersistenciaException e) {
            throw new ProductoFinalException("No se pudo guardar el producto", e);
        }
    }
    public void guardarProductoFinalSinCostos(ProductoFinal producto) throws ProductoFinalException {
        try {
            ValidadorProductoFinal.validar(producto);
            repo.guardarProductoFinalSinCostos(producto);
        } catch (PersistenciaException e) {
            throw new ProductoFinalException("No se pudo guardar el producto", e);
        }
    }

    public boolean modificarProductoFinal(ProductoFinal p) throws ProductoFinalException {
        try {
            ValidadorProductoFinal.validar(p);
            return repo.modificarProductoFinal(p);
        } catch (PersistenciaException e) {
            throw new ProductoFinalException("No se pudo modificar el producto", e);
        }
    }

    public void eliminarProductoFinal(int id) throws ProductoFinalException {
        try {
            repo.eliminarProductoFinal(id);
        } catch (PersistenciaException e) {
            throw new ProductoFinalException("No se pudo eliminar el producto", e);
        }
    }

    public ProductoFinal buscarProductoFinalPorId(int id) throws ProductoFinalException {
        try {
            return repo.buscarProductoFinalPorId(id)
                       .orElseThrow(() -> new ProductoFinalException("Producto no encontrado"));
        } catch (PersistenciaException e) {
            throw new ProductoFinalException("No se pudo buscar el producto", e);
        }
    }

    public List<ProductoFinal> listarProductosFinales() throws ProductoFinalException {
        try {
            return repo.ListarProductosFinales();
        } catch (PersistenciaException e) {
            throw new ProductoFinalException("No se pudo obtener la lista de productos", e);
        }
    }
}

