/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.logicaNegocio.servicios;

import appCalculaCostos.productoFinal.modelo.exepciones.DatosNoValidosException;
import appCalculaCostos.productoFinal.modelo.exepciones.ProductoFinalException;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.CostoModuloDTO;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades.ProductoFinal;
import appCalculaCostos.productoFinal.modelo.validaciones.ValidadorProductoFinal;
import conexion.Exepciones.PersistenciaException;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.IRepositorioProductoFinal;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.DTO.ProductoFinalCreacionDTO;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades.CostoDeModulo;
import java.util.List;

/**
 *
 * @author jose
 */
public class ServicioProductoFinal
{

    private final IRepositorioProductoFinal repo;

    public ServicioProductoFinal(IRepositorioProductoFinal repo)
    {
        this.repo = repo;
    }

    public void guardarNuevoProducto(ProductoFinalCreacionDTO dto) throws ProductoFinalException
    {
        try
        {

            // 1. Validar la entrada de datos
            validarDatosEntrada(dto);
            // 2. Crear y construir la entidad de dominio
            ProductoFinal productoFinal = construirProductoFinal(dto);
            // 3. Aplicar los cálculos de precio y ganancia
            aplicarLogicaPrecioVenta(dto, productoFinal);
            // 4. Persistir el agregado completo
            repo.guardarProductoFinalYsusCostos(productoFinal);
            System.out.println("producto guardado");
            System.out.println(productoFinal.toString());
        } catch (PersistenciaException e)
        {
            // Captura errores de la capa inferior y relanza una excepción de la capa de servicio
            throw new ProductoFinalException("No se pudo guardar el producto debido a un error de persistencia.", e);
        }
    }

    public void guardarNuevoProductoSinCostos(ProductoFinalCreacionDTO dto) throws ProductoFinalException
    {
        try
        {
            // 1. Validar la entrada de datos
            validarDatosEntrada(dto);
            // 2. Crear y construir la entidad de dominio
            ProductoFinal productoFinal = construirProductoFinalSinCostos(dto);
            // 3. Aplicar los cálculos de precio y ganancia
            aplicarLogicaPrecioVenta(dto, productoFinal);
            // 4. Persistir el agregado completo
            repo.guardarProductoFinalSinCostos(productoFinal);
            System.out.println("producto SIN costos guardado");
            System.out.println(productoFinal.toString());
        } catch (PersistenciaException e)
        {
            // Captura errores de la capa inferior y relanza una excepción de la capa de servicio
            throw new ProductoFinalException("No se pudo guardar el producto debido a un error de persistencia.", e);
        }
    }

    /**
     * Crea la entidad ProductoFinal e inserta todos los módulos de costo.
     */
    private ProductoFinal construirProductoFinalSinCostos(ProductoFinalCreacionDTO dto) throws PersistenciaException
    {
        final double COSTO_INICIAL = 0;
        
        //Creación de la Entidad y adjunción de Costos
        ProductoFinal productoFinal = new ProductoFinal(dto.nombre(), dto.cantidadVendida());
        // Calcular y fijar el costo total (snapshot)
        productoFinal.setCostoTotal(COSTO_INICIAL);
        return productoFinal;
    }

    //--------------------------------------------------------------------------
    // Nivel de Abstracción 2: Validación y Construcción
    /**
     * Valida los datos crudos del DTO.
     */
    private void validarDatosEntrada(ProductoFinalCreacionDTO dto) throws DatosNoValidosException
    {
        ValidadorProductoFinal.validarDatos(dto);
    }

    /**
     * Crea la entidad ProductoFinal e inserta todos los módulos de costo.
     */
    private ProductoFinal construirProductoFinal(ProductoFinalCreacionDTO dto) throws PersistenciaException
    {

        //Creación de la Entidad y adjunción de Costos
        ProductoFinal productoFinal = new ProductoFinal(dto.nombre(), dto.cantidadVendida());
        // Adjuntar todos los módulos de costo al producto
        adjuntarModulosDeCosto(dto, productoFinal);
        // Calcular y fijar el costo total (snapshot)
        double costoTotal = productoFinal.calcularCostoTotal();
        productoFinal.setCostoTotal(costoTotal);

        return productoFinal;
    }

    // Nivel de Abstracción 3: Adjuntar Costos
    /**
     * Itera los DTOs de costo y los convierte en entidades de dominio.
     */
    private void adjuntarModulosDeCosto(ProductoFinalCreacionDTO dto, ProductoFinal productoFinal)
    {
        // La implementación real requiere la lógica de switch/if-else para llamar al ServicioCosto correcto
        if (dto.costos() != null)
        {
            for (CostoModuloDTO costoDto : dto.costos())
            {
                // Implementación pendiente: Llamar al servicio y obtener el módulo
                // CostoDeModulo moduloDeDominio = servicioCostoFactory.obtenerModulo(costoDto);

                CostoDeModulo moduloDeDominio = null; // Línea temporal

                if (moduloDeDominio != null)
                {
                    productoFinal.agregarCosto(moduloDeDominio);
                }
            }
        }
    }

    //--------------------------------------------------------------------------
    // Nivel de Abstracción 2: Orquestación de Precio/Ganancia
    /**
     * Aplica la lógica de consistencia entre Porcentaje de Ganancia y Precio de
     * Venta.
     */
    private void aplicarLogicaPrecioVenta(ProductoFinalCreacionDTO dto, ProductoFinal productoFinal)
    {

        if (dto.porcentajeGanancia() != null)
        {
            // El usuario eligió fijar el porcentaje.
            aplicarPorcentajeFijo(dto, productoFinal);
        } else if (dto.precioVenta() != null)
        {
            // El usuario eligió fijar el precio de venta.
            aplicarPrecioVentaFijo(dto, productoFinal);
        } else
        {
            // Ninguno fue elegido. Aplica el default.
            aplicarValoresDefault(productoFinal);
        }
    }

    // Nivel de Abstracción 3: Implementación de la Lógica
    private void aplicarPorcentajeFijo(ProductoFinalCreacionDTO dto, ProductoFinal productoFinal)
    {
        // Asignamos el porcentaje elegido por el usuario
        productoFinal.setPorcentajeGanancia(dto.porcentajeGanancia());

        // La Entidad calcula el valor derivado (Precio de Venta)
        var precioVentaCalculado = productoFinal.calcularPrecioVenta();
        productoFinal.setPrecioVenta(precioVentaCalculado);
    }

    private void aplicarPrecioVentaFijo(ProductoFinalCreacionDTO dto, ProductoFinal productoFinal)
    {
        // Establecemos el precio de venta elegido por el usuario
        productoFinal.setPrecioVenta(dto.precioVenta());

        // La Entidad calcula el valor derivado (Porcentaje de Ganancia)
        var porcentajeGananciaCalculado = productoFinal.calcularPorcentajeGanancia();
        productoFinal.setPorcentajeGanancia(porcentajeGananciaCalculado);
    }

    private void aplicarValoresDefault(ProductoFinal productoFinal)
    {
        productoFinal.setPorcentajeGanancia(0.0);
        productoFinal.setPrecioVenta(0.0);
    }

    public List<ProductoFinal> listarProductosFinales() throws ProductoFinalException
    {
        try
        {
            return repo.ListarProductosFinales();
        } catch (PersistenciaException e)
        {
            throw new ProductoFinalException("No se pudo obtener la lista de productos", e);
        }
    }
}
