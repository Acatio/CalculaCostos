/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosFijos.Modelo.Servicio;

import appCalculaCostos.costosFijos.Modelo.Excepciones.CostoFijoException;
import appCalculaCostos.costosFijos.Modelo.dao.ICostoFijoRepo;
import appCalculaCostos.productoFinal.modelo.exepciones.ProductoFinalException;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.IRepositorioProductoFinal;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades.ProductoFinal;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.servicios.ServicioProductoFinal;
import conexion.Exepciones.PersistenciaException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author jose
 */
public class CalculoCostosFijosService
{

    ICostoFijoRepo repoCostoFijo;
    IRepositorioProductoFinal productoRepo;
    ServicioProductoFinal servicioProductoFinal;

    public void calcularCostosFijos() throws CostoFijoException
    {
        try
        {
            var productosSinPonderacion = obtenerProductosSinPonderacion();

            if (!productosSinPonderacion.isEmpty())
            {
                Optional<ProductoFinal> opt = productoRepo.buscarProductoFinalPorId(productosSinPonderacion.getFirst());
                if (opt.isPresent())
                {
                    throw new CostoFijoException(
                            """
                            Debe asignar una ponderaci\u00f3n a todos los productos.
                            Producto sin ponderaci\u00f3n: """ + opt.get().getNombre()
                    );
                }
                throw new CostoFijoException("Error al verificar productos sin ponderación.");
            }

            // Todos tienen ponderación
            var ponderaciones = repoCostoFijo.listarPonderaciones();

            // 1. Calcular la suma total de ponderaciones
            double sumaPonderaciones = 0;
            for (Ponderacion p : ponderaciones)
            {
                sumaPonderaciones += p.calcularPonderacion();
            }

            // 2. Obtener el total de costos fijos
            double costosFijosTotales = repoCostoFijo.calcularTotalCostosFijos();

            // 3. Asignar el costo fijo proporcional por producto
            for (Ponderacion p : ponderaciones)
            {
                double ponderacion = p.calcularPonderacion();
                double costoAsignado = (ponderacion / sumaPonderaciones) * costosFijosTotales;

                // Guardar en BD
                repoCostoFijo.guardarCostoFijoAsignado(p.getIdProducto(), costoAsignado);
                servicioProductoFinal.actualizarCostoTotal(p.getIdProducto());
                servicioProductoFinal.actualizarPorcentajeDeGanancia(p.getIdProducto());
              
            }

        } catch (PersistenciaException | ProductoFinalException ex)
        {
            throw new CostoFijoException(ex.getMessage());
        }
    }

    public List<Integer> obtenerProductosSinPonderacion() throws PersistenciaException
    {
        List<Integer> productosFinales = productoRepo.listarIdProductosFinales();
        List<Integer> productosConPonderacion = repoCostoFijo.obtenerIdsProductosConPonderacion();
        // Filtra los productos que no están en la lista de ponderados
        return productosFinales.stream()
                .filter(id -> !productosConPonderacion.contains(id))
                .collect(Collectors.toList());
    }

}
