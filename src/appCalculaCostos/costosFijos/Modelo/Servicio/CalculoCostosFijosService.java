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
import java.util.ArrayList;
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

    public CalculoCostosFijosService(ICostoFijoRepo repoCostoFijo, IRepositorioProductoFinal productoRepo, ServicioProductoFinal servicioProductoFinal)
    {
        this.repoCostoFijo = repoCostoFijo;
        this.productoRepo = productoRepo;
        this.servicioProductoFinal = servicioProductoFinal;
    }

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

            // 1. Calcular la suma total de ponderaciones
            // 2. Obtener el total de costos fijos
            double costosFijosTotales = repoCostoFijo.calcularTotalCostosFijos();
            var ponderaciones = repoCostoFijo.listarPonderaciones();
            var sumaPonderaciones = obtenerSumaTotalDePonderaciones(ponderaciones);

            // 3. Asignar el costo fijo proporcional por producto
            for (Ponderacion p : ponderaciones)
            {

                var costoMensualAsignado=calcularCostoFijoAsignadoMensual(p, sumaPonderaciones, costosFijosTotales);
                // Guardar en BD    
                var opt = productoRepo.buscarProductoFinalPorId(p.getIdProducto());
                if (opt.isEmpty())
                {
                    throw new CostoFijoException("No se puede seguir asignando costos fijos ya que no se encontro un producto.");//TODO despues mejorar la logica
                }
                var productoFinal = opt.get();
                var ventasMensuales = productoFinal.getCantidadVendida();
                var costoUnitario = 0d;
                if (ventasMensuales != 0)
                {
                    costoUnitario = costoMensualAsignado / ventasMensuales;
                }

                repoCostoFijo.guardarCostoFijoAsignado(p.getIdProducto(), costoUnitario);//TODO verificar si es mejor hacerlo en una sola transacccion y separar responzabilidades
                servicioProductoFinal.actualizarCostoTotal(p.getIdProducto());
                servicioProductoFinal.actualizarPorcentajeDeGanancia(p.getIdProducto());

            }

        } catch (PersistenciaException | ProductoFinalException ex)
        {
            ex.printStackTrace();
            throw new CostoFijoException(ex.getMessage());
        }
    }

    public double calcularCostoFijoAsignadoMensual(Ponderacion p, double sumaPonderaciones,double costosFijosTotales)
    {
        if (sumaPonderaciones==0)
        {
            return 0;
        }
        double ponderacionTotal = p.calcularPonderacion();
        return (ponderacionTotal / sumaPonderaciones) * costosFijosTotales;
    }

    private double obtenerSumaTotalDePonderaciones(List<Ponderacion> ponderaciones) throws PersistenciaException
    {
        // Todos tienen ponderación

        double sumaPonderaciones = 0;
        for (Ponderacion p : ponderaciones)
        {
            sumaPonderaciones += p.calcularPonderacion();
        }
        return sumaPonderaciones;

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
